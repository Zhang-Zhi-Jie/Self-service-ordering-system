package com.example.a13051_000.buffetmealsystem;

import java.util.List;

/**
 * Created by shubin on 2016/6/12.
 */
public class Status {
    private String status;
    private List<Result_Spoon_detail> result_spoon_details;
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setResult_spoon_details(List<Result_Spoon_detail> result_spoon_details){
        this.result_spoon_details = result_spoon_details;
    }
    public List<Result_Spoon_detail> getResult_spoon_details(){
        return this.result_spoon_details;
    }
    public String toString(){
        return "Status:" +this.status+" result_details:" +this.result_spoon_details.toString();
    }
}
