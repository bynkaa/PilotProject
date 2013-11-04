package com.qsoft.pilotproject.ui;

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
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.adapter.CropOption;
import com.qsoft.pilotproject.adapter.CropOptionAdapter;
import com.qsoft.pilotproject.authenticator.AccountGeneral;
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
public class ProfileSetupActivity extends FragmentActivity
{
    private static final String TAG = "ProfileSetupActivity";
    final Context context = this;
    static final int DATE_DIALOG_ID = 999;
    private static int RESULT_LOAD_IMAGE = 1;
    private int year;
    private int month;
    private int day;
    private DatePicker dpResult;
    private RelativeLayout rlCover;
    private ImageView ivProfile;
    private EditText tvBirthday;
    private EditText tvCountry;
    private TextView tvProfileName;
    private ImageButton ibLeft;
    private ImageButton ibRight;
    private Boolean flag = null;
    private ScrollView svDescription;
    private EditText etDescription;
    private ImageView ibProfileCancel;
    private ImageView ibProfileSave;
    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    AccountManager accountManager;
    Account account;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup);
        accountManager = AccountManager.get(this);
        account =  accountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)[0];
        tvBirthday = (EditText) findViewById(R.id.profile_et_birthday);
        dpResult = (DatePicker) findViewById(R.id.dpResult);
        rlCover = (RelativeLayout) findViewById(R.id.profile_relativeLayout);
        rlCover.setOnClickListener(ivCoverListener);
        ivProfile = (ImageView) findViewById(R.id.profile_iv_icon);
        ivProfile.setOnClickListener(ivProfileListener);
        tvBirthday.setOnClickListener(tvBirthdayListener);
        ibLeft = (ImageButton) findViewById(R.id.profile_ibleft);
        ibLeft.setSelected(true);
        ibRight = (ImageButton) findViewById(R.id.profile_ibright);
        ibRight.setSelected(false);
        ibLeft.setOnClickListener(ibLeftListener);
        ibRight.setOnClickListener(ibRightListener);
        tvCountry = (EditText) findViewById(R.id.profile_et_country);
        tvCountry.setOnClickListener(btArrowCountryListener);
        etDescription = (EditText) findViewById(R.id.profile_et_desciption);
        ibProfileCancel = (ImageView) findViewById(R.id.ibProfileCancel);
        ibProfileCancel.setOnClickListener(ibProfileCancelOnClickListener);
        ibProfileSave = (ImageView) findViewById(R.id.ibProfileSave);
        ibProfileSave.setOnClickListener(ibProfileSaveOnClickListener);
        tvProfileName = (TextView) findViewById(R.id.tv_profile_name);

        setupData();
    }

    private void setupData()
    {
        final ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(OnlineDioContract.Profile.CONTENT_URI,null,null,null,null);
        if (cursor.getCount() == 0)
        {
            new AsyncTask<Void, Void, ProfileDTO>()
            {
                Long userId = 0l;
                @Override
                protected ProfileDTO doInBackground(Void... voids)
                {
                    String userIdStr = accountManager.getUserData(account,LoginActivity.USER_ID_KEY);
                    ProfileHandler profileHandler = new ProfileHandlerImpl(accountManager,account);
                    userId = Long.valueOf(userIdStr);
                    ProfileDTO profileDTO = profileHandler.getProfile(userId);
                    return profileDTO;
                }

                @Override
                protected void onPostExecute(ProfileDTO profileDTO)
                {
                    Uri singleUri = ContentUris.withAppendedId(OnlineDioContract.Profile.CONTENT_URI, userId);
                    contentResolver.insert(singleUri,profileDTO.getContentValues());
                    setToView(profileDTO);
                }
            };
        }
        while (cursor.moveToNext()){
            setToView(Profile.fromCursor(cursor));
        }

    }

    private void setToView(ProfileDTO profileDTO)
    {
        tvBirthday.setText(profileDTO.getBirthday());
        tvCountry.setText(profileDTO.getCountryId());

    }

    View.OnClickListener ibProfileSaveOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Log.d(TAG, "save ok");
            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
            finish();
        }
    };
    View.OnClickListener ibProfileCancelOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Log.d(TAG, "cancel ok");
            Intent intent = getIntent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    };

    View.OnClickListener ibLeftListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if (ibLeft.isSelected())
            {

            }
            else
            {
                ibLeft.setSelected(true);
                ibLeft.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.profile_btn_select_left));
                ibRight.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.profile_btn_unselect_right));
                ibRight.setSelected(false);
            }
        }
    };

    View.OnClickListener ibRightListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if (ibRight.isSelected())
            {

            }
            else
            {
                ibRight.setSelected(true);
                ibRight.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.profile_btn_select_right));
                ibLeft.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.profile_btn_unselect_left));
                ibLeft.setSelected(false);
            }
        }
    };

    View.OnClickListener ivCoverListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            flag = true;
            uploadImage();
        }
    };

    View.OnClickListener ivProfileListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            flag = false;
            uploadImage();
        }
    };

    private void uploadImage()
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

    private void setImageProfile(Bitmap bmImg)
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

    private Bitmap getBitmap(Intent data)
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

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener()
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

    private void viewListCountry()
    {
        final String[] countryList = getResources().getStringArray(R.array.country);
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
