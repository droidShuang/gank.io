package com.walker.gank.gank.daygank;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.walker.gank.R;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by walker on 2016/12/20 0020.
 */

public class TitleItemViewProvider extends ItemViewProvider<TitleItem, TitleItemViewProvider.TextHolder> {
    @NonNull
    @Override
    protected TextHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_title, parent, false);
        return new TextHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull TextHolder holder, @NonNull TitleItem titleItem) {

        holder.title.setText(titleItem.title);
    }

    static class TextHolder extends RecyclerView.ViewHolder {
        private final TextView title;

        public TextHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_tx_title);
        }
    }
}
