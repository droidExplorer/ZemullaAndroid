package com.zemulla.android.app.model.account.optgenval;

import com.zemulla.android.app.model.base.BaseResponse;
import com.zemulla.android.app.model.base.BaseSmallCaseResponse;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class OTPGenValResponse extends BaseSmallCaseResponse{


    private String HiddenMobile;
    private long UserID;

    public String getHiddenMobile() {
        return HiddenMobile;
    }

    public void setHiddenMobile(String HiddenMobile) {
        this.HiddenMobile = HiddenMobile;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }
}
