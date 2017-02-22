package com.walker.gank.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.walker.gank.R;


/**
 * Created by walker on 2016/11/17 0017.
 */

public class EmptyLayout extends FrameLayout {
    private final Context context;
    private final View emptyView;
    private View bindView;
    private final View errorView;
    final Button btRefresh;

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyLayout, 0, 0);

        int emptyResourceId = typedArray.getResourceId(R.styleable.EmptyLayout_emptyLayout, R.layout.layout_empty);
        emptyView = View.inflate(context, emptyResourceId, null);
        addView(emptyView, params);

        int errorResourceId = typedArray.getResourceId(R.styleable.EmptyLayout_errorLayout, R.layout.layout_error);
        errorView = View.inflate(context, R.layout.layout_error, null);
        btRefresh = (Button) errorView.findViewById(R.id.layout_bt_refresh);
        addView(errorView, params);
        setGone();
        typedArray.recycle();
    }

    public void setGone() {
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    public void bindView(View view) {
        bindView = view;
    }

    public void showError(View.OnClickListener listener) {
        if (bindView != null) {
            bindView.setVisibility(View.GONE);
        }
        setGone();
        errorView.setVisibility(View.VISIBLE);
        btRefresh.setOnClickListener(listener);
    }

    public void showEmpty() {

        setGone();
        emptyView.setVisibility(View.VISIBLE);


    }

    public void showBindView() {
        if (bindView != null) {
            bindView.setVisibility(View.VISIBLE);
            setGone();
        }
    }
}
