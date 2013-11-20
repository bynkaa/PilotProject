package com.qsoft.pilotproject.ui.activity;

import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.common.CommandExecutor;
import com.qsoft.pilotproject.common.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.common.authenticator.OnlineDioAuthenticator;
import com.qsoft.pilotproject.common.commands.GenericStartActivityCommand;
import com.qsoft.pilotproject.common.utils.Utilities;
import com.qsoft.pilotproject.data.model.dto.SignInDTO;
import com.qsoft.pilotproject.data.rest.OnlineDioClientProxy;
import com.qsoft.pilotproject.handler.AuthenticatorHandler;
import com.qsoft.pilotproject.handler.impl.AuthenticatorHandlerImpl;
import com.qsoft.pilotproject.ui.controller.LoginController;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 2:34 PM
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends AccountAuthenticatorActivity {
    private static final int RC_LAUCH_ACTIVITY = 1;
    private static final int RC_SLIDE_BAR_ACTIVITY = 2;
    @Bean(AuthenticatorHandlerImpl.class)
    public AuthenticatorHandler onLineDioService;

    static final String TAG = "LoginActivity";
    static final String ERROR_MESSAGE = "Error_Message";
    static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    static final String AUTHTOKEN_TYPE_FULL_ACCESS = "token_type";
    static final String KEY_USER_PASSWORD = "user_pass";
    public static final String USER_ID_KEY = "user_id";

    @ViewById(R.id.login_ivLogin)
    ImageView imDone;

    @ViewById(R.id.login_ivBack)
    ImageView imBack;

    @ViewById(R.id.login_etMail)
    EditText etEmail;

    @ViewById(R.id.login_etPassword)
    EditText etPassword;

    @ViewById(R.id.login_tvForgotPass)
    TextView forgotPass;
    @Bean
    CommandExecutor commandExecutor;


    @SystemService
    AccountManager accountManager;

    String authTokenType;

    @Bean
    ApplicationAccountManager applicationAccountManager;

    @Bean
    OnlineDioClientProxy onlineDioClientProxy;

    @Bean
    LoginController loginController;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void afterViews() {
        authTokenType = getIntent().getStringExtra(OnlineDioAuthenticator.AUTH_TYPE_KEY);
        if (authTokenType == null) {
            authTokenType = AUTHTOKEN_TYPE_FULL_ACCESS;
        }
    }

    @AfterTextChange({R.id.login_etMail, R.id.login_etPassword})
    void handleTextChangeEmail() {
        if (etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
            imDone.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_btdone_invisible));
            imDone.setClickable(false);
        } else {
            imDone.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_btdone));
            imDone.setClickable(true);
        }
    }

    @Click(R.id.login_ivBack)
    void doClickBack() {
        commandExecutor.execute(this,
                new GenericStartActivityCommand(this, LaunchActivity_.class, RC_LAUCH_ACTIVITY) {
                    @Override
                    public void overrideExtra(Intent intent) {
                    }
                }, false);

        Log.d(TAG, "come back to launch screen");
    }

    @Click(R.id.login_ivLogin)
    void doClickLogin() {
        if (loginController.isOnlineNetwork() && loginController.validateMailAndPassword(etEmail, etPassword)) {
//
            final String email = etEmail.getText().toString();
            final String pass = Utilities.stringToMD5(etPassword.getText().toString());
            final String accountType = getIntent().getStringExtra(OnlineDioAuthenticator.ACCOUNT_TYPE_KEY);
            doSignInService(email, pass, accountType);
        }
    }

    @Background
    void doSignInService(String email, String pass, String accountType) {
        Log.d(TAG, "started authenticating ...");
        Bundle data = new Bundle();
        try {
            SignInDTO signInDTO = onLineDioService.signIn(email, pass, authTokenType);
//            SignInDTO signInDTO = onlineDioClientProxy.signIn(email,pass);
            if (signInDTO == null) {
                throw new Exception();
            }
            data.putLong(USER_ID_KEY, Long.valueOf(signInDTO.getUserId()));
            data.putString(AccountManager.KEY_AUTHTOKEN, signInDTO.getAccessToken());
            data.putString(AccountManager.KEY_ACCOUNT_NAME, email);
            data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
            data.putString(KEY_USER_PASSWORD, pass);
        } catch (Exception e) {
            data.putString(ERROR_MESSAGE, "User and password are incorrect!");
        }
        final Intent res = new Intent();
        res.putExtras(data);
        updateLogin(res);
    }

    @UiThread
    void updateLogin(Intent intent) {
        if (intent.hasExtra(ERROR_MESSAGE)) {
            Toast.makeText(getBaseContext(), intent.getStringExtra(ERROR_MESSAGE), Toast.LENGTH_LONG).show();
        } else {
            loginController.finishLogin(intent);
        }

    }

    @Click(R.id.login_tvForgotPass)
    void doClickForgetPassword() {
        AlertDialog dialog = showAlertDialogResetPassword("Forgot Password", "To reset your password, please enter your" +
                " email address");
    }

    void checkTimeoutService() {
        HttpGet httpGet = new HttpGet("http://www.google.com");
        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 15000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 15000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
        try {
            Log.d(TAG, "Checking connection...");
            httpClient.execute(httpGet);
            Log.d(TAG, "request service successfully");
            return;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Connection timeout");
    }

    AlertDialog showAlertDialogResetPassword(String txtTitle, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText emailAddress = new EditText(this);
        final TextView title = new TextView(this);
        emailAddress.setHint("Email Address");
        builder.setView(emailAddress);
        title.setText(txtTitle);
        title.setTextSize(20);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        builder.setCustomTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Reset", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String _emailAddress = emailAddress.getText().toString();
                if (_emailAddress.matches(LoginController.EMAIL_PATTERN) == false) {
                    AlertDialog dialogError = loginController.showAlertDialog("Request Error", "Invalid email address");
                    dialogError.show();
                    etEmail.requestFocus();
                }
            }
        });
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        return dialog;
    }
}