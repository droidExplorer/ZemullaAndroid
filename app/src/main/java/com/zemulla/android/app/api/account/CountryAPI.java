package com.zemulla.android.app.api.account;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.account.country.CountryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class CountryAPI {

    private AccountAPI accountAPI;
    private APIListener countryListener;

    public CountryAPI() {
        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);
    }

    public void getCountryAPI(final APIListener countryListener) {
        Call<CountryResponse> countryResponseCall = accountAPI.GetCountryListAD();
        countryResponseCall.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {

                countryListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                countryListener.onFailure(call, t);
            }
        });
    }

    public void setCountryListener(APIListener countryListener) {
        this.countryListener = countryListener;
    }

    public void onDestory() {
        if (accountAPI != null) {
            accountAPI = null;
        }
        if (countryListener != null) {
            countryListener = null;
        }
    }


}
