package com.example.aihomeservice.module;

/**
 *
 * @author thangth
 */
public class SignUp8KResultResponse {
    Integer status_code;
    String msg;
    Integer code;
    String error;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

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
        return "SignUp8KResultResponse{" + "status_code=" + status_code + ", msg=" + msg + ", code=" + code + ", error=" + error + '}';
    }

}

