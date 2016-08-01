package com.example.a13051_000.buffetmealsystem.Fragment.FragmentForm;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shubin on 2016/6/16.
 */
public class ResultFromServer implements Serializable{
    private String status;
    private String total_price;
    private List<Result> result;
    public void setResult(List<Result> result) {
        this.result = result;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getTotal_price() {
        return total_price;
    }
}
