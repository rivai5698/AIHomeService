package com.example.aihomeservice.connect;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author thangth
 */
@Getter
@Setter
@Data
public class Verify16KResponse {
    @SerializedName("raw_score")
    private String raw_score;
    @SerializedName("result")
    private String result;
    @SerializedName("score")
    private Float score;
    @SerializedName("status")
    private Integer status;
    @SerializedName("msg")
    private String msg;

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

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
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

    public Verify16KResponse() {

    }

    public Verify16KResponse(String raw_score, String result, Float score, Integer status, String msg) {
        this.raw_score = raw_score;
        this.result = result;
        this.score = score;
        this.status = status;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Verify16KResponse{" +
                "raw_score='" + raw_score + '\'' +
                ", result='" + result + '\'' +
                ", score=" + score +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}

