package com.qsoft.pilotproject.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.PilotProject.R;

/**
 * User: thanhtd
 * Date: 10/14/13
 * Time: 3:48 PM
 */
public class ProfileSetupFragment extends FragmentActivity
{
    final Context context = this;
    static final int DATE_DIALOG_ID = 999;
    private int year;
    private int month;
    private int day;
    private DatePicker dpResult;
    private TextView tvBirthday;
    private ImageView btArrowCountry;
    private EditText etBirthday;
    private EditText etCountry;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup);
        etBirthday = (EditText)findViewById(R.id.profile_et_birthday);
        tvBirthday = (TextView) findViewById(R.id.profile_tv_birthday);
        dpResult = (DatePicker) findViewById(R.id.dpResult);
        tvBirthday.setOnClickListener(tvBirthdayListener);
        etCountry = (EditText)findViewById(R.id.profile_et_country);
        btArrowCountry = (ImageView)findViewById(R.id.profile_iv_bt_arrow);
        btArrowCountry.setOnClickListener(btArrowCountryListener);
    }

    View.OnClickListener tvBirthdayListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            showDialog(DATE_DIALOG_ID);
        }
    };

    View.OnClickListener btArrowCountryListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            viewListCountry();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            etBirthday.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
            // set selected date into datepicker also
            dpResult.init(year, month, day, null);

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
