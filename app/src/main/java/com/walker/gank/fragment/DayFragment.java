package com.walker.gank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.walker.gank.R;
import com.walker.gank.bean.DayGank;
import com.walker.gank.bean.Gank;
import com.walker.gank.bean.HttpResult;
import com.walker.gank.gank.daygank.GankItemViewProvider;
import com.walker.gank.gank.daygank.HeaderItem;
import com.walker.gank.gank.daygank.HeaderViewProvider;
import com.walker.gank.gank.daygank.TitleItem;
import com.walker.gank.gank.daygank.TitleItemViewProvider;
import com.walker.gank.util.RetrofitUtil;
import com.walker.gank.view.EmptyLayout;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by walker on 2016/12/20 0020.
 */

public class DayFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.main_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.day_container)
    EmptyLayout emptyLayout;
    @Bind(R.id.day_rv)
    RecyclerView recyclerView;
    MultiTypeAdapter adapter;
    List<Object> items;
    private boolean isPrepared = false;
    View view;

    public DayFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(container.getContext(), R.layout.fragment_day, null);
        isPrepared = true;
        init();

        return view;
    }


    private void init() {
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emptyLayout.bindView(recyclerView);
        emptyLayout.showBindView();
        items = new ArrayList<>();
        adapter = new MultiTypeAdapter(items);
        adapter.register(TitleItem.class, new TitleItemViewProvider());
        adapter.register(Gank.class, new GankItemViewProvider());
        adapter.register(HeaderItem.class, new HeaderViewProvider());
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(true);

    }


    void getData() {
        Logger.d("getData");
        //先获取有干货的日期，然后获取最近一天的干货
        RetrofitUtil.getGankService().getGankDay()
                .map(new Function<HttpResult<List<String>>, String>() {
                    @Override
                    public String apply(HttpResult<List<String>> listHttpResult) throws Exception {
                        return listHttpResult.results.get(0).replace("-", "/");
                    }
                })
                .flatMap(new Function<String, Publisher<HttpResult<DayGank>>>() {
                    @Override
                    public Publisher<HttpResult<DayGank>> apply(String s) throws Exception {
                        return RetrofitUtil.getGankService().getGank(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<HttpResult<DayGank>>() {
                    @Override
                    public void onNext(HttpResult<DayGank> result) {

                        items.clear();
                        for (String category :
                                result.category) {
                            Logger.d(category);

                            items.add(new TitleItem(category));
                            switch (category) {
                                case "福利":
                                    items.add(new HeaderItem(result.results.get福利().get(0).getUrl()));
                                    break;
                                case "Android":
                                    items.addAll(result.results.getAndroid());
                                    break;
                                case "iOS":
                                    items.addAll(result.results.getiOS());
                                    break;
                                case "休息视频":
                                    items.addAll(result.results.get休息视频());
                                    break;
                                case "拓展资源":
                                    items.addAll(result.results.get拓展资源());
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.e(t.getMessage());
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        emptyLayout.showError(emptyListener);
                        recyclerView.setNestedScrollingEnabled(true);
                    }

                    @Override
                    public void onComplete() {
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        adapter.notifyDataSetChanged();
                        emptyLayout.showBindView();


                    }
                });
    }

    @Override
    public void onRefresh() {

        getData();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.e("isVisibleTouser   " + isVisibleToUser);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (!getUserVisibleHint() || !isPrepared) {
            Logger.d("dayFragment 未加载 isPrepared:   " + isPrepared + "     isVisible   " + getUserVisibleHint());
            return;
        } else {
            Logger.d("dayFragment 加载isPrepared:   " + isPrepared + "     isVisible   " + getUserVisibleHint());
            getData();
        }
    }

    View.OnClickListener emptyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            swipeRefreshLayout.setRefreshing(true);
            getData();
        }
    };
}
