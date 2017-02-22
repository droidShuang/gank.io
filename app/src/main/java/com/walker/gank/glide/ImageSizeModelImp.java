package com.walker.gank.glide;

import android.os.Parcel;
import android.os.Parcelable;

import com.orhanobut.logger.Logger;

/**
 * Created by walker on 2016-12-29.
 */

public class ImageSizeModelImp implements ImageSizeModel, Parcelable {
    private String baseUrl;

    public ImageSizeModelImp(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public ImageSizeModelImp(Parcel in) {
        this.baseUrl = in.readString();
    }

    @Override
    public String buildUrl(int width, int height) {
        String url = baseUrl + "?imageView2/3/h/" + height + "/w/" + width;
        Logger.d(url);
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.baseUrl);
    }

    public static final Creator<ImageSizeModelImp> CREATOR = new Creator<ImageSizeModelImp>() {
        @Override
        public ImageSizeModelImp createFromParcel(Parcel source) {
            return new ImageSizeModelImp(source);
        }

        @Override
        public ImageSizeModelImp[] newArray(int size) {
            return new ImageSizeModelImp[size];
        }
    };
}
