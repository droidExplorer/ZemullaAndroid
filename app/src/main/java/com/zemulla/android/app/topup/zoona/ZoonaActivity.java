package com.zemulla.android.app.topup.zoona;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.widgets.OTPDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbanje.kurt.fabbutton.FabButton;

public class ZoonaActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.edtAmount)
    EditText edtAmount;
    @BindView(R.id.btnProcessInitialTransaction)
    FabButton btnProcessInitialTransaction;
    @BindView(R.id.lineatInitialViewTopup)
    LinearLayout lineatInitialViewTopup;
    @BindView(R.id.edtNumber)
    EditText edtNumber;
    @BindView(R.id.edtNationdID)
    EditText edtNationdID;
    @BindView(R.id.edtPin)
    EditText edtPin;
    @BindView(R.id.btnProcessResetTransaction)
    FabButton btnProcessResetTransaction;
    @BindView(R.id.btnProcessConfirmTransaction)
    FabButton btnProcessConfirmTransaction;
    @BindView(R.id.linearTrnsViewTopup)
    LinearLayout linearTrnsViewTopup;
    @BindView(R.id.frameRootTopup)
    FrameLayout frameRootTopup;
    @BindView(R.id.activity_topup_initial_transaction)
    LinearLayout activityTopupInitialTransaction;

    private FlipAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoona);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        initToolbar();

        actionListener();

        btnProcessResetTransaction = (FabButton) findViewById(R.id.btnProcessResetTransaction);
        btnProcessResetTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.reverse();
                frameRootTopup.startAnimation(animation);
            }
        });
    }

    private void actionListener() {
        btnProcessInitialTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validations in if..else then method

                if (Functions.isEmpty(edtAmount)) {
                    Functions.showError(ZoonaActivity.this, "Please Enter Amount", false);
                } else {
                    calculateAmount();
                }
            }
        });

        btnProcessResetTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.reverse();
                frameRootTopup.startAnimation(animation);
            }
        });

        btnProcessConfirmTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // validations in if..else then method

                if (Functions.isEmpty(edtNumber)) {
                    Functions.showError(ZoonaActivity.this, "Please Enter Number", false);
                } else if (Functions.getLength(edtNumber) < 10) {
                    Functions.showError(ZoonaActivity.this, "Please Enter Valid Number", false);
                } else if (Functions.isEmpty(edtNationdID)) {
                    Functions.showError(ZoonaActivity.this, "Please Enter National ID", false);
                } else if (Functions.isEmpty(edtPin)) {
                    Functions.showError(ZoonaActivity.this, "Please Enter PIN", false);
                } else if (Functions.getLength(edtPin) < 4) {
                    Functions.showError(ZoonaActivity.this, "Please Enter Valid PIN", false);
                } else {
                    new OTPDialog(ZoonaActivity.this, new OTPDialog.onSubmitListener() {
                        @Override
                        public void onSubmit(String OTP) {

                        }

                        @Override
                        public void onResend() {

                        }

                        @Override
                        public void ChangeEmail() {

                        }

                    }).show();
                }
            }
        });
    }

    private void calculateAmount() {
        btnProcessInitialTransaction.showProgress(true);

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                btnProcessInitialTransaction.hideProgressOnComplete(true);
                btnProcessInitialTransaction.onProgressCompleted();
                animation = new FlipAnimation(lineatInitialViewTopup, linearTrnsViewTopup);
                frameRootTopup.startAnimation(animation);

            }
        }.start();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Dhruvil Patel");
            toolbar.setSubtitle("Effective Balance : ZMW 1222.5");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkVisibility();
            }
        });
    }

    private void checkVisibility() {
        if (lineatInitialViewTopup.isShown()) {
            finish();
        } else {
            animation.reverse();
            frameRootTopup.startAnimation(animation);
        }
    }

    @Override
    public void onBackPressed() {
        checkVisibility();
    }
}
