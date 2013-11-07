package com.qsoft.pilotproject.ui.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.*;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.*;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.adapter.CropOption;
import com.qsoft.pilotproject.adapter.CropOptionAdapter;
import com.qsoft.pilotproject.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.common.SuperAnnotationActivity;
import com.qsoft.pilotproject.handler.ProfileHandler;
import com.qsoft.pilotproject.handler.impl.ProfileHandlerImpl;
import com.qsoft.pilotproject.model.Profile;
import com.qsoft.pilotproject.model.dto.ProfileDTO;
import com.qsoft.pilotproject.provider.OnlineDioContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    static int RESULT_LOAD_IMAGE = 1;
    int year;
    int month;
    int day;
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
    Boolean flag = null;
    ScrollView svDescription;
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
    Uri mImageCaptureUri;
    static final int PICK_FROM_CAMERA = 1;
    static final int CROP_FROM_CAMERA = 2;
    static final int PICK_FROM_FILE = 3;


    @SystemService
    AccountManager accountManager;

    Account account;
    @Bean
    ApplicationAccountManager applicationAccountManager;

    String[] countryList;
    String[] countryCodes;

    @AfterViews
    void setupData()
    {
        account = applicationAccountManager.getAccount();
        countryList = getResources().getStringArray(R.array.country);
        countryCodes = getResources().getStringArray(R.array.country_codes);
        final ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(OnlineDioContract.Profile.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0)
        {
            Log.d(TAG, "get profile from server and push to local");
            new AsyncTask<Void, Void, ProfileDTO>()
            {
                Long userId = 0l;

                @Override
                protected ProfileDTO doInBackground(Void... voids)
                {
                    Log.d(TAG, "doInBackground: ");
                    String userIdStr = accountManager.getUserData(account, LoginActivity.USER_ID_KEY);
                    ProfileHandler profileHandler = new ProfileHandlerImpl(accountManager, account);
                    userId = Long.valueOf(userIdStr);
                    ProfileDTO profileDTO = profileHandler.getProfile(userId);
                    return profileDTO;
                }

                @Override
                protected void onPostExecute(ProfileDTO profileDTO)
                {
//                    Uri singleUri = ContentUris.withAppendedId(OnlineDioContract.Profile.CONTENT_URI, userId);
                    contentResolver.insert(OnlineDioContract.Profile.CONTENT_URI, profileDTO.getContentValues());
                    setToView(profileDTO);
                }
            }.execute();
        }
        else
        {
            while (cursor.moveToNext())
            {
                setToView(Profile.fromCursor(cursor));
            }
        }


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data && flag == true)
        {
            Bitmap bmImg = getBitmap(data);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bmImg, rlCover.getWidth(), rlCover.getHeight(), true);
            Drawable drawable = new BitmapDrawable(bMapScaled);
            rlCover.setBackgroundDrawable(drawable);
        }
        else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data && flag == false)
        {
            Bitmap bmImg = getBitmap(data);
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

    Bitmap getBitmap(Intent data)
    {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
//        return bitmap;
        doCrop(picturePath);
        return BitmapFactory.decodeFile(picturePath);
    }

    public void doCrop(String filePath)
    {
        try
        {
            //New Flow
            mImageCaptureUri = Uri.fromFile(new File(filePath));

            final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setType("image/*");
            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);

            int size = list.size();
            if (size == 0)
            {
                Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                intent.setData(mImageCaptureUri);
                intent.putExtra("outputX", 300);
                intent.putExtra("outputY", 300);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);

                if (size == 1)
                {
                    Intent i = new Intent(intent);
                    ResolveInfo res = list.get(0);
                    i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    startActivityForResult(i, CROP_FROM_CAMERA);
                }

                else
                {
                    for (ResolveInfo res : list)
                    {
                        final CropOption co = new CropOption();
                        co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                        co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                        co.appIntent = new Intent(intent);
                        co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                        cropOptions.add(co);
                    }

                    CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Choose Crop App");
                    builder.setAdapter(adapter, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int item)
                        {
                            startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                        }
                    });

                    builder.setOnCancelListener(new DialogInterface.OnCancelListener()
                    {
                        public void onCancel(DialogInterface dialog)
                        {
                            if (mImageCaptureUri != null)
                            {
                                getContentResolver().delete(mImageCaptureUri, null, null);
                                mImageCaptureUri = null;
                            }
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        }
        catch (Exception ex)
        {
//            genHelper.showErrorLog("Error in Crop Function-->"+ex.toString());
        }
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
