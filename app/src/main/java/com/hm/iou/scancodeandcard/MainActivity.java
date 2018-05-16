package com.hm.iou.scancodeandcard;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hm.iou.base.utils.PermissionUtil;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.qrcode.business.view.QRCodeActivity;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.hm.iou.tools.ToastUtil;
import com.sina.weibo.sdk.utils.MD5;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 打开扫码页面
     *
     * @param view
     */
    public void onClickScanCode(View view) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Intent intent = new Intent(MainActivity.this, QRCodeActivity.class);
                            intent.putExtra(QRCodeActivity.EXTRA_KEY_SHOW_TYPE, QRCodeActivity.SHOW_TYPE_SCAN_CODE);
                            intent.putExtra(QRCodeActivity.EXTRA_KEY_SCAN_CODE_BEGIN_URL, "http://192.168.1.254");
                            startActivity(intent);
                        } else {
                            PermissionUtil.showCameraPermissionDialog(MainActivity.this);
                        }
                    }
                });
    }

    /**
     * 打开我的名片
     *
     * @param view
     */
    public void onClickMyCard(View view) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Intent intent = new Intent(MainActivity.this, QRCodeActivity.class);
                            intent.putExtra(QRCodeActivity.EXTRA_KEY_SHOW_TYPE, QRCodeActivity.SHOW_TYPE_MY_CARD);
                            intent.putExtra(QRCodeActivity.EXTRA_KEY_SCAN_CODE_BEGIN_URL, "http://192.168.1.254");
                            startActivity(intent);
                        } else {
                            PermissionUtil.showCameraPermissionDialog(MainActivity.this);
                        }
                    }
                });

    }

    public void onClickLogin(View view) {
        String pwd = MD5.hexdigest("123456".getBytes());
        HttpReqManager.getInstance().getService(LoginService.class)
                .mobileLogin("15267163669", pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<UserInfo>>() {
                    @Override
                    public void accept(BaseResponse<UserInfo> userInfoBaseResponse) throws Exception {
                        ToastUtil.showMessage(MainActivity.this, "登录成功");
                        UserInfo userInfo = userInfoBaseResponse.getData();
                        UserManager.getInstance(MainActivity.this).updateOrSaveUserInfo(userInfo);
                        HttpReqManager.getInstance().setUserId(userInfo.getUserId());
                        HttpReqManager.getInstance().setToken(userInfo.getToken());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable t) throws Exception {
                        t.printStackTrace();
                    }
                });
    }


}
