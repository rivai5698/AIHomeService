package com.example.aihomeservice.module;

/**
 *
 * @author thangth
 */
public class CreateUserResult {
    Integer status_code;
    String msg;

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CreateUserResult{" + "status_code=" + status_code + ", msg=" + msg + '}';
    }









}
