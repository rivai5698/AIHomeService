package its.homeai.connect;

import com.google.gson.annotations.SerializedName;

public class AppResponse {
    @SerializedName("conversation_id")
    private String conversation_id;

    @SerializedName("msg")
    private String msg;

    @SerializedName("status")
    private Integer status;

    public AppResponse(String conversation_id, String msg, Integer status) {
        this.conversation_id = conversation_id;
        this.msg = msg;
        this.status = status;
    }


    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AppResponse{" +
                "conversation_id='" + conversation_id + '\'' +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                '}';
    }
}
