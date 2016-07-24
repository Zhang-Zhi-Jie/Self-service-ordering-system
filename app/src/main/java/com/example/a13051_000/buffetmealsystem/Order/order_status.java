package com.example.a13051_000.buffetmealsystem.Order;

/**
 * Created by shubin on 2016/6/16.
 */
public class order_status {
    private String status;
    private String total_price;

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
