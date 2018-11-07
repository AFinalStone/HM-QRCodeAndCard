package com.hm.iou.qrcode.business;

import com.hm.iou.base.mvp.BaseContract;

/**
 * 确认登录
 */
public interface ConfirmLoginContract {

    interface View extends BaseContract.BaseView {

        void updateCountDownText(String text);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void doConfirmLogin(String ip, String uuid, int loginType);

        void startCountDown();
    }

}
