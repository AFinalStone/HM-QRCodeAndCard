package com.hm.iou.qrcode.business;

import android.graphics.Bitmap;

import com.hm.iou.base.mvp.BaseContract;


/**
 * @author syl
 * @time 2018/5/11 下午5:05
 */
public interface PersonalCardContract {

    interface View extends BaseContract.BaseView {

        /**
         * 设置卡片背景
         *
         * @param imageResId 图片资源id
         */
        void setCardBackground(int imageResId);

        /**
         * 设置头像
         *
         * @param imageResIdDefault 站位图，可能是未知，男性，女性
         * @param imageUrl          真正的头像
         */
        void setHeader(int imageResIdDefault, String imageUrl);

        /**
         * 设置用户昵称
         *
         * @param nickName
         */
        void setUserNickName(String nickName);

        /**
         * 设置用户姓名和ID
         *
         * @param nameAndID
         */
        void setUserNameAndID(String nameAndID);

        /**
         * 二维码图片
         *
         * @param bitmap
         */
        void setQRCodeImage(Bitmap bitmap);
    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 获取用户信息
         */
        void getUserInfo();
    }

}
