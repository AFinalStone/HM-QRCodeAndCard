package com.hm.iou.qrcode.business;

import com.hm.iou.base.mvp.BaseContract;


/**
 * @author syl
 * @time 2018/5/11 下午5:05
 */
public interface QRCodeContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 根据借条id进行搜索操作
         *
         * @param iouShowId
         */
        void searchData(String iouShowId);

        /**
         * 判断数据类型
         *
         * @param scanCodeBeginUrl 只有以这个url开头的域名，才进行识别
         * @param qrCodeContent    扫描二维码得到的内容
         */
        void judgeData(String scanCodeBeginUrl, String qrCodeContent);
    }

}
