package com.example.shift.model;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private String Cat_Icon_link;
    private String Cat_name;

    public CategoryModel(String CAt_Icon_link, String cat_name) {
        this.Cat_Icon_link = CAt_Icon_link;
        Cat_name = cat_name;
    }

    public String getCat_Icon_link() {
        return Cat_Icon_link;
    }

    public void setCat_Icon_link(String CAt_Icon_link) {
        this.Cat_Icon_link = CAt_Icon_link;
    }

    public String getCat_name() {
        return Cat_name;
    }

    public void setCat_name(String cat_name) {
        Cat_name = cat_name;
    }
}
