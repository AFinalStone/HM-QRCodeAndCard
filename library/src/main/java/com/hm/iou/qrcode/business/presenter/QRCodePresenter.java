package com.hm.iou.qrcode.business.presenter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.qrcode.NavigationHelper;
import com.hm.iou.qrcode.api.QRCodeApi;
import com.hm.iou.qrcode.bean.IOUBriefMoney;
import com.hm.iou.qrcode.business.QRCodeContract;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.tools.ToastUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

public class QRCodePresenter extends MvpActivityPresenter<QRCodeContract.View> implements QRCodeContract.Presenter {

    private static final String FLAG_URL_BEGIN = "http://h5.54jietiao.com";
    private static final String URL_PARAMETER_PROTOCOL = "protocol";
    private static final String URL_TYPE_IOU = "1"; //电子借条
    private static final String URL_TYPE_MY_CARD = "2";//个人名片
    private static final String URL_PARAMETER_JUSTID = "justiceId";

    private UserManager mUserManager;

//    http://h5.54jietiao.com/IOU/Money/Template/5dd0b90393bb4d35bf71591f9c475c37/index.html?protocol=1&justiceId=180513173001000011

    public QRCodePresenter(@NonNull Context context, @NonNull QRCodeContract.View view) {
        super(context, view);
        mUserManager = UserManager.getInstance(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void searchData(final String iouShowId) {
        QRCodeApi.getBriefMoneyIOUByJusticeId(iouShowId)
                .compose(getProvider().<BaseResponse<IOUBriefMoney>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<IOUBriefMoney>handleResponse())
                .subscribeWith(new CommSubscriber<IOUBriefMoney>(mView) {
                    @Override
                    public void handleResult(IOUBriefMoney iouBriefMoney) {
                        //判断出是资金借条，则跳转到资金借条收录的页面
                        NavigationHelper.toMoneyReceiptInclude(mContext, iouBriefMoney, iouShowId);
                    }

                    @Override
                    public void handleException(String s, String s1) {
                        ToastUtil.showMessage(mContext, "借条搜索失败,请稍后重新尝试" + s + s1);
                    }
                });
    }

    @Override
    public void judgeData(String qrCodeContent) {
        if (qrCodeContent.startsWith(FLAG_URL_BEGIN)) {
            Uri uri = Uri.parse(qrCodeContent);
            String type = uri.getQueryParameter(URL_PARAMETER_PROTOCOL);
            String id = uri.getQueryParameter(URL_PARAMETER_JUSTID);
            ToastUtil.showMessage(mContext, "type===" + type + "id===" + id);
            if (URL_TYPE_IOU == type) {
                searchData(id);
            } else if (URL_TYPE_MY_CARD == type) {

            } else {
                ToastUtil.showMessage(mContext, "该功能暂未开放");
            }
        } else {

        }
    }
}