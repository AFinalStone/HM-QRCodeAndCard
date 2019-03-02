package com.hm.iou.qrcode.api;

import com.hm.iou.network.HttpReqManager;
import com.hm.iou.qrcode.bean.IOUBriefMoney;
import com.hm.iou.sharedata.model.BaseResponse;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author syl
 * @time 2018/5/13 下午5:52
 */
public class QRCodeApi {

    private static QRCodeService getService() {
        return HttpReqManager.getInstance().getService(QRCodeService.class);
    }


    /**
     * 通过公正id查询电子借条或者电子收条
     *
     * @param justiceId
     * @return
     */
    public static Flowable<BaseResponse<IOUBriefMoney>> searchIOUById(String justiceId) {
        return getService().searchIOUById(justiceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 解析短连接
     *
     * @param shortUrl
     * @return
     */
    public static Flowable<BaseResponse<String>> parseShortUrl(String shortUrl) {
        shortUrl += "?type=1";      //type = 1，表示请求来自APP
        return getService().parseShortUrl(shortUrl).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * APP扫码后确认登录
     *
     * @param ip
     * @param uuid
     * @return
     */
    public static Flowable<BaseResponse<String>> doConfirmLogin(String ip, String uuid) {
        return getService().doConfirmLogin(ip, uuid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //后台管理系统登录
    public static Flowable<BaseResponse<String>> doBackendLogin(String ip, String uuid) {
        return getService().doBackendLogin(ip, uuid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //后台管理系统绑定用户
    public static Flowable<BaseResponse<String>> doBindBackendUser(String ip, String uuid) {
        return getService().doBindBackendUser(ip, uuid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Flowable<BaseResponse<String>> scanQrcodeElecBorrowV2(String content) {
        return getService().scanQrcodeElecBorrowV2(content).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}