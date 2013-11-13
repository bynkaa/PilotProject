package com.qsoft.pilotproject.ui.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.*;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.common.SuperAnnotationActivity;
import com.qsoft.pilotproject.imageloader.ImageLoader;
import com.qsoft.pilotproject.model.Profile;
import com.qsoft.pilotproject.model.dto.ProfileDTO;
import com.qsoft.pilotproject.provider.OnlineDioContract;
import com.qsoft.pilotproject.rest.OnlineDioClientProxy;
import com.qsoft.pilotproject.ui.controller.ProfileController;

/**
 * User: thanhtd
 * Date: 10/14/13
 * Time: 3:48 PM
 */
@EActivity(R.layout.profile_setup)
public class ProfileSetupActivity extends SuperAnnotationActivity
{
    final Context context = this;
    static final int DATE_DIALOG_ID = 999;
    static final int RESULT_LOAD_IMAGE = 1;
    int year;
    int month;
    int day;
    Boolean flag = null;

    @ViewById(R.id.dpResult)
    DatePicker dpResult;
    @ViewById(R.id.profile_relativeLayout)
    RelativeLayout rlCover;
    @ViewById(R.id.profile_iv_icon)
    ImageView ivProfile;
    @ViewById(R.id.profile_et_birthday)
    EditText tvBirthday;
    @ViewById(R.id.profile_et_country)
    EditText tvCountry;
    @ViewById(R.id.tv_profile_name)
    TextView tvProfileName;
    @ViewById(R.id.profile_et_desciption)
    EditText etDescription;
    @ViewById(R.id.ibProfileCancel)
    ImageView ibProfileCancel;
    @ViewById(R.id.ibProfileSave)
    ImageView ibProfileSave;
    @ViewById(R.id.profile_et_displayname)
    TextView etDisplayName;
    @ViewById(R.id.profile_et_name)
    EditText etFullName;
    @ViewById(R.id.et_profile_phone)
    EditText etPhone;
    @ViewById(R.id.tv_profile_gender)
    TextView tvGender;
    @ViewById(R.id.profile_ibleft)
    ImageButton imFemale;
    @ViewById(R.id.profile_ibright)
    ImageButton imMale;

    ImageLoader imageLoader;


    @SystemService
    AccountManager accountManager;

    @Bean
    ProfileController profileController;

    @Bean
    OnlineDioClientProxy onlineDioClientProxy;

    Account account;
    @Bean
    ApplicationAccountManager applicationAccountManager;

    String[] countryList;
    String[] countryCodes;
    ContentResolver contentResolver;

    @AfterViews
    void setupData()
    {
//        imageLoader = new ImageLoader(getApplicationContext());
        account = applicationAccountManager.getAccount();
        countryList = getResources().getStringArray(R.array.country);
        countryCodes = getResources().getStringArray(R.array.country_codes);
        contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(OnlineDioContract.Profile.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0)
        {
            Log.d(TAG, "get profile from server and push to local");
            doGetProfileFromService();
        }
        else
        {
            while (cursor.moveToNext())
            {
                setToView(Profile.fromCursor(cursor));
            }
        }
    }

    @Background
    void doGetProfileFromService()
    {
        Log.d(TAG, "doInBackground: ");
        String userIdStr = accountManager.getUserData(account, LoginActivity.USER_ID_KEY);
        long userId = Long.valueOf(userIdStr);
        ProfileDTO profileDTO = onlineDioClientProxy.getProfile(userId);
        updateProfileUI(profileDTO);
    }

    @UiThread
    void updateProfileUI(ProfileDTO profileDTO)
    {
        contentResolver.insert(OnlineDioContract.Profile.CONTENT_URI, profileDTO.getContentValues());
        setToView(profileDTO);
    }

    void setToView(ProfileDTO profileDTO)
    {
        tvBirthday.setText(profileDTO.getBirthday());
        etDisplayName.setText(profileDTO.getDisplayName());
        etFullName.setText(profileDTO.getFullName());
        etPhone.setText(profileDTO.getPhone());
        etDescription.setText(profileDTO.getDescription());
        if (profileDTO.getGender() == 0)
        {
            imFemale.performClick();
        }
        else
        {
            imMale.performClick();
        }
        if (profileDTO.getCountryId() != null && !profileDTO.getCountryId().isEmpty())
        {
            int index = -1;

            for (int i = 0; i < countryCodes.length; i++)
            {
                if (countryCodes[i].equals(profileDTO.getCountryId()))
                {
                    index = i;
                    break;
                }
            }
            if (index != -1)
            {
                tvCountry.setText(countryList[index]);
            }
        }
        // load image
        if (profileDTO.getAvatar() != null)
        {
//            Bitmap imageAvatar = imageLoader.getBitmap(profileDTO.getAvatar(), R.drawable.profile_icon);
//            setImageProfile(imageAvatar);
        }

        if (profileDTO.getCoverImage() != null)
        {
            Bitmap imageCover = imageLoader.getBitmap(profileDTO.getCoverImage(), R.drawable.profile_cover);
            Drawable d = new BitmapDrawable(getResources(), imageCover);
            rlCover.setBackground(d);
        }
    }


    @Click(R.id.ibProfileSave)
    void doClickProfileSaveButton()
    {
        Log.d(TAG, "save ok");
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Click(R.id.ibProfileCancel)
    void doClickProfileCancel()
    {
        Log.d(TAG, "cancel ok");
        Intent intent = getIntent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Click(R.id.profile_ibleft)
    void doClickProfileFemale()
    {
        if (imFemale.isSelected())
        {

        }
        else
        {
            imFemale.setSelected(true);
            imFemale.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.profile_btn_select_left));
            imMale.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.profile_btn_unselect_right));
            imMale.setSelected(false);
        }
    }

    @Click(R.id.profile_ibright)
    void doClickProfileMale()
    {
        if (imMale.isSelected())
        {

        }
        else
        {
            imMale.setSelected(true);
            imMale.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.profile_btn_select_right));
            imFemale.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.profile_btn_unselect_left));
            imFemale.setSelected(false);
        }

    }


    @Click(R.id.profile_relativeLayout)
    void doClickCover()
    {
        flag = true;
        uploadImage();
    }

    @Click(R.id.profile_iv_icon)
    void doClickProfileIcon()
    {
        flag = false;
        uploadImage();

    }

    void uploadImage()
    {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }


    @OnActivityResult(RESULT_LOAD_IMAGE)
    void onResult(int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK && null != data && flag == true)
        {
            Bitmap bmImg = profileController.getBitmap(data);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bmImg, rlCover.getWidth(), rlCover.getHeight(), true);
            Drawable drawable = new BitmapDrawable(bMapScaled);
            rlCover.setBackgroundDrawable(drawable);
        }
        else if (resultCode == RESULT_OK && null != data && flag == false)
        {
            Bitmap bmImg = profileController.getBitmap(data);
            setImageProfile(bmImg);
        }
    }

    void setImageProfile(Bitmap bmImg)
    {
        Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.profile_mask);

        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);

        Bitmap photoBitmapScale = Bitmap.createScaledBitmap(bmImg, mask.getWidth(), mask.getHeight(), false);

        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(photoBitmapScale, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        ivProfile.setImageBitmap(result);
        ivProfile.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ivProfile.setBackgroundResource(R.drawable.profile_frame);
    }

    @Click(R.id.profile_tv_birthday)
    void doClickBirthday()
    {
        showDialog(DATE_DIALOG_ID);
    }

    @Click(R.id.profile_et_country)
    void doClickCountry()
    {
        viewListCountry();
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
        }
        return null;
    }

    DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener()
    {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay)
        {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            tvBirthday.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
            dpResult.init(year, month, day, null);
        }
    };

    void viewListCountry()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("list country");

        builder.setSingleChoiceItems(countryList, -1, new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                tvCountry.setText(countryList[which]);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
            }
        });
        builder.show();
    }
}
