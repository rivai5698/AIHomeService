package com.example.aihomeservice.module;

/**
 *
 * @author thangth
 */
public class CheckAuthResponseResult {
    String access_token;
    String token_type;
    String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    @Override
    public String toString() {
        return "CheckAuthResponseResult{" + "access_token=" + access_token + ", token_type=" + token_type + ", result=" + result + '}';
    }





}

