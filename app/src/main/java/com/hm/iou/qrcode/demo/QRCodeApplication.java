package com.hm.iou.qrcode.demo;

import android.app.Application;

import com.hm.iou.base.BaseBizAppLike;
import com.hm.iou.logger.Logger;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.network.HttpRequestConfig;
import com.hm.iou.router.Router;


/**
 * @author syl
 * @time 2018/5/14 下午3:23
 */
public class QRCodeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Router.init(this);
        BaseBizAppLike appLike = new BaseBizAppLike();
        appLike.onCreate(this);
        appLike.initServer("http://dev.54jietiao.com", "http://dev.54jietiao.com",
                "http://dev.54jietiao.com");
//        appLike.initServer("https://api.54jietiao.com", "https://api.54jietiao.com",
//                "https://h5.54jietiao.com");
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
                .setBaseUrl(BaseBizAppLike.getInstance().getApiServer())
                .build();
        HttpReqManager.init(config);
    }

}
