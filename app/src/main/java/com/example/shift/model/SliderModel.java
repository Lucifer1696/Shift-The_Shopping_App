package com.example.shift.model;

import java.io.Serializable;

public class SliderModel implements Serializable {

    private String banner;
    private String backgroundcolor;

    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }

    public SliderModel(String banner, String backgroundcolor) {
        this.banner = banner;
        this.backgroundcolor = backgroundcolor;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

}
