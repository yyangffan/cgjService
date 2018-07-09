package com.lhkj.cgjservice.ui.mine;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.BaseActivity;
import com.lhkj.cgjservice.databinding.ActivityShareBinding;
import com.lhkj.cgjservice.lock.AppBarLock;
import com.lhkj.cgjservice.utils.PixelUtil;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * Created by 浩琦 on 2017/6/22.
 */

public class ShareActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityShareBinding shareBinding= DataBindingUtil.setContentView(this, R.layout.activity_share);
        shareBinding.include6.setAppBarLock(new AppBarLock(this, R.string.myshare,R.mipmap.icon_back,R.string.myfen));

        String str = "https://www.baidu.com";
        if (str.equals("")) {
            Toast.makeText(ShareActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
        } else {
            // 位图
            try {
                /**
                 * 参数：1.文本 2 3.二维码的宽高 4.二维码中间的那个logo
                 */
                Bitmap bitmap = EncodingUtils.createQRCode(str, PixelUtil.dpToPx(this,150), PixelUtil.dpToPx(this,150), null);
                // 设置图片
                shareBinding.qrcode.setImageBitmap(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
