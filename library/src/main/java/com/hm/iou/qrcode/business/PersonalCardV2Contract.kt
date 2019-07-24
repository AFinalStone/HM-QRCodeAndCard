package com.hm.iou.qrcode.business

import android.graphics.Bitmap

import com.hm.iou.base.mvp.BaseContract


/**
 * @author syl
 * @time 2018/5/11 下午5:05
 */
interface PersonalCardV2Contract {

    interface View : BaseContract.BaseView {

        /**
         * 设置头像
         *
         * @param imageResIdDefault 站位图，可能是未知，男性，女性
         * @param imageUrl          真正的头像
         */
        fun setHeader(imageResIdDefault: Int, imageUrl: String)

        /**
         * 设置性别
         */
        fun setSexImageResId(imageResId: Int)

        /**
         * 设置用户昵称
         *
         * @param nickName
         */
        fun setUserNickName(nickName: String)

        /**
         * 设置用户姓名和ID
         *
         * @param nameAndID
         */
        fun setUserNameAndID(nameAndID: String)

        /**
         * 二维码图片
         *
         * @param bitmap
         */
        fun setQRCodeImage(bitmap: Bitmap)
    }

    interface Presenter : BaseContract.BasePresenter {

        /**
         * 获取用户信息
         */
        fun getUserInfo()
    }

}
