package com.hm.iou.scancodeandcard;

import android.app.Application;

import com.hm.iou.logger.Logger;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.network.HttpRequestConfig;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.UserInfo;


/**
 * @author syl
 * @time 2018/5/14 下午3:23
 */
public class QRCodeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init(this, true);
        initNetwork();
    }

    private void initNetwork() {
        System.out.println("init-----------");
        HttpRequestConfig config = new HttpRequestConfig.Builder(this)
                .setDebug(true)
                .setOsType("android")
                .setOsVersion("19")
                .setAppVersion("1.0.2")
                .setDeviceId("123abc123")
                .setBaseUrl("http://192.168.1.254")
                .build();
        HttpReqManager.init(config);
    }

}
