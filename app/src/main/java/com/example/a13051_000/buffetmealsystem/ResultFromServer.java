package com.example.a13051_000.buffetmealsystem;

/**
 * Created by shubin on 2016/4/29.
 */
public class ResultFromServer {
    private String status;
    private String nick_name;
    private String nickname;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String Nick_name) {
        this.nick_name = Nick_name;
    }
    public String getNickname(){
        return nickname;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
}