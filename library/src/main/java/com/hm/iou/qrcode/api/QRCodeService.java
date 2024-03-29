package com.hm.iou.qrcode.api;

import com.hm.iou.qrcode.bean.IOUBriefMoney;
import com.hm.iou.sharedata.model.BaseResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by hjy on 18/4/27.<br>
 */

public interface QRCodeService {

    @GET("/api/iou/front/v1/getIOUByJusticeId")
    Flowable<BaseResponse<IOUBriefMoney>> searchIOUById(@Query("justiceId") String justiceId);

    @GET
    Flowable<BaseResponse<String>> parseShortUrl(@Url String url);

    @GET("/auth/web/user/v1/loginByQrcode/scan")
    Flowable<BaseResponse<String>> doConfirmLogin(@Query("i") String ip, @Query("u") String uuid);

    @GET("/api/backenduser/user/v1/backenduserLogin")
    Flowable<BaseResponse<String>> doBackendLogin(@Query("i") String ip, @Query("u") String uuid);

    @GET("/api/backenduser/user/v1/bindBackendUser")
    Flowable<BaseResponse<String>> doBindBackendUser(@Query("i") String ip, @Query("u") String uuid);

    @GET("/api/base/scan/v1/scanQrcode")
    Flowable<BaseResponse<String>> scanQrcodeElecBorrowV2(@Query("content") String content);

}
