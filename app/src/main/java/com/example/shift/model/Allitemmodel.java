package com.example.shift.model;

public class Allitemmodel {
    String Category;
    String Name;
    String Company;
    String Price;
    String Discount;
    String Stock;
    String Description;
    String Photo1,Photo2,Photo3,Photo4;
    String Warranty;
    String Quantity;
    String NumbweOfCartitem;
    String CID;

    public Allitemmodel(String cid,String category, String name, String company, String price, String discount, String description, String photo1, String photo2, String photo3, String photo4, String warranty, String quantity, String numbweOfCartitem) {
        Category = category;
        Name = name;
        Company = company;
        Price = price;
        Discount = discount;
        Description = description;
        Photo1 = photo1;
        Photo2 = photo2;
        Photo3 = photo3;
        Photo4 = photo4;
        Warranty = warranty;
        Quantity = quantity;
        NumbweOfCartitem = numbweOfCartitem;
        CID=cid;
    }

    public Allitemmodel(String category, String name, String company, String price, String discount, String photo1, String quantity, String CID) {
        Category = category;
        Name = name;
        Company = company;
        Price = price;
        Discount = discount;
        Photo1 = photo1;
        Quantity = quantity;
        this.CID = CID;
    }

    public Allitemmodel(int i,String cid,String category, String name, String company, String price, String discount, String stock, String description, String photo1, String photo2, String photo3, String photo4, String warranty, String quantity) {
        Category = category;
        Name = name;
        Company = company;
        Price = price;
        Discount = discount;
        Stock = stock;
        Description = description;
        Photo1 = photo1;
        Photo2 = photo2;
        Photo3 = photo3;
        Photo4 = photo4;
        Warranty = warranty;
        Quantity = quantity;
        CID=cid;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getNumbweOfCartitem() {
        return NumbweOfCartitem;
    }

    public void setNumbweOfCartitem(String numbweOfCartitem) {
        NumbweOfCartitem = numbweOfCartitem;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPhoto1() {
        return Photo1;
    }

    public void setPhoto1(String photo1) {
        Photo1 = photo1;
    }

    public String getPhoto2() {
        return Photo2;
    }

    public void setPhoto2(String photo2) {
        Photo2 = photo2;
    }

    public String getPhoto3() {
        return Photo3;
    }

    public void setPhoto3(String photo3) {
        Photo3 = photo3;
    }

    public String getPhoto4() {
        return Photo4;
    }

    public void setPhoto4(String photo4) {
        Photo4 = photo4;
    }

    public String getWarranty() {
        return Warranty;
    }

    public void setWarranty(String warranty) {
        Warranty = warranty;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
