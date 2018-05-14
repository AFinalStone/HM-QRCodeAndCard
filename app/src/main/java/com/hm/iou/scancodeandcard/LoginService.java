package com.hm.iou.scancodeandcard;

import com.hm.iou.qrcode.bean.IOUBriefMoney;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.UserInfo;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hjy on 18/4/27.<br>
 */

public interface LoginService {

    @POST("/acct/mobileLogin")
    @FormUrlEncoded
    Flowable<BaseResponse<UserInfo>> mobileLogin(@Field("loginName") String userPhone, @Field("queryPswd") String queryPswd);

}
