package com.hm.iou.qrcode.api;

import com.hm.iou.qrcode.bean.IOUBriefMoney;
import com.hm.iou.sharedata.model.BaseResponse;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
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
}
