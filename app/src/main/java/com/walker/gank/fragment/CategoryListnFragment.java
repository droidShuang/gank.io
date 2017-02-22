package com.walker.gank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.logger.Logger;
import com.walker.gank.R;
import com.walker.gank.bean.Gank;
import com.walker.gank.bean.HttpResult;
import com.walker.gank.gank.categorygank.CategoryGankViewProvider;
import com.walker.gank.util.RetrofitUtil;
import com.walker.gank.view.EmptyLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by walker on 2016/12/26 0026.
 */

public class CategoryListnFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.gank_container)
    EmptyLayout emptyLayout;
    @Bind(R.id.gank_rv)
    XRecyclerView rvGank;
    @Bind(R.id.gank_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    List<Object> items;
    MultiTypeAdapter adapter;
    String type;
    int count = 1;
    boolean isPrepared = false;
    View view;

    public CategoryListnFragment() {
        super();
    }

    public static CategoryListnFragment getInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        CategoryListnFragment categoryListnFragment = new CategoryListnFragment();
        categoryListnFragment.setArguments(args);
        return categoryListnFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = this.getArguments().getString("type");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gank, container, false);
        isPrepared = true;
        init();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


    }

    private void init() {
        ButterKnife.bind(this, view);
        emptyLayout.bindView(rvGank);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        rvGank.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGank.setPullRefreshEnabled(false);
        rvGank.setLoadingMoreEnabled(true);
        items = new ArrayList<>();
        adapter = new MultiTypeAdapter(items);
        adapter.register(Gank.class, new CategoryGankViewProvider());
        rvGank.setAdapter(adapter);
        rvGank.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

                getData(count);
            }
        });
    }

    @Override
    public void onRefresh() {
        items.clear();
        count = 1;
        lazyLoad();
    }

    private void getData(int page) {
        RetrofitUtil.getGankService().getGankByCategory(type, page + "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<HttpResult<List<Gank>>>() {
                    @Override
                    public void onNext(HttpResult<List<Gank>> listHttpResult) {
                        if (!listHttpResult.error) {

                            items.addAll(listHttpResult.results);
                            for (Gank gank :
                                    listHttpResult.results) {

                            }
                            count++;
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        adapter.notifyDataSetChanged();
                        if (count != 1) {
                            rvGank.loadMoreComplete();
                        }
                    }
                });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.e("isVisibleTouser   " + isVisibleToUser);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (!getUserVisibleHint() || !isPrepared) {
            Logger.d("CategoryListFragment 未加载isPrepared:   " + isPrepared + "     isVisible   " + isVisible);
            return;
        } else {
            Logger.d("CategoryListFragment 加载");
            getData(count);
        }
    }
}
