package com.hm.iou.qrcode.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hm.iou.demo.R;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.qrcode.business.view.QRCodeActivity;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;
import com.hm.iou.tools.Md5Util;
import com.hm.iou.tools.ToastUtil;
import com.sina.weibo.sdk.utils.MD5;

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
        Intent intent = new Intent(MainActivity.this, QRCodeActivity.class);
        intent.putExtra(QRCodeActivity.EXTRA_KEY_SHOW_TYPE, QRCodeActivity.SHOW_TYPE_SCAN_CODE);
        intent.putExtra(QRCodeActivity.EXTRA_KEY_SCAN_CODE_BEGIN_URL, "http://192.168.1.254");
        startActivity(intent);
    }

    /**
     * 打开我的名片
     *
     * @param view
     */
    public void onClickMyCard(View view) {
        Intent intent = new Intent(MainActivity.this, QRCodeActivity.class);
        intent.putExtra(QRCodeActivity.EXTRA_KEY_SHOW_TYPE, QRCodeActivity.SHOW_TYPE_MY_CARD);
        intent.putExtra(QRCodeActivity.EXTRA_KEY_SCAN_CODE_BEGIN_URL, "http://192.168.1.254");
        startActivity(intent);
    }

    public void onClickLogin(View view) {
        String pwd = Md5Util.getMd5ByString("123456");
        MobileLoginReqBean reqBean = new MobileLoginReqBean();
        reqBean.setMobile("15267163669");
        reqBean.setQueryPswd(pwd);
        HttpReqManager.getInstance().getService(LoginService.class)
                .mobileLogin(reqBean)
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
