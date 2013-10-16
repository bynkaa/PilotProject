package com.qsoft.pilotproject.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.PilotProject.R;

/**
 * User: thanhtd
 * Date: 10/14/13
 * Time: 3:48 PM
 */
public class ProfileSetupFragment extends FragmentActivity
{
    final Context context = this;
    private ImageView btArrowCountry;
    private EditText etCountry;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup);
        etCountry = (EditText)findViewById(R.id.profile_et_country);
        btArrowCountry = (ImageView)findViewById(R.id.profile_iv_bt_arrow);
        btArrowCountry.setOnClickListener(btArrowCountryListener);
    }

    View.OnClickListener btArrowCountryListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            viewListCountry();
        }
    };

    private void viewListCountry() {
        final String[] countryList = getResources().getStringArray(R.array.country);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("list country");

        builder.setSingleChoiceItems(countryList,-1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                etCountry.setText(countryList[which]);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        builder.show();
    }
}
