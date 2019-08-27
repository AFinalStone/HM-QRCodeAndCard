package com.hm.iou.qrcode.business.presenter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hm.iou.base.BaseBizAppLike;
import com.hm.iou.base.comm.CommApi;
import com.hm.iou.base.comm.PowerSearchResult;
import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RouterUtil;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.base.utils.TraceUtil;
import com.hm.iou.logger.Logger;
import com.hm.iou.network.exception.NoNetworkException;
import com.hm.iou.qrcode.NavigationHelper;
import com.hm.iou.qrcode.R;
import com.hm.iou.qrcode.api.QRCodeApi;
import com.hm.iou.qrcode.bean.IOUBriefMoney;
import com.hm.iou.qrcode.business.QRCodeContract;
import com.hm.iou.router.Router;
import com.hm.iou.sharedata.event.CommBizEvent;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.sharedata.model.IOUKindEnum;
import com.hm.iou.tools.SystemUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class QRCodePresenter extends MvpActivityPresenter<QRCodeContract.View> implements QRCodeContract.Presenter {

    //个人名片二维码暂时使用条管家官网代替
    private String APP_OFFICIAL_WEBSITE_URL;

    private static final String URL_PARAMETER_PROTOCOL = "protocol";
    private static final String URL_PARAMETER_JUSTID = "justiceId";

    private static final String PARAMETER_PROTOCOL_TYPE_ELEC_BORROW = "1";      //电子借条
    private static final String PARAMETER_PROTOCOL_TYPE_ELEC_RECEIVE = "2";     //电子收条
    private static final String PARAMETER_PROTOCOL_TYPE_QRCODE_LOGIN = "3";     //官网二维码扫描登录
    private static final String PARAMETER_PROTOCOL_BACK_BIND_USER = "4";        //后台绑定用户
    private static final String PARAMETER_PROTOCOL_BACK_LOGIN = "5";        //后台登录
    private static final String PARAMETER_PROTOCOL_TYPE_ELEC_BORROW_V2 = "6";   //电子借条V2.0
    private static final String PARAMETER_PROTOCOL_TYPE_ELEC_QIANTIAO = "7";    //电子欠条

    //http://h5.54jietiao.com/IOU/Money/Template/5dd0b90393bb4d35bf71591f9c475c37/index.html?protocol=1&justiceId=180513173001000011

    public QRCodePresenter(@NonNull Context context, @NonNull QRCodeContract.View view) {
        super(context, view);
        EventBus.getDefault().register(this);
        APP_OFFICIAL_WEBSITE_URL = context.getString(R.string.base_official_website_url);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void judgeData(String qrCodeContent) {
        Logger.d("二维码内容:" + qrCodeContent);
        if (TextUtils.isEmpty(qrCodeContent)) {
            return;
        }

        if (qrCodeContent.startsWith(APP_OFFICIAL_WEBSITE_URL)) {
            SystemUtil.openWebBrowser(mContext, qrCodeContent);
            return;
        }

        if (qrCodeContent.startsWith(BaseBizAppLike.getInstance().getH5Server() + "/userQrcode/index.html")) {
            try {
                Uri uri = Uri.parse(qrCodeContent);
                String showId = uri.getQueryParameter("showId");
                if (!TextUtils.isEmpty(showId)) {
                    searchFriend(showId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        parseUrl(qrCodeContent);
    }

    /**
     * 如果是电子借条，则进行搜索收录,如果不是，则提示当前版本暂不支持该功能
     *
     * @param qrCodeContent 扫码识别出来的二维码
     */
    private void parseUrl(String qrCodeContent) {
        mView.showLoadingView();
        QRCodeApi.parseShortUrl(qrCodeContent)
                .compose(getProvider().<BaseResponse<String>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<String>handleResponse())
                .subscribeWith(new CommSubscriber<String>(mView) {
                    @Override
                    public void handleResult(String realCodeContent) {
                        Uri uri = Uri.parse(realCodeContent);
                        String type = uri.getQueryParameter(URL_PARAMETER_PROTOCOL);
                        if (PARAMETER_PROTOCOL_TYPE_ELEC_BORROW.equals(type) || PARAMETER_PROTOCOL_TYPE_ELEC_RECEIVE.equals(type)) {
                            //判断是电子借条或者电子收条，进行搜索操作
                            String id = uri.getQueryParameter(URL_PARAMETER_JUSTID);
                            searchData(id);
                            return;
                        }

                        if (PARAMETER_PROTOCOL_TYPE_QRCODE_LOGIN.equals(type)) {
                            String ip = uri.getQueryParameter("i");
                            String uuid = uri.getQueryParameter("u");
                            mView.dismissLoadingView();
                            mView.toQRCodeLoginConfirmPage(ip, uuid);
                            mView.closeCurrPage();
                            return;
                        }

                        //后台管理系统-绑定用户
                        if (PARAMETER_PROTOCOL_BACK_BIND_USER.equals(type)) {
                            String ip = uri.getQueryParameter("i");
                            String uuid = uri.getQueryParameter("u");
                            mView.dismissLoadingView();
                            mView.toBindBackendUser(ip, uuid);
                            mView.closeCurrPage();
                            return;
                        }

                        //后台管理系统-直接登录
                        if (PARAMETER_PROTOCOL_BACK_LOGIN.equals(type)) {
                            String ip = uri.getQueryParameter("i");
                            String uuid = uri.getQueryParameter("u");
                            mView.dismissLoadingView();
                            mView.toBackendLogin(ip, uuid);
                            mView.closeCurrPage();
                            return;
                        }

                        //电子借条V2.0
                        if (PARAMETER_PROTOCOL_TYPE_ELEC_BORROW_V2.equals(type)) {
                            scanQrcodeElecBorrowV2(realCodeContent);
                            return;
                        }

                        //电子欠条
                        if (PARAMETER_PROTOCOL_TYPE_ELEC_QIANTIAO.equals(type)) {
                            scanQrcodeElecBorrowV2(realCodeContent);
                            return;
                        }

                        mView.dismissLoadingView();
                        mView.toastMessage("当前版本暂不支持该功能");
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                        mView.dismissLoadingView();
                        if (throwable instanceof NoNetworkException) {
                            mView.toastErrorMessage(msg);
                        } else {
                            mView.showNoSupportQrCode();
                        }
                    }

                    @Override
                    public boolean isShowBusinessError() {
                        return false;
                    }

                    @Override
                    public boolean isShowCommError() {
                        return false;
                    }
                });

    }

    /**
     * 搜索电子借条/收条
     *
     * @param justiceId 电子借条的公证id
     */
    private void searchData(final String justiceId) {
        QRCodeApi.searchIOUById(justiceId)
                .compose(getProvider().<BaseResponse<IOUBriefMoney>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<IOUBriefMoney>handleResponse())
                .subscribeWith(new CommSubscriber<IOUBriefMoney>(mView) {
                    @Override
                    public void handleResult(IOUBriefMoney iouBriefMoney) {
                        mView.dismissLoadingView();
                        //判断出是资金借条，则跳转到资金借条收录的页面
                        int iouKind = iouBriefMoney.getIouKind();
                        if (IOUKindEnum.ElecBorrowReceipt.getValue() == iouKind) {
                            Router.getInstance()
                                    .buildWithUrl("hmiou://m.54jietiao.com/iou_search/get_elec_borrow")
                                    .withString("justiceId", justiceId)
                                    .navigation(mContext);
                            mView.closeCurrPage();
                            return;
                        }
                        if (IOUKindEnum.ElecReceiveReceipt.getValue() == iouKind) {
                            Router.getInstance()
                                    .buildWithUrl("hmiou://m.54jietiao.com/iou_search/get_elec_receive")
                                    .withString("justiceId", justiceId)
                                    .navigation(mContext);
                            mView.closeCurrPage();
                            return;
                        }
                        NavigationHelper.toMoneyReceiptInclude(mContext, justiceId);
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                        mView.dismissLoadingView();
                        if (!TextUtils.isEmpty(code)) {
                            TraceUtil.onEvent(mContext, "card_scan_fail_count");
                        }
                    }

                });
    }

    /**
     * 搜索V2借条
     *
     * @param content
     */
    private void scanQrcodeElecBorrowV2(String content) {
        QRCodeApi.scanQrcodeElecBorrowV2(content)
                .compose(getProvider().<BaseResponse<String>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<String>handleResponse())
                .subscribeWith(new CommSubscriber<String>(mView) {
                    @Override
                    public void handleResult(String url) {
                        mView.dismissLoadingView();
                        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/webview/index")
                                .withString("url", url)
                                .navigation(mContext);
                        mView.closeCurrPage();
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                        mView.dismissLoadingView();
                        if (!TextUtils.isEmpty(code)) {
                            TraceUtil.onEvent(mContext, "card_scan_fail_count");
                        }
                    }

                });
    }

    /**
     * 搜索好友
     *
     * @param showId
     */
    private void searchFriend(String showId) {
        mView.showLoadingView();
        CommApi.powerSearch(showId, 3)
                .compose(getProvider().<BaseResponse<PowerSearchResult>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<PowerSearchResult>handleResponse())
                .subscribeWith(new CommSubscriber<PowerSearchResult>(mView) {
                    @Override
                    public void handleResult(PowerSearchResult powerSearchResult) {
                        mView.dismissLoadingView();
                        String url = powerSearchResult.getUrl();
                        if (!TextUtils.isEmpty(url)) {
                            RouterUtil.clickMenuLink(mContext, url);
                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {
                        mView.dismissLoadingView();
                    }
                });
    }

    /**
     * 获取到用户收录成功的事件，关闭当前页面
     *
     * @param commBizEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvenLoginSuccess(CommBizEvent commBizEvent) {
        if ("IOUSearch_includeSuccess".equals(commBizEvent.key)) {
            mView.closeCurrPage();
        }
    }

}