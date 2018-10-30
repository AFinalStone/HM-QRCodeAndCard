package com.hm.iou.qrcode.demo;

import android.app.Application;

import com.hm.iou.base.BaseBizAppLike;
import com.hm.iou.logger.Logger;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.network.HttpRequestConfig;


/**
 * @author syl
 * @time 2018/5/14 下午3:23
 */
public class QRCodeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseBizAppLike appLike = new BaseBizAppLike();
        appLike.onCreate(this);
        appLike.initServer("http://192.168.1.254:8071", "http://192.168.1.217",
                "http://192.168.1.217");
        Logger.init(this, true);
        initNetwork();
    }

    private void initNetwork() {
        System.out.println("init-----------");
        HttpRequestConfig config = new HttpRequestConfig.Builder(this)
                .setDebug(true)
                .setAppChannel("yyb")
                .setAppVersion("1.0.2")
                .setDeviceId("123abc123")
                .setBaseUrl("https://api.54jietiao.com")
                .build();
        HttpReqManager.init(config);
    }

}
