package com.qsoft.pilotproject.activity;

import android.accounts.*;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.Service.Impl.OnlineDioServiceImpl;
import com.qsoft.pilotproject.Service.OnlineDioService;
import com.qsoft.pilotproject.authenticator.AccountGeneral;
import com.qsoft.pilotproject.authenticator.OnlineDioAuthenticator;
import com.qsoft.pilotproject.model.SignInDTO;
import com.qsoft.pilotproject.utils.Utilities;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 2:34 PM
 */
public class LoginFragment extends AccountAuthenticatorActivity
{
    private static final String TAG = "LoginActivity";
    public static final OnlineDioService onLineDioService = new OnlineDioServiceImpl();
    private static final String USER_ID = "UserId";
    private static final String ERROR_MESSAGE = "Error_Message";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String AUTHTOKEN_TYPE_FULL_ACCESS = "token_type";
    private static final String KEY_USER_PASSWORD = "user_pass";
    private ImageView imDone;
    private ImageView imBack;
    private EditText etEmail;
    private EditText etPassword;
    private TextView forgotPass;
    final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private AccountManager accountManager;
    private String authTokenType;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        accountManager = AccountManager.get(this);
        authTokenType = getIntent().getStringExtra(OnlineDioAuthenticator.AUTH_TYPE_KEY);
        if (authTokenType == null)
            authTokenType = AUTHTOKEN_TYPE_FULL_ACCESS;
        imBack = (ImageView) findViewById(R.id.login_ivBack);
        imDone = (ImageView) findViewById(R.id.login_ivLogin);
        imBack.setOnClickListener(btBackClickListener);
        imDone.setOnClickListener(btDoneClickListener);
        etEmail = (EditText) findViewById(R.id.login_etMail);
        etPassword = (EditText) findViewById(R.id.login_etPassword);
        forgotPass = (TextView) findViewById(R.id.login_tvForgotPass);
        forgotPass.setOnClickListener(btForgotPassListener);
        etEmail.addTextChangedListener(textChangeListener);
        etPassword.addTextChangedListener(textChangeListener);
    }

    private final TextWatcher textChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() )
            {
                imDone.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_btdone_invisible));
                imDone.setClickable(false);
            }else{
                imDone.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_btdone));
                imDone.setClickable(true);
            }
        }
    };

    View.OnClickListener btBackClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intentBack = new Intent(LoginFragment.this, LaunchActivity.class);
            startActivity(intentBack);
            Log.d(TAG, "come back to launch screen");
        }
    };

    View.OnClickListener btDoneClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if (isOnlineNetwork() && validateMailAndPassword(etEmail, etPassword))
            {
//
                final String email = etEmail.getText().toString();
                final String pass = Utilities.stringToMD5(etPassword.getText().toString());
                final String accountType = getIntent().getStringExtra(OnlineDioAuthenticator.ACCOUNT_TYPE_KEY);
                new AsyncTask<String,Void,Intent>(){

                    @Override
                    protected Intent doInBackground(String... strings) {
                        Log.d(TAG,"started authenticating ...");
                        Bundle data = new Bundle();
                        try{
                            SignInDTO signInDTO = onLineDioService.signIn(email,pass,authTokenType);
                            data.putInt(USER_ID,Integer.valueOf(signInDTO.getUser_id()));
                            data.putString(ACCESS_TOKEN,signInDTO.getAccess_token());
                            data.putString(AccountManager.KEY_ACCOUNT_NAME,email);
                            data.putString(AccountManager.KEY_ACCOUNT_TYPE,accountType);
                            data.putString(KEY_USER_PASSWORD,pass);
                        } catch (Exception e) {
                            data.putString(ERROR_MESSAGE,e.getMessage());
                        }
                        final Intent res = new Intent();
                        res.putExtras(data);
                        return res;
                    }

                    @Override
                    protected void onPostExecute(Intent intent)
                    {
                        if (intent.hasExtra(ERROR_MESSAGE))
                        {
                            Toast.makeText(getBaseContext(),intent.getStringExtra(ERROR_MESSAGE),Toast.LENGTH_LONG).show();
                        }
                        else {
                            finishLogin(intent);
                        }
                    }
                }.execute();
            }
        }
    };



    private void finishLogin(Intent intent) {
        Log.d(TAG,"finishLogin(intent)");
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(KEY_USER_PASSWORD);
        Account account = new Account(accountName,intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        if (getIntent().getBooleanExtra(OnlineDioAuthenticator.IS_ADDED_ACCOUNT_KEY,false))
        {
            Log.d(TAG, "finishLogin > addAccountExplicitly");
            String authToken = intent.getStringExtra(AccountManager.KEY_INTENT);
            accountManager.addAccountExplicitly(account,accountPassword,null);
            accountManager.setAuthToken(account,authTokenType,authToken);
        }
        else{
            Log.d(TAG, "finish Login > set password");
            accountManager.setPassword(account,accountPassword);
        }
        setAccountAuthenticatorResult(intent.getExtras());
        Intent slideBarIntent = new Intent(LoginFragment.this, SlideBar.class);
        startActivity(slideBarIntent);
        Log.d(TAG, "Login successfully");
    }

    View.OnClickListener btForgotPassListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            AlertDialog dialog = showAlertDialogResetPassword("Forgot Password", "To reset your password, please enter your" +
                    " email address");
        }
    };


    private boolean isOnlineNetwork()
    {
        // checkTimeoutService();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            Log.d(TAG, "network available");
            return true;
        }
        else
        {
            AlertDialog dialog = showAlertDialog("Error Signing In", "There is no connection to the internet.");
            dialog.show();
            Log.d(TAG, "network no connection");
            return false;
        }
    }

    private void checkTimeoutService()
    {
        HttpGet httpGet = new HttpGet("http://www.google.com");
        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 15000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 15000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
        try
        {
            Log.d(TAG, "Checking connection...");
            httpClient.execute(httpGet);
            Log.d(TAG, "request service successfully");
            return;
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Log.d(TAG, "Connection timeout");
    }

    private boolean validateMailAndPassword(EditText mail, EditText password)
    {
        String _mail = mail.getText().toString();
        String _password = password.getText().toString();
        if (_mail.matches(EMAIL_PATTERN) == false)
        {
            AlertDialog dialog = showAlertDialog("Error Signing In", "Email address is incorrect.");
            dialog.show();
            mail.requestFocus();
            return false;
        }
        else if (_password.length() <= 0)
        {
            AlertDialog dialog = showAlertDialog("Error Signing In", "Password is incorrect.");
            dialog.show();
            password.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }

    private AlertDialog showAlertDialog(String txtTitle, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TextView title = new TextView(this);
        title.setText(txtTitle);
        title.setTextSize(20);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        builder.setCustomTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        return dialog;
    }

    private AlertDialog showAlertDialogResetPassword(String txtTitle, String message)
    {
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
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
            }
        });
        builder.setNegativeButton("Reset", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                String _emailAddress = emailAddress.getText().toString();
                if (_emailAddress.matches(EMAIL_PATTERN) == false)
                {
                    AlertDialog dialogError = showAlertDialog("Request Error", "Invalid email address");
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