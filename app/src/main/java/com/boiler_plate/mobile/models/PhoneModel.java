package com.boiler_plate.mobile.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by juanpablogarcia on 3/30/16.
 */
public class PhoneModel {
    @SerializedName("phone_Id")
    public String phoneId;
    @SerializedName("phone_last_four_digits")
    public String phoneDigits;

    public PhoneModel(String phoneId, String phoneDigits) {
        this.phoneId = phoneId;
        this.phoneDigits = phoneDigits;
    }
}
