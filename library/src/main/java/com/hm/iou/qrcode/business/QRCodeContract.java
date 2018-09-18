package com.hm.iou.qrcode.business;

import com.hm.iou.base.mvp.BaseContract;


/**
 * @author syl
 * @time 2018/5/11 下午5:05
 */
public interface QRCodeContract {

    interface View extends BaseContract.BaseView {

        /**
         * 跳转到二维码登录确认页面
         *
         * @param url 扫描之后的url信息，APP再调用该url
         */
        void toQRCodeLoginConfirmPage(String url);
    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 判断数据类型
         *
         * @param qrCodeContent 扫描二维码得到的内容
         */
        void judgeData(String qrCodeContent);
    }

}
