package com.walker.gank.bean;

/**
 * Created by walker on 2016/12/22 0022.
 */

public class ImageInfo {

    /**
     * format : <ImageType         string>
     * width : <ImageWidth        int>
     * height : <ImageHeight       int>
     * colorModel : <ImageColorModel   string>
     * frameNumber : <ImageFrameNumber  int>
     */

    private String format;
    private int width;
    private int height;
    private String colorModel;
    private String frameNumber;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColorModel() {
        return colorModel;
    }

    public void setColorModel(String colorModel) {
        this.colorModel = colorModel;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }
}
