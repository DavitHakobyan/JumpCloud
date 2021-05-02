package com.smartit.jumpcloud;

public class ActionTime {

    private String action;
    private Integer time;

    public ActionTime() {
    }

    public ActionTime(String action, Integer time) {
        this.action = action;
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public Integer getTime() {
        return time;
    }

}
