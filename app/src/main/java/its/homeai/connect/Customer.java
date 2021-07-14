package its.homeai.connect;

import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("token")
    private String token;

    @SerializedName("callbot_id")
    private String callbot_id;

    @SerializedName("callcenter_phone")
    private String callcenter_phone;

    @SerializedName("customer_phone")
    private String customer_phone;

    @SerializedName("input_slots")
    private Object input_slots;

    public String getToken() {
        return token;
    }

    public String getCallbot_id() {
        return callbot_id;
    }

    public String getCallcenter_phone() {
        return callcenter_phone;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public Object getInput_slots() {
        return input_slots;
    }

    public Customer(String token, String callbot_id, String callcenter_phone, String customer_phone, Object input_slots) {
        this.token = token;
        this.callbot_id = callbot_id;
        this.callcenter_phone = callcenter_phone;
        this.customer_phone = customer_phone;
        this.input_slots = input_slots;
    }
}

