package com.zemulla.android.app.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.user.LoginActivity;
import com.zemulla.android.app.widgets.customdialog.SweetAlertDialog;

import java.lang.reflect.Field;

import mbanje.kurt.fabbutton.CircleImageView;
import mbanje.kurt.fabbutton.FabButton;
import rx.Subscription;


public class Functions {

    public static SharedPreferences preferences;

    public static SharedPreferences.Editor editor;


    public static String regularFont = "fonts/Roboto-Light.ttf";

    public static String LATO_FONT = "fonts/Lato-Regular.ttf";

    private static int up = 0, low = 0, no = 0, spl = 0, xtra = 0, len = 0, points = 0;
    private static char c;

    public static DisplayMetrics getDeviceMetrics(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }


    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static void fireIntent(Context context, Class cls) {
        Intent i = new Intent(context, cls);
        context.startActivity(i);
    }

    public static void fireIntentWithClearFlag(Activity context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();

    }

    public static void fireIntentWithClearFlagWithWithPendingTransition(Activity context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();
        context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    public static void fireIntentWithClearFlag(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();

    }

    public static void fireIntentWithClearFlag(Activity context, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();

    }

    public static void fireIntent(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static Typeface getLatoFont(Context ctx) {
        Typeface typeface = Typeface.createFromAsset(ctx.getAssets(), LATO_FONT);
        return typeface;
    }

    public static boolean haveNetworkConnection(Activity context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static boolean isEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString());
    }

    public static boolean emailValidation(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }


    public static int getLength(EditText editText) {
        return editText.getText().toString().trim().length();
    }

    public static String toStingEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String toStingTextView(TextView textView) {
        return textView.getText().toString().trim();
    }

    public static void showError(final Context context, final boolean isFinish) {
        new MaterialDialog.Builder(context)
                .content(context.getResources().getString(R.string.errorMsg))
                .typeface(Functions.getLatoFont(context), Functions.getLatoFont(context))
                .positiveText(android.R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        if (isFinish) {
                            Activity activity = (Activity) context;
                            activity.finish();
                            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }
                })
                .show();
    }


    public static void showError(final Context context, String errorMsg, final boolean isFinish) {

        if (context == null) {
            return;
        }
        new MaterialDialog.Builder(context)
                .content(errorMsg)
                .typeface(Functions.getLatoFont(context), Functions.getLatoFont(context))
                .positiveText(android.R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        if (isFinish) {
                            Activity activity = (Activity) context;
                            activity.finish();
                            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }
                }).show();
    }


    public static void showError(final Activity activity, String errorMsg, final boolean isFinish, final Class<?> cls) {


        if (activity == null) {
            return;
        }

        new MaterialDialog.Builder(activity)
                .content(errorMsg)
                .typeface(Functions.getLatoFont(activity), Functions.getLatoFont(activity))
                .positiveText(android.R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        if (isFinish) {
                            Intent intent = new Intent(activity, cls);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(intent);
                            activity.finish();
                            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }
                })
                .show();
    }


    public static void showSuccessMsg(final Activity activity, String msg, final boolean isFinish) {

        new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(msg)
                .showContentText(false)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (isFinish) {
                            activity.finish();
                            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }
                }).show();
    }

    public static void showSuccessMsg(final Activity activity, String msg, final boolean isFinish, final Class<?> cls) {
        if (activity == null) {
            return;
        }
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(msg)
                .showContentText(false)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (isFinish) {
                            Intent intent = new Intent(activity, cls);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(intent);
                            activity.finish();
                            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }
                });
        sweetAlertDialog.show();
    }

    public static void showFailedMsg(final Activity activity, String msg, final boolean isFinish, final Class<?> cls) {
        if (activity == null) {
            return;
        }
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(msg)
                .showContentText(false)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (isFinish) {
                            Intent intent = new Intent(activity, cls);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(intent);
                            activity.finish();
                            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }
                });

        sweetAlertDialog.show();
    }


    public static SweetAlertDialog showSuccessMsgReturn(final Activity activity, String msg, final boolean isFinish, final Class<?> cls) {

        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(msg)
                .showContentText(false)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (isFinish) {
                            Intent intent = new Intent(activity, cls);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(intent);
                            activity.finish();
                            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }
                });

        return sweetAlertDialog;
    }

    public static SweetAlertDialog showFailedMsgReturn(final Activity activity, String msg, final boolean isFinish, final Class<?> cls) {

        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(msg)
                .showContentText(false)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (isFinish) {
                            Intent intent = new Intent(activity, cls);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(intent);
                            activity.finish();
                            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }
                });

        return sweetAlertDialog;
    }


    public static void showSuccessMsg(final Activity activity, String msg, final boolean isFinish, final Intent intent) {

        if (activity == null) {
            return;
        }
        new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(msg)
                .showContentText(false)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (isFinish) {
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                        }
                    }
                }).show();
    }


    public static String setEmptyString(String test) {
        return test == null ? "" : test;
    }

    public static AdvancedSpannableString getTermsAndConditionAndPrivacyAndContecPolicy(final String text, final String url, final Context context, TextView tv) {

        String fullName = text.trim();
        AdvancedSpannableString advFullName = new AdvancedSpannableString(fullName);
        advFullName.setClickableSpanTo(fullName);
        advFullName.setBold(fullName);
        advFullName.setUnderLine(fullName);

        advFullName.setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark), fullName);
        advFullName.setSpanClickListener(new AdvancedSpannableString.OnClickableSpanListner() {
            @Override
            public void onSpanClick() {

                Toast.makeText(context, "text", Toast.LENGTH_LONG).show();
            }
        });
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        return advFullName;
    }

    public static void initProgressDialog(Context context, ProgressDialog progressDialog) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
    }

    /**
     * Ask Run time permission for android marshmallow and above api.
     */
    public static void setPermission(final Context context, @NonNull String[] permissions, PermissionListener permissionListene) {

        if (permissions != null && permissions.length == 0 && permissionListene != null) {
            return;
        }
        new TedPermission(context)
                .setPermissionListener(permissionListene)
                .setDeniedMessage("If you reject permission,you can not use this service\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(permissions)
                .check();
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        } else {
            if (phoneNumber.trim().length() < 10) {
                return false;
            } else return phoneNumber.trim().length() == 10;
        }
    }

    public static void setImage(Context context, ImageView imgProfilePic, String s) {
        Glide.with(context).load(s).asBitmap().into(imgProfilePic);
    }

    public static void setRoundImage(final Context context, final ImageView imageView, String url) {
        Glide.with(context).load(url).asBitmap().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void showPromptDialog(final Context context, String errorMsg, final boolean isFinish) {
        new MaterialDialog.Builder(context)
                .content(errorMsg)
                .typeface(Functions.getLatoFont(context), Functions.getLatoFont(context))
                .positiveText("Yes")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        if (isFinish)
                            ((Activity) context).finish();
                    }
                })
                .negativeText("No")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void closeSession(Context context) {
        PrefUtils.setLoggedIn(context, false);

        LoginResponse response = new LoginResponse();
        PrefUtils.setUserProfile(context, response);
        Functions.fireIntentWithClearFlag(context, LoginActivity.class);

    }

    public static void removeListener(APIListener<?>... listener) {

        for (APIListener<?> apiListener : listener) {
            if (apiListener != null) {
                apiListener = null;
            }
        }
    }

    public static void removeSubscition(Subscription subscription) {

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

    }

    public static void removeSubscitions(Subscription... subscription) {

        for (Subscription subscription1 : subscription) {
            if (subscription1 != null && !subscription1.isUnsubscribed()) {
                subscription1.unsubscribe();
            }
        }

    }

    public static PasswordTracker getPasswordStr(String pass) {

        PasswordTracker tracker = new PasswordTracker();

        int len = pass.length();
        int passWordInt = 0;
        String passwordType = "", color = "#000000";

        if (len <= 5) points++;
        else if (len <= 10) points += 2;
        else
            points += 3;
        for (int i = 0; i < len; i++) {
            c = pass.charAt(i);
            if (c >= 'a' && c <= 'z') {
                if (low == 0) points++;
                low = 1;
            } else {
                if (c >= 'A' && c <= 'Z') {
                    if (up == 0) points++;
                    up = 1;
                } else {
                    if (c >= '0' && c <= '9') {
                        if (no == 0) points++;
                        no = 1;
                    } else {
                        if (c == '_' || c == '@') {
                            if (spl == 0) points += 1;
                            spl = 1;
                        } else {
                            if (xtra == 0) points += 2;
                            xtra = 1;

                        }
                    }
                }
            }
        }
        if (points <= 3) {
            passwordType = "WEAK";
            color = "#FF0000";
            passWordInt = AppConstant.WEAK;

        } else if (points <= 6) {
            passwordType = "MEDIUM";
            color = "#FFA500";
            passWordInt = AppConstant.MEDIUM;

        } else if (points <= 9) {
            passwordType = "HIGH";
            color = "#008000";
            passWordInt = AppConstant.HIGH;
        }

        points = 0;
        len = 0;
        up = 0;
        low = 0;
        no = 0;
        xtra = 0;
        spl = 0;

        tracker.setText(passwordType);
        tracker.setColor(Color.parseColor(color));
        tracker.setPasswordType(passWordInt);

        return tracker;
    }

    public static void setToolbarWallet(Toolbar toolbar, GetWalletDetailResponse walletResponse, LoginResponse loginResponse) {
        if (toolbar != null) {
            toolbar.setTitle(String.format("%s %s", loginResponse.getFirstName(), loginResponse.getLastName()));
            toolbar.setSubtitle(String.format("Effective Balance: %s %.2f", AppConstant.ZMW, walletResponse.getEffectiveBalance()));
            try {
                Field f = toolbar.getClass().getDeclaredField("mSubtitleTextView");
                f.setAccessible(true);
                TextView toolbarTextView = (TextView) f.get(toolbar);
                toolbarTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                toolbarTextView.setFocusable(true);
                toolbarTextView.setFocusableInTouchMode(true);
                toolbarTextView.requestFocus();
                toolbarTextView.setSingleLine(true);
                toolbarTextView.setSelected(true);
                toolbarTextView.setMarqueeRepeatLimit(-1);

            } catch (NoSuchFieldException e) {

            } catch (IllegalAccessException e) {
            }


        }

    }

    public static boolean isFabAnimate(FabButton fabButton) {
        boolean progressVisible = false;
        if (fabButton != null) {
            try {
                Field f = fabButton.getClass().getDeclaredField("circle");
                f.setAccessible(true);
                CircleImageView circleImageView = (CircleImageView) f.get(fabButton);
                Field circleImageViewFField = circleImageView.getClass().getDeclaredField("progressVisible");
                circleImageViewFField.setAccessible(true);
                progressVisible = circleImageViewFField.getBoolean(circleImageView);
            } catch (NoSuchFieldException e) {

            } catch (IllegalAccessException e) {
            }


        }
        return progressVisible;
    }


    public static boolean checkWalleatBalance(Context context, EditText edtAmount, GetWalletDetailResponse walletDetailResponse) {

        boolean isValid = true;
        try {
            if (Double.parseDouble(Functions.toStingEditText(edtAmount)) > walletDetailResponse.getEffectiveBalance()) {

                isValid = false;
            }
        } catch (NumberFormatException e) {

            isValid = false;
        }
        return isValid;
    }

    public static void hideKeyPad(Context context, View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static PayPalConfiguration getPayPalConfig() {
        PayPalConfiguration config = new PayPalConfiguration()
                // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
                // or live (ENVIRONMENT_PRODUCTION)
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(PayPalConfig.PAYPAL_CLIENT_ID);
        return config;
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
