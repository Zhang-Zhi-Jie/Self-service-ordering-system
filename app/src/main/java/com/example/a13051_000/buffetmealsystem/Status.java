package com.example.a13051_000.buffetmealsystem;

import java.util.List;

/**
 * Created by shubin on 2016/6/12.
 */
public class Status {
    private String status;
    private List<Result_Spoon_detail> result;
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setResult(List<Result_Spoon_detail> result){
        this.result = result;
    }
    public List<Result_Spoon_detail> getResult(){
        return this.result;
    }
    @Override
    public String toString(){
        return "Status:" +this.status+" result_details:" +this.result.toString();
    }
}
