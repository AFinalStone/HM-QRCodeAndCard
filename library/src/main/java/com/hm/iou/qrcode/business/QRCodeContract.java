package com.hm.iou.qrcode.business;

import com.hm.iou.base.mvp.BaseContract;


/**
 * @author syl
 * @time 2018/5/11 下午5:05
 */
public interface QRCodeContract {

    interface View extends BaseContract.BaseView {

        /**
         * 官网-跳转到二维码登录确认页面
         *
         * @param ip
         * @param uuid
         */
        void toQRCodeLoginConfirmPage(String ip, String uuid);

        /**
         * 后台管理系统绑定用户
         *
         * @param ip
         * @param uuid
         */
        void toBindBackendUser(String ip, String uuid);

        /**
         * 后台管理系统登录
         *
         * @param ip
         * @param uuid
         */
        void toBackendLogin(String ip, String uuid);
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
