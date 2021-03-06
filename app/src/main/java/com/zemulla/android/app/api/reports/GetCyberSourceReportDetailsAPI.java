package com.zemulla.android.app.api.reports;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.ReportRequest;
import com.zemulla.android.app.topup.transaction.cybersource.GetCyberSourceReportDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetCyberSourceReportDetailsAPI {

    private ReportsAPI paymentAPI;
    private APIListener apiListener;
    Call<GetCyberSourceReportDetailsResponse> call;

    public GetCyberSourceReportDetailsAPI() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ReportsAPI.class);
    }

    public void getSendMoneyApiReportDetailsAPI(final ReportRequest reportRequest, final APIListener apiListener) {
        this.apiListener = apiListener;
        call = paymentAPI.getCyberSourceReportDetails(reportRequest);
        call.enqueue(new Callback<GetCyberSourceReportDetailsResponse>() {
            @Override
            public void onResponse(Call<GetCyberSourceReportDetailsResponse> call, Response<GetCyberSourceReportDetailsResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<GetCyberSourceReportDetailsResponse> call, Throwable t) {
                apiListener.onFailure(call, t);
            }
        });

    }

    public void onDestory() {
        if (paymentAPI != null) {
            paymentAPI = null;
        }
        if (apiListener != null) {
            apiListener = null;
        }
    }
}
