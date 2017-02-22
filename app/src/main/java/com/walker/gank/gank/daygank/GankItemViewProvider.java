package com.walker.gank.gank.daygank;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bilibili.magicasakura.widgets.TintImageView;
import com.bilibili.magicasakura.widgets.TintTextView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.walker.gank.R;
import com.walker.gank.activity.ImageViewPagerActivity;
import com.walker.gank.bean.Gank;
import com.walker.gank.bean.ImageInfo;
import com.walker.gank.glide.ImageSizeModel;
import com.walker.gank.glide.ImageSizeModelImp;
import com.walker.gank.util.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import me.drakeet.multitype.ItemViewProvider;
import okhttp3.Call;


/**
 * Created by walker on 2016/12/20 0020.
 */

public class GankItemViewProvider extends ItemViewProvider<Gank, GankItemViewProvider.ContentHolder> {
    Context context;

    @NonNull
    @Override
    protected ContentHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_content, parent, false);
        context = parent.getContext();
        return new ContentHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ContentHolder holder, @NonNull final Gank contentItem) {

        if (contentItem.getImages() == null) {
            holder.txDesc.setText("*" + contentItem.getDesc());
            holder.ivShow.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.GONE);
        } else {
            holder.ivShow.setVisibility(View.VISIBLE);
            holder.txDesc.setText("*" + contentItem.getDesc());
            holder.ivShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, ImageViewPagerActivity.class);
                    intent.putStringArrayListExtra("imageUrls", (ArrayList<String>) contentItem.getImages());
                    context.startActivity(intent);
                }
            });

            //   ImageLoader.displayImage(imageSizeModel, holder.ivShow);
            OkHttpUtils.get().url(contentItem.getImages().get(0) + "?imageInfo").build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.json(response);
                    ImageInfo imageInfo = new Gson().fromJson(response, ImageInfo.class);
                    ImageSizeModel imageSizeModel = new ImageSizeModelImp(contentItem.getImages().get(0));
                    if (imageInfo.getFormat().contains("gif")) {
                        ImageLoader.displayGif(imageSizeModel, holder.ivShow, holder.progressBar);

                    } else {
                        ImageLoader.displayImage(imageSizeModel, holder.ivShow, holder.progressBar);
                    }
                }
            });


        }
    }

    static class ContentHolder extends RecyclerView.ViewHolder {
        final TintTextView txDesc;
        final TintImageView ivShow;
        final ProgressBar progressBar;


        public ContentHolder(View itemView) {
            super(itemView);
            txDesc = (TintTextView) itemView.findViewById(R.id.item_tx_desc);
            ivShow = (TintImageView) itemView.findViewById(R.id.item_iv_show);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_load_progress);

        }
    }
}
