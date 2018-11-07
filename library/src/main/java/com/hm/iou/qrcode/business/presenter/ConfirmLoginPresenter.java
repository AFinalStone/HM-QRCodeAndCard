package com.hm.iou.qrcode.business.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.qrcode.api.QRCodeApi;
import com.hm.iou.qrcode.business.ConfirmLoginContract;
import com.hm.iou.qrcode.business.view.QRCodeConfirmLoginActivity;
import com.hm.iou.sharedata.model.BaseResponse;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ConfirmLoginPresenter extends MvpActivityPresenter<ConfirmLoginContract.View> implements ConfirmLoginContract.Presenter {

    private static final int COUNT_DOWN_SEC = 45;

    public ConfirmLoginPresenter(@NonNull Context context, @NonNull ConfirmLoginContract.View view) {
        super(context, view);
    }

    @Override
    public void doConfirmLogin(String ip, String uuid, int loginType) {
        mView.showLoadingView();
        if (loginType == QRCodeConfirmLoginActivity.TYPE_WEB_LOGIN) {
            QRCodeApi.doConfirmLogin(ip, uuid)
                    .compose(getProvider().<BaseResponse<String>>bindUntilEvent(ActivityEvent.DESTROY))
                    .map(RxUtil.<String>handleResponse())
                    .subscribeWith(new CommSubscriber<String>(mView) {
                        @Override
                        public void handleResult(String s) {
                            mView.dismissLoadingView();
                            mView.toastMessage("登录成功");
                            mView.closeCurrPage();
                        }

                        @Override
                        public void handleException(Throwable throwable, String code, String msg) {
                            mView.dismissLoadingView();
                        }
                    });
        } else if (loginType == QRCodeConfirmLoginActivity.TYPE_BACKEND_BIND_USER) {
            QRCodeApi.doBindBackendUser(ip, uuid)
                    .compose(getProvider().<BaseResponse<String>>bindUntilEvent(ActivityEvent.DESTROY))
                    .map(RxUtil.<String>handleResponse())
                    .subscribeWith(new CommSubscriber<String>(mView) {
                        @Override
                        public void handleResult(String s) {
                            mView.dismissLoadingView();
                            mView.toastMessage("绑定成功");
                            mView.closeCurrPage();
                        }

                        @Override
                        public void handleException(Throwable throwable, String code, String msg) {
                            mView.dismissLoadingView();
                        }
                    });
        } else if (loginType == QRCodeConfirmLoginActivity.TYPE_BACKEND_LOGIN) {
            QRCodeApi.doBackendLogin(ip, uuid)
                    .compose(getProvider().<BaseResponse<String>>bindUntilEvent(ActivityEvent.DESTROY))
                    .map(RxUtil.<String>handleResponse())
                    .subscribeWith(new CommSubscriber<String>(mView) {
                        @Override
                        public void handleResult(String s) {
                            mView.dismissLoadingView();
                            mView.toastMessage("登录成功");
                            mView.closeCurrPage();
                        }

                        @Override
                        public void handleException(Throwable throwable, String code, String msg) {
                            mView.dismissLoadingView();
                        }
                    });
        }
    }

    @Override
    public void startCountDown() {
        mView.updateCountDownText(String.format("(%dS)", COUNT_DOWN_SEC));
        Flowable.interval(1, 1, TimeUnit.SECONDS)
                .take(COUNT_DOWN_SEC)
                .compose(getProvider().<Long>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommSubscriber<Long>(mView) {
                    @Override
                    public void handleResult(Long aLong) {
                        System.out.println("count down: " + aLong);
                        mView.updateCountDownText((COUNT_DOWN_SEC - aLong - 1) + "S");
                        mView.updateCountDownText(String.format("(%dS)", COUNT_DOWN_SEC - aLong - 1));
                        if (aLong == COUNT_DOWN_SEC - 1) {
                            mView.closeCurrPage();
                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {

                    }
                });
    }
}