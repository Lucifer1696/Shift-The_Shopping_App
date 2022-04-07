package com.example.shift.model;

import java.io.Serializable;
import java.util.List;

public class HomePageModal implements Serializable {

    public static final int BANNER_SLIDER = 0;
    public static final int STRIP_AD_BANNER= 1;
    public static final int HORIZONTAL_PRODUCT_VIEW= 2;
    public static final int GRID_PRODUCT_VIEW= 3;
    private int type;
    private String backgroundColor;

    /////////// Banner Slider
    private List<SliderModel> sliderModelList;
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }
    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    public HomePageModal(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }
    /////////// Banner Slider

    /////////// Strip Ad
    private String resource;

    public HomePageModal(int type , String resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }

    public String getResource() {
        return resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }
    public String getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    /////////// Strip Ad

    ////////// Horizontal layout view
    private String title;
    private List<HorizontalProductScrollModel>horizontalProductScrollModelList;

    public HomePageModal(int type, String title ,String backgroundColor,List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.type = type;
        this.title = title;
        this.backgroundColor =backgroundColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<HorizontalProductScrollModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }
    public void setHorizontalProductScrollModelList(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }
    ////////// Horizontal layout view
}
