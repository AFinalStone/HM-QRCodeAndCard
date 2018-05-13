package com.hm.iou.qrcode.api;

import com.hm.iou.qrcode.bean.IOUBriefMoney;
import com.hm.iou.sharedata.model.BaseResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hjy on 18/4/27.<br>
 */

public interface QRCodeService {

    @GET("/fnt/getBriefMoneyIOUByJusticeId")
    Flowable<BaseResponse<IOUBriefMoney>> getBriefMoneyIOUByJusticeId(@Query("justiceId") String justiceId);

}
