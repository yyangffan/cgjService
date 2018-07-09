package com.lhkj.cgjservice.ui.mine;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.BaseActivity;
import com.lhkj.cgjservice.databinding.ActivityMineBinding;
import com.lhkj.cgjservice.entity.Operation;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.lock.MineLock;
import com.lhkj.cgjservice.reponse.UserReponse;
import com.lhkj.cgjservice.utils.BitmapUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/26.
 */

public class MineActivity extends BaseActivity {
    public String iconPath;
    private Uri corpUri;
    public File corpFile;
    private View view;
    private MineLock mineLock;
    private Drawable icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMineBinding mineBinding = DataBindingUtil.setContentView(this, R.layout.activity_mine);
        mineLock = new MineLock(this, mineBinding);
        mineBinding.setMineLock(mineLock);
    }

    public void exchangeHead(View view) {
        startActivityForResult(new Intent(this, SelectPicPopupWindow.class), 200);
        this.view = view;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (User.getUser().isLogin) {

        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            corpUri = data.getData();
            Bitmap photo = decodeUriAsBitmap(corpUri, false);
            photo = BitmapUtils.makeRoundCorner(photo);
            corpUri = Uri.fromFile(saveFile(photo, "img_name.png"));
            iconPath = getRealFilePath(this, corpUri);
            ((ImageView) view).setImageBitmap(photo);
            if (iconPath != null) {
                userPush();
            }
        }
    }

    private void userPush() {
        HashMap hashMap = new HashMap();
        hashMap.put("s_id", User.getUser().sId);
        File file = new File(iconPath);
        hashMap.put("img_name", file);
        HttpUtil.getInstance().formRequset(Operation.UPLOAD_HEAD, hashMap, UserReponse.class, new HttpCallListener<UserReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(final String URL, @NonNull final UserReponse bean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            Toast.makeText(MineActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
//                            User.getUser().initUser(bean);
                            Glide.with(MineActivity.this).asDrawable().load(bean.info.head_url).into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                    User.getUser().userIcon = resource;
                                    icon = resource;
                                }
                            });
//                            mineLock.fulsh();
                        } else {
                            Toast.makeText(MineActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {

            }
        });
    }


    public Bitmap decodeUriAsBitmap(Uri uri, boolean type) {
        Bitmap bitmap = null;
        try {
            if (type) {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } else {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            }
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public File saveFile(Bitmap bm, String fileName) {
        String path = getExternalCacheDir().getPath();
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + File.separator + fileName);
        BufferedOutputStream bos = null;
        try {
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCaptureFile;
    }
}
