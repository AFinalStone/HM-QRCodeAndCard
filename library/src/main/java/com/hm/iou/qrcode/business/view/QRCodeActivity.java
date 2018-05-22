package com.hm.iou.qrcode.business.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hm.iou.base.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * 权限请求页面
 *
 * @author syl
 * @time 2018/5/11 下午5:07
 */
public class QRCodeActivity extends AppCompatActivity {

    public static final String EXTRA_KEY_SHOW_TYPE = "show_type";
    public static final String EXTRA_KEY_SCAN_CODE_BEGIN_URL = "scan_code_begin_url";

    //显示我的名片
    public static final String SHOW_TYPE_MY_CARD = "show_my_card";
    //显示扫码页面
    public static final String SHOW_TYPE_SCAN_CODE = "show_scan_code";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            String showType = getIntent().getStringExtra(EXTRA_KEY_SHOW_TYPE);
                            String scanCodeBeginUrl = getIntent().getStringExtra(EXTRA_KEY_SCAN_CODE_BEGIN_URL);
                            Intent intent = new Intent(QRCodeActivity.this, RealQRCodeActivity.class);
                            intent.putExtra(EXTRA_KEY_SHOW_TYPE, showType);
                            intent.putExtra(EXTRA_KEY_SCAN_CODE_BEGIN_URL, scanCodeBeginUrl);
                            startActivity(intent);
                            finish();
                        } else {
                            PermissionUtil.showCameraPermissionDialog(QRCodeActivity.this, new PermissionUtil.OnPermissionDialogClick() {
                                @Override
                                public void onPositiveBtnClick() {
                                    finish();
                                }

                                @Override
                                public void onNegativeBtnClick() {
                                    finish();
                                }
                            });
                        }
                    }
                });
    }
}
