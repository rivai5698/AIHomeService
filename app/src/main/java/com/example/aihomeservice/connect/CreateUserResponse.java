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
public class CreateUserResponse {
    @SerializedName("status_code")
    private Integer status_code;
    @SerializedName("msg")
    private String msg;

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
        return "CreateUserResponse{" + "v=" + status_code + ", msg=" + msg + '}';
    }








}
