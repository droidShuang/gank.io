package com.walker.gank.gank.categorygank;

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
import com.walker.gank.bean.Gank;
import com.walker.gank.bean.ImageInfo;
import com.walker.gank.glide.ImageSizeModel;
import com.walker.gank.glide.ImageSizeModelImp;
import com.walker.gank.util.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import me.drakeet.multitype.ItemViewProvider;
import okhttp3.Call;


/**
 * Created by walker on 2016/12/26 0026.
 */

public class CategoryGankViewProvider extends ItemViewProvider<Gank, CategoryGankViewProvider.ItemHolder> {
    @NonNull
    @Override
    protected ItemHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_gank, parent, false);
        return new ItemHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ItemHolder holder, @NonNull final Gank gank) {

        holder.txTitle.setText(gank.getDesc());
        if (gank.getImages() != null) {
            holder.imageView.setVisibility(View.VISIBLE);
            OkHttpUtils.get().url(gank.getImages().get(0) + "?imageInfo").build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.e("okhttp imageinfo error " + e.getMessage());
                }

                @Override
                public void onResponse(String response, int id) {
                    ImageInfo imageInfo = new Gson().fromJson(response, ImageInfo.class);
                    ImageSizeModel imageSizeModel = new ImageSizeModelImp(gank.getImages().get(0));
                    if (imageInfo.getFormat().contains("gif")) {
                        ImageLoader.displayGif(imageSizeModel, holder.imageView, holder.progressBar);
                    } else {
                        ImageLoader.displayImage(imageSizeModel, holder.imageView, holder.progressBar);
                    }
                }
            });
        } else {
            holder.progressBar.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);

        }

    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        //   @Bind(R.id.item_iv_image)
        final TintImageView imageView;
        //  @Bind(R.id.item_tx_title)
        final TintTextView txTitle;
        final ProgressBar progressBar;


        public ItemHolder(View itemView) {
            super(itemView);
            imageView = (TintImageView) itemView.findViewById(R.id.item_iv_image);
            txTitle = (TintTextView) itemView.findViewById(R.id.item_tx_desc);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_load_progress);

        }
    }
}
