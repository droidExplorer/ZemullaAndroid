package com.zemulla.android.app.user.api;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.AccountAPI;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.user.model.ContactRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 08-08-2016.
 */
public class ContactApi {

    private AccountAPI accountAPI;
    private APIListener<com.zemulla.android.app.model.base.Response> apiListener;

    public ContactApi() {

        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);
    }

    public void doContact(final ContactRequest request, final APIListener<com.zemulla.android.app.model.base.Response> apiListener) {
        Call<com.zemulla.android.app.model.base.Response> loginResponseCall = accountAPI.contactZemulla(request);
        loginResponseCall.enqueue(new Callback<com.zemulla.android.app.model.base.Response>() {
            @Override
            public void onResponse(Call<com.zemulla.android.app.model.base.Response> call, Response<com.zemulla.android.app.model.base.Response> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<com.zemulla.android.app.model.base.Response> call, Throwable t) {
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
