package com.walker.gank.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.logger.Logger;
import com.walker.gank.R;
import com.walker.gank.bean.Gank;
import com.walker.gank.bean.HttpResult;
import com.walker.gank.gank.meizigank.MeiziContentProvider;
import com.walker.gank.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by walker on 2016/12/20 0020.
 */

public class ImageFragment extends BaseFragment {
    @Bind(R.id.image_rv_image)
    XRecyclerView recyclerView;
    List<Object> items;
    MultiTypeAdapter adapter;
    private int count = 1;
    boolean isPrepared = false;
    View view;
    Context context;

    public ImageFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(container.getContext(), R.layout.fragment_image, null);
        isPrepared = true;
        context = container.getContext();
        init();

        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.e("isVisibleTouser   " + isVisibleToUser);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (!isPrepared || !getUserVisibleHint()) {
            Logger.d("ImageFragment 未加载isPrepared:   " + isPrepared + "     isVisible   " + isVisible);
            return;
        } else {
            Logger.d("ImageFragment 加载isPrepared:   " + isPrepared + "     isVisible   " + isVisible);
            getData(count);
        }

    }

    private void init() {
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        items = new ArrayList<>();
        adapter = new MultiTypeAdapter(items);
        adapter.register(Gank.class, new MeiziContentProvider());
        recyclerView.setAdapter(adapter);
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                items.clear();
                getData(1);
                count = 1;
            }

            @Override
            public void onLoadMore() {
                getData(count);
            }
        });

    }


    void getData(int page) {
        RetrofitUtil.getGankService().getGankByCategory("福利", page + "").observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new DisposableSubscriber<HttpResult<List<Gank>>>() {
            @Override
            public void onNext(HttpResult<List<Gank>> listHttpResult) {
                if (!listHttpResult.error) {
                    items.addAll(listHttpResult.results);
                    count++;
                }
            }

            @Override
            public void onError(Throwable t) {
                if (count == 2) {
                    recyclerView.refreshComplete();
                } else {
                    recyclerView.loadMoreComplete();
                }
            }

            @Override
            public void onComplete() {
                adapter.notifyDataSetChanged();

                if (count == 2) {
                    recyclerView.refreshComplete();
                } else {
                    recyclerView.loadMoreComplete();
                }
            }
        });
    }
}
