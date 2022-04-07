package com.example.shift.model;

import java.io.Serializable;

public class MyOrdersItemsModel implements Serializable {

    private String productImage;
   private String productTilte;
    private String deliveryStatus;

    public MyOrdersItemsModel(String productImage,  String productTilte, String deliveryStatus) {
        this.productImage = productImage;
        this.productTilte = productTilte;
        this.deliveryStatus = deliveryStatus;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTilte() {
        return productTilte;
    }

    public void setProductTilte(String productTilte) {
        this.productTilte = productTilte;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

}
