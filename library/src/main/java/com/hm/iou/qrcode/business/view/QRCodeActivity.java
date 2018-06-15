package com.hm.iou.qrcode.business.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * 权限请求页面
 *
 * @author syl
 * @time 2018/5/11 下午5:07
 */
public class QRCodeActivity<T extends MvpActivityPresenter> extends BaseActivity<T> {

    public static final String EXTRA_KEY_SHOW_TYPE = "show_type";

    //显示我的名片
    public static final String SHOW_TYPE_MY_CARD = "show_my_card";
    //显示扫码页面
    public static final String SHOW_TYPE_SCAN_CODE = "show_scan_code";
    private String mShowType;
    private String mScanCodeBeginUrl;


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected T initPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        Intent intent = getIntent();
        mShowType = intent.getStringExtra(EXTRA_KEY_SHOW_TYPE);
        if (bundle != null) {
            mShowType = bundle.getString(EXTRA_KEY_SHOW_TYPE);
        }
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            toRealQRCodeActivity();
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_SHOW_TYPE, mShowType);
    }

    private void toRealQRCodeActivity() {
        Intent intent = new Intent(QRCodeActivity.this, RealQRCodeActivity.class);
        intent.putExtra(EXTRA_KEY_SHOW_TYPE, mShowType);
        startActivity(intent);
        finish();
    }

}
