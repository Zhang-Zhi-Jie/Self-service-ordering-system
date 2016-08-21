package com.example.a13051_000.buffetmealsystem.Order;

import java.util.List;

/**
 * Created by shubin on 2016/8/21.
 */
public class Parse_Json_comments{
    private String status;
    private List<Data> data;

    public void setDatas(List<Data> datas) {
        this.data = datas;
    }

    public List<Data> getDatas() {
        return data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
class Data{
    private String comment_content;
    private String comment_id;
    private String user_name;
    private String time;
    private String user_id;
    private String star_level;

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getStar_level() {
        return star_level;
    }

    public void setStar_level(String star_level) {
        this.star_level = star_level;
    }
}
