package com.example.shift.seller;

public class ItemsModel {
    String os,name,ram,rom, price ;

    public ItemsModel(String os, String name, String ram, String rom, String price) {
        this.os = os;
        this.name = name;
        this.ram = ram;
        this.rom = rom;
        this.price = price;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
