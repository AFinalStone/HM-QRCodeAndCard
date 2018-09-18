package com.hm.iou.qrcode.business.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.qrcode.api.QRCodeApi;
import com.hm.iou.qrcode.business.ConfirmLoginConstract;
import com.hm.iou.sharedata.model.BaseResponse;
import com.trello.rxlifecycle2.android.ActivityEvent;

public class ConfirmLoginPresenter extends MvpActivityPresenter<ConfirmLoginConstract.View> implements ConfirmLoginConstract.Presenter {


    public ConfirmLoginPresenter(@NonNull Context context, @NonNull ConfirmLoginConstract.View view) {
        super(context, view);
    }

    @Override
    public void doConfirmLogin(String ip, String uuid) {
        mView.showLoadingView();
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
    }
}