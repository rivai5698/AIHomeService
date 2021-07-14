package com.example.aihomeservice.module;

/**
 *
 * @author thangth
 */
public class SignUp16KResultResponse {
    Integer status;
    String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public SignUp16KResultResponse() {

    }
    public SignUp16KResultResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SignUp16KResultResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}


