package com.example.a13051_000.buffetmealsystem;

/**
 * Created by shubin on 2016/6/12.
 */
public class Result_Spoon_detail {
    private String dish_name;
    private String price;
    private String unit;
    private String user_name;
    public String getDish_name(){
        return this.dish_name;
    }
    public void setDish_name(String dish_name){
        this.dish_name = dish_name;
    }
    public String getPrice(){
        return this.price;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public String getUnit(){
        return this.unit;
    }
    public void setUser_name(String user_name){
        this.user_name  = user_name;
    }
    public String getUser_name(){
        return this.user_name;
    }
    public String toString(){
        return "Name:"+this.dish_name+"Price:"+this.price;
    }
}

