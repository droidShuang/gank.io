package com.walker.gank.gank.daygank;

import java.util.List;

/**
 * Created by walker on 2016/12/20 0020.
 */

public class ContentItem {
    public final String desc;
    public List<String> imageUrl;

    public ContentItem(String desc) {
        super();
        this.desc = desc;
    }

    public ContentItem(String desc, List<String> imageUrl) {
        super();
        this.desc = desc;
        this.imageUrl = imageUrl;
    }
}
