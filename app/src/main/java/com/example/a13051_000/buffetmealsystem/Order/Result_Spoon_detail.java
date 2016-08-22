package com.example.a13051_000.buffetmealsystem.Order;

/**
 * Created by shubin on 2016/6/12.
 */
public class Result_Spoon_detail {
    private String dish_name;
    private String price;
    private String unit;
    private String user_name;
    private String id;
    private String classify;
    private String arg1;
    private String arg2;
    private String arg3;
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

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }

    public String getArg3() {
        return arg3;
    }

    public String toString(){
        return "Name:"+this.dish_name+"Price:"+this.price;
    }
}

