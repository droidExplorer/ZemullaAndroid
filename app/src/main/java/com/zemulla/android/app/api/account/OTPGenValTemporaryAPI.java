package com.zemulla.android.app.api.account;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.account.otpgenvaltemporary.OTPGenValRequest;
import com.zemulla.android.app.model.account.otpgenvaltemporary.OTPGenValResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class OTPGenValTemporaryAPI {
    private AccountAPI accountAPI;
    private APIListener apiListener;

    public OTPGenValTemporaryAPI() {

        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);
    }

    public void otpGenValTemporary(final OTPGenValRequest otpGenValTemporaryRequest, final APIListener apiListener) {
        Call<OTPGenValResponse> loginResponseCall = accountAPI.OTPGenValTemporary(otpGenValTemporaryRequest);
        loginResponseCall.enqueue(new Callback<OTPGenValResponse>() {
            @Override
            public void onResponse(Call<OTPGenValResponse> call, Response<OTPGenValResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<OTPGenValResponse> call, Throwable t) {
                apiListener.onFailure(call, t);
            }
        });

    }


    public void onDestory() {
        if (accountAPI != null) {
            accountAPI = null;
        }
        if (apiListener != null) {
            apiListener = null;
        }
    }
}
