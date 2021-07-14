package com.example.aihomeservice.module;

/**
 *
 * @author thangth
 */
public class Verify8KResultResponse {
    Integer status_code;
    String msg;
    Double score;
    Integer code;
    String error;

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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

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

    @Override
    public String toString() {
        return "Verify8KResultResponse{" + "status_code=" + status_code + ", msg=" + msg + ", score=" + score + ", code=" + code + ", error=" + error + '}';
    }


}

