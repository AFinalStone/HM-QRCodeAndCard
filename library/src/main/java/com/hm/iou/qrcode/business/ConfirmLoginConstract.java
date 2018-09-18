package com.hm.iou.qrcode.business;

import com.hm.iou.base.mvp.BaseContract;

/**
 * 确认登录
 */
public interface ConfirmLoginConstract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.BasePresenter {

        void doConfirmLogin(String ip, String uuid);

    }

}
