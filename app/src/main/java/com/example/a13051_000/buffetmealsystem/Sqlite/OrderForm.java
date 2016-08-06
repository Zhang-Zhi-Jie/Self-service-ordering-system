package com.example.a13051_000.buffetmealsystem.Sqlite;

/**
 * Created by shubin on 2016/8/6.
 */
public class OrderForm {
    private long id;
    private long num;
    private String detail;
    private String price;
    private String id_server;
    public long getId() {
        return id;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getNum() {
        return num;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return detail;
    }

    public String getId_server() {
        return id_server;
    }

    public void setId_server(String id_server) {
        this.id_server = id_server;
    }
}
