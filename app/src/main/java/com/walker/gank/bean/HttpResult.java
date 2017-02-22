package com.walker.gank.bean;

import java.util.List;

/**
 * Created by walker on 2016/12/13 0013.
 */

public class HttpResult<T> {
    public List<String> category;
    public boolean error;
    public T results;
}
