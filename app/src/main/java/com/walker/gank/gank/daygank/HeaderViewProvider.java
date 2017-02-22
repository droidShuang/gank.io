package com.walker.gank.gank.daygank;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.walker.gank.R;
import com.walker.gank.glide.ImageSizeModel;
import com.walker.gank.glide.ImageSizeModelImp;
import com.walker.gank.util.ImageLoader;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by walker on 2016/12/21 0021.
 */

public class HeaderViewProvider extends ItemViewProvider<HeaderItem, HeaderViewProvider.ItemHolder> {
    @NonNull
    @Override
    protected ItemHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_header, parent, false);
        return new ItemHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemHolder holder, @NonNull HeaderItem headerItem) {

        //  ImageLoader.displayImage(headerItem.url + "?imageView2/0/h/" + DensityUtils.dip2px(MApplication.getContext(), 200), holder.ivHeader);
        ImageSizeModel imageSizeModel = new ImageSizeModelImp(headerItem.url);
        ImageLoader.displayImage(imageSizeModel, holder.ivHeader, holder.progressBar);
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        final ImageView ivHeader;
        final ProgressBar progressBar;

        public ItemHolder(View itemView) {
            super(itemView);
            ivHeader = (ImageView) itemView.findViewById(R.id.item_iv_header);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_load_progress);
        }
    }

}
