package com.example.foodorder.models;

public class Category {

    private  String Name;
    private String Image;
    Category(){


    }
    Category(String name,String image){
        this.setName(name);
        this.setImage(image);

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
