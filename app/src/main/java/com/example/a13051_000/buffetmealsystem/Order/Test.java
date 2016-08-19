package com.example.a13051_000.buffetmealsystem.Order;

import java.util.List;

/**
 * Created by 13051_000 on 2016/8/7.
 */
public class Test {
    private String id;
    private String name;
    private String price;
    private int num;//商品数量
    private int sumIntegral;

    public String toString() {
        return "test [id=" + id + ", name=" + name
                + ", price=" + price + "]";
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public Test(String id, String name, String price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public Test(){

    }
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSumIntegral() {
        return sumIntegral;
    }

    public void setSumIntegral(int sumIntegral) {
        this.sumIntegral = sumIntegral;
    }


    public static boolean isAllFalse(List<Boolean> array) {

        return false;
    }

    public static boolean isAllTrue(List<Boolean> array) {

        return true;
    }

    public static boolean isHaveOneFasle(List<Boolean> array) {

        return false;
    }

    public static boolean isHaveOneTrue(List<Boolean> array) {
        return true;
    }
}
