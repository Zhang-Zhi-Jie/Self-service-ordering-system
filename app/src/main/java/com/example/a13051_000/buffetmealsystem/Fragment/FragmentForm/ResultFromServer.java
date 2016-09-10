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
    private List<Finish_status> finish_status;
    public void setResult(List<Result> result) {
        this.result = result;
    };
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

    public List<Finish_status> getFinish_status() {
        return finish_status;
    }
    public void setFinish_status(List<Finish_status> finish_statuses) {
        this.finish_status = finish_statuses;
    }
}
class Finish_status{
    private String id;
    private Finish_status_detail finish_status_detail;

    public Finish_status_detail getFinish_status_detail() {
        return finish_status_detail;
    }

    public void setFinish_status_detail(Finish_status_detail finish_status_detail) {
        this.finish_status_detail = finish_status_detail;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
class Finish_status_detail{
    private String total_num;
    private String finished_num;

    public String getFinished_num() {
        return finished_num;
    }

    public void setFinished_num(String finished_num) {
        this.finished_num = finished_num;
    }

    public String getTotal_num() {
        return total_num;
    }
    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }

}
