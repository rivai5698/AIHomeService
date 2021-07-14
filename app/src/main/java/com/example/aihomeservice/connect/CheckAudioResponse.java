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
public class CheckAudioResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("file_code")
    private String file_code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private String status;

    public CheckAudioResponse(){

    }

    public CheckAudioResponse(String code, String file_code, String msg, String status) {
        this.code = code;
        this.file_code = file_code;
        this.msg = msg;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFile_code() {
        return file_code;
    }

    public void setFile_code(String file_code) {
        this.file_code = file_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CheckAudioResponse{" +
                "code='" + code + '\'' +
                ", file_code='" + file_code + '\'' +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                '}';
    }
}
