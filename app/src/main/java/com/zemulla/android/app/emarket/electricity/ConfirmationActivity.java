package com.zemulla.android.app.emarket.electricity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.kazang.KazangElectricityAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.emarket.MarketActivity;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangElectricityRequest;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangElectricityResponse;
import com.zemulla.android.app.widgets.OTPDialogAfterLogin;
import com.zemulla.android.app.widgets.TfTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;
import retrofit2.Call;
import retrofit2.Response;

public class ConfirmationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.initFab)
    FabButton initFab;
    @BindView(R.id.lineatInitialViewTopup)
    LinearLayout lineatInitialViewTopup;
    @BindView(R.id.frameRootTopup)
    FrameLayout frameRootTopup;
    @BindView(R.id.activity_topup_initial_transaction)
    LinearLayout activityTopupInitialTransaction;
    @BindView(R.id.edtName)
    TfTextView edtName;
    @BindView(R.id.edtAddress)
    TfTextView edtAddress;
    @BindView(R.id.edtOtherDetails)
    TfTextView edtOtherDetails;
    private Unbinder unbinder;
    private FlipAnimation animation;
    private KazangElectricityResponse kazangElectricityResponse;
    private OTPDialogAfterLogin mOtpDialogAfterLogin;
    private ProgressDialog progressDialog;
    private KazangElectricityAPI kazangElectricityAPI;
    private KazangElectricityRequest kazangElectricityRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        unbinder = ButterKnife.bind(this);

        init();
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        kazangElectricityRequest = (KazangElectricityRequest) intent.getSerializableExtra(Intent.EXTRA_REFERRER);
        kazangElectricityResponse = (KazangElectricityResponse) intent.getSerializableExtra(Intent.EXTRA_REFERRER_NAME);
        edtAddress.setText(String.format("Address : %s", kazangElectricityResponse.getCustomer_address()));
        edtName.setText(String.format("Name : %s", kazangElectricityResponse.getCustomer_name()));
        edtOtherDetails.setText(String.format("Other Details : %s", kazangElectricityResponse.getConfirmation_message()));
    }


    private void init() {
        initObject();
        initToolbar();
        actionListener();
        mOtpDialogAfterLogin = new OTPDialogAfterLogin(new MaterialDialog.Builder(ConfirmationActivity.this));
        mOtpDialogAfterLogin.setOnSubmitListener(onSubmitListener);
    }

    private void initObject() {
        kazangElectricityAPI = new KazangElectricityAPI();
        kazangElectricityRequest = new KazangElectricityRequest();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
    }

    private void showProgressDialog() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    private void hidProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    OTPDialogAfterLogin.OnSubmitListener onSubmitListener = new OTPDialogAfterLogin.OnSubmitListener() {
        @Override
        public void onSubmit(String OTP) {
            confirmEbill(OTP);

        }

        @Override
        public void OTPReceived() {
            hidProgressDialog();
        }
    };

    private void confirmEbill(String otp) {

        //MeterNumber,TotalCharge,Amount,TotalPayableAmount,
        // UserID,UserID,product_id,
        // confirmation_number,
        // ZemullaTransionID,
        // VerificationCode
        showProgressDialog();
        kazangElectricityRequest.setVerificationCode(otp);
        kazangElectricityRequest.setZemullaTransionID(kazangElectricityResponse.getZemullaTransactionID());
        kazangElectricityRequest.setConfirmation_number(kazangElectricityResponse.getConfirmation_number());
        kazangElectricityAPI.kazangDirectRechargeAPI(kazangElectricityRequest, kazangElectricityResponseAPIListener);

    }


    private void actionListener() {
        initFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call Api for requesting OTP
                showProgressDialog();
                mOtpDialogAfterLogin.generateOPT();
            }
        });
    }

    private void initToolbar() {

        if (toolbar != null) {
            toolbar.setTitle("Dhruvil Patel");
            toolbar.setSubtitle("Effective Balance : ZMW 1222.5");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    APIListener<KazangElectricityResponse> kazangElectricityResponseAPIListener = new APIListener<KazangElectricityResponse>() {
        @Override
        public void onResponse(Response<KazangElectricityResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful()) {
                    KazangElectricityResponse kazangElectricityResponse = response.body();
                    if (kazangElectricityResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        Functions.showSuccessMsg(ConfirmationActivity.this, kazangElectricityResponse.getResponse().getResponseMsg(), true, MarketActivity.class);
                    } else {
                        Functions.showError(ConfirmationActivity.this, kazangElectricityResponse.getResponse().getResponseMsg(), false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onFailure(Call<KazangElectricityResponse> call, Throwable t) {

        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
