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
public class CheckAuthResponse {
    @SerializedName("access_token")
    private String access_token;
    @SerializedName("token_type")
    private String token_type;

    public CheckAuthResponse() {
    }

    public CheckAuthResponse(String access_token, String token_type) {
        this.access_token = access_token;
        this.token_type = token_type;
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
        return "CheckAuthResponse{" + "access_token=" + access_token + ", token_type=" + token_type + '}';
    }


}
