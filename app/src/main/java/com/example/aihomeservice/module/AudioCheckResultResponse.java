package com.example.aihomeservice.module;

public class AudioCheckResultResponse {

    String code;
    String status;
    String msg;
    String file_code;

    public AudioCheckResultResponse() {
    }

    public AudioCheckResultResponse(String code, String status, String msg, String file_code) {
        this.code = code;
        this.status = status;
        this.msg = msg;
        this.file_code = file_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFile_code() {
        return file_code;
    }

    public void setFile_code(String file_code) {
        this.file_code = file_code;
    }


    @Override
    public String toString() {
        return "AudioCheckResultResponse{" +
                "code='" + code + '\'' +
                ", status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", file_code='" + file_code + '\'' +
                '}';
    }
}