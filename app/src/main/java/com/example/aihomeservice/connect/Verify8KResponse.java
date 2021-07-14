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
public class Verify8KResponse {
    @SerializedName("msg")
    private String msg;
    @SerializedName("raw_score")
    private String raw_score;
    @SerializedName("score")
    private Float score;
    @SerializedName("status_code")
    private Integer status_code;
    @SerializedName("code")
    private Integer code;
    @SerializedName("error")
    private String error;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRaw_score() {
        return raw_score;
    }

    public void setRaw_score(String raw_score) {
        this.raw_score = raw_score;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Verify8KResponse{" + "msg=" + msg + ", raw_score=" + raw_score + ", score=" + score + ", status_code=" + status_code + ", code=" + code + ", error=" + error + '}';
    }
}
