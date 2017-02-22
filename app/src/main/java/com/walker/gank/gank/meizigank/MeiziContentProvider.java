package com.walker.gank.gank.meizigank;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.walker.gank.R;
import com.walker.gank.bean.Gank;
import com.walker.gank.glide.ImageSizeModel;
import com.walker.gank.glide.ImageSizeModelImp;
import com.walker.gank.util.ImageLoader;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by walker on 2016/12/22 0022.
 */

public class MeiziContentProvider extends ItemViewProvider<Gank, MeiziContentProvider.ItemHolder> {
    @NonNull
    @Override
    protected ItemHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_image, parent, false);
        return new ItemHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemHolder holder, @NonNull Gank meiziItem) {
        ImageView target = holder.imageViewWeakReference.get();
        if (target != null) {
            //ImageLoader.displayImage(meiziItem.getUrl() + "?imageView2/0/w/100/h/100/interlace/1", target);
            ImageSizeModel imageSizeModel = new ImageSizeModelImp(meiziItem.getUrl());
            ImageLoader.displayImage(imageSizeModel, target, holder.progressBar);
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_iv_image)
        ImageView ivMeizi;
        @Bind(R.id.item_load_progress)
        ProgressBar progressBar;
        WeakReference<ImageView> imageViewWeakReference;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imageViewWeakReference = new WeakReference<ImageView>(ivMeizi);
        }
    }
}
