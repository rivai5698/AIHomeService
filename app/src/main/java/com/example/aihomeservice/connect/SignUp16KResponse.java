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
public class SignUp16KResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("msg")
    private String msg;

    public SignUp16KResponse(){

    }

    public SignUp16KResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
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

    @Override
    public String toString() {
        return "SignUp16KResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
