package com.example.shift.model;

public class ProductSpecificationModel {
    String Featurename ;
    String FeatureValue ;

    public ProductSpecificationModel(String featurename, String featureValue) {
        Featurename = featurename;
        FeatureValue = featureValue;
    }

    public String getFeaturename() {
        return Featurename;
    }

    public void setFeaturename(String featurename) {
        Featurename = featurename;
    }

    public String getFeatureValue() {
        return FeatureValue;
    }

    public void setFeatureValue(String featureValue) {
        FeatureValue = featureValue;
    }
}
