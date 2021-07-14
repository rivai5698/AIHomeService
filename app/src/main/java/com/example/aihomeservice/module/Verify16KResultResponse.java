package com.example.aihomeservice.module;

/**
 *
 * @author thangth
 */
public class Verify16KResultResponse {


    Integer status;
    String msg;
    Double score;
    String raw_score;
    String result;

    public Verify16KResultResponse() {
    }

    public Verify16KResultResponse(Integer status, String msg, Double score, String raw_score, String result) {
        this.status = status;
        this.msg = msg;
        this.score = score;
        this.raw_score = raw_score;
        this.result = result;
    }

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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getRaw_score() {
        return raw_score;
    }

    public void setRaw_score(String raw_score) {
        this.raw_score = raw_score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Verify16KResultResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", score=" + score +
                ", raw_score='" + raw_score + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}

