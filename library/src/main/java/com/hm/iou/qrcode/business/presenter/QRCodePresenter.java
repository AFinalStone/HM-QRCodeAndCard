package com.hm.iou.qrcode.business.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.logger.Logger;
import com.hm.iou.qrcode.NavigationHelper;
import com.hm.iou.qrcode.api.QRCodeApi;
import com.hm.iou.qrcode.bean.IOUBriefMoney;
import com.hm.iou.qrcode.business.QRCodeContract;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.tools.StringUtil;
import com.hm.iou.tools.SystemUtil;
import com.hm.iou.tools.ToastUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

public class QRCodePresenter extends MvpActivityPresenter<QRCodeContract.View> implements QRCodeContract.Presenter {

    private static final String URL_PARAMETER_PROTOCOL = "protocol";
    private static final String URL_PARAMETER_JUSTID = "justiceId";
    private static final String PARAMETER_PROTOCOL_TYPE_IOU = "1"; //电子借条

    //    http://h5.54jietiao.com/IOU/Money/Template/5dd0b90393bb4d35bf71591f9c475c37/index.html?protocol=1&justiceId=180513173001000011
    //允许识别的url
    private static final String URL_BEGIN_DEFAULT_HTTP = "http://h5.54jietiao.com";
    private static final String URL_BEGIN_DEFAULT_HTTPS = "https://h5.54jietiao.com";

    public QRCodePresenter(@NonNull Context context, @NonNull QRCodeContract.View view) {
        super(context, view);
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
                        NavigationHelper.toMoneyReceiptInclude(mContext, iouShowId);
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {

                    }


                });
    }

    @Override
    public void judgeData(String scanCodeBeginUrl, String qrCodeContent) {
        if (!StringUtil.isEmpty(scanCodeBeginUrl) && qrCodeContent.startsWith(scanCodeBeginUrl)) {
            //如果scanCodeBeginUrl不为空,且qrCode以scanCodeBeginUrl开头，则进行解析
            parseUrl(qrCodeContent);
        } else if (qrCodeContent.startsWith(URL_BEGIN_DEFAULT_HTTP)
                || qrCodeContent.startsWith(URL_BEGIN_DEFAULT_HTTPS)) {
            //如果scanCodeBeginUrl为空，则使用默认的url作为校验开头，进行解析
            parseUrl(qrCodeContent);
        } else if (PersonalCardPresenter.APP_OFFICIAL_WEBSITE_URL.equals(qrCodeContent)) {
            SystemUtil.openWebBrowser(mContext, qrCodeContent);
        } else {
            mView.toastMessage("当前版本暂不支持识别其他来源二维码");
        }
    }

    /**
     * 如果是电子借条，则进行搜索收录,如果不是，则提示当前版本暂不支持该功能
     *
     * @param qrCodeContent 扫码识别出来的二维码
     */
    private void parseUrl(String qrCodeContent) {
        Uri uri = Uri.parse(qrCodeContent);
        String type = uri.getQueryParameter(URL_PARAMETER_PROTOCOL);
        if (PARAMETER_PROTOCOL_TYPE_IOU.equals(type)) {
            String id = uri.getQueryParameter(URL_PARAMETER_JUSTID);
            searchData(id);
        } else {
            mView.toastMessage("当前版本暂不支持该功能");
        }
    }

}