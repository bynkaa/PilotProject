package com.qsoft.pilotproject.ui.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.qsoft.pilotproject.ui.adapter.CropOption;
import com.qsoft.pilotproject.ui.adapter.CropOptionAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 11/8/13
 * Time: 2:20 PM
 */
@EBean
public class ProfileController {
    @RootContext
    Activity activity;

    Uri mImageCaptureUri;
    static final int PICK_FROM_CAMERA = 1;
    static final int CROP_FROM_CAMERA = 2;
    static final int PICK_FROM_FILE = 3;

    public Bitmap getBitmap(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = activity.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        doCrop(picturePath);
        return BitmapFactory.decodeFile(picturePath);
    }

    public void doCrop(String filePath) {
        try {
            //New Flow
            mImageCaptureUri = Uri.fromFile(new File(filePath));

            final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setType("image/*");
            List<ResolveInfo> list = activity.getPackageManager().queryIntentActivities(intent, 0);

            int size = list.size();
            if (size == 0) {
                Toast.makeText(activity, "Can not find image crop app", Toast.LENGTH_SHORT).show();
                return;
            } else {
                intent.setData(mImageCaptureUri);
                intent.putExtra("outputX", 300);
                intent.putExtra("outputY", 300);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);

                if (size == 1) {
                    Intent i = new Intent(intent);
                    ResolveInfo res = list.get(0);
                    i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    activity.startActivityForResult(i, CROP_FROM_CAMERA);
                } else {
                    for (ResolveInfo res : list) {
                        final CropOption co = new CropOption();
                        co.title = activity.getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                        co.icon = activity.getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                        co.appIntent = new Intent(intent);
                        co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                        cropOptions.add(co);
                    }

                    CropOptionAdapter adapter = new CropOptionAdapter(activity.getApplicationContext(), cropOptions);
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Choose Crop App");
                    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            activity.startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                        }
                    });

                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            if (mImageCaptureUri != null) {
                                activity.getContentResolver().delete(mImageCaptureUri, null, null);
                                mImageCaptureUri = null;
                            }
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        } catch (Exception ex) {
//            genHelper.showErrorLog("Error in Crop Function-->"+ex.toString());
        }
    }

}
