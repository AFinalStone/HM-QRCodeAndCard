package com.hm.iou.qrcode.business.presenter

import android.content.Context
import com.hm.iou.base.BaseBizAppLike
import com.hm.iou.base.mvp.MvpActivityPresenter
import com.hm.iou.logger.Logger
import com.hm.iou.qrcode.R
import com.hm.iou.qrcode.business.PersonalCardV2Contract
import com.hm.iou.scancode.CodeUtils
import com.hm.iou.sharedata.UserManager
import com.hm.iou.sharedata.model.CustomerTypeEnum
import com.hm.iou.sharedata.model.SexEnum
import com.hm.iou.tools.kt.dp2px

class PersonalCardV2Presenter(context: Context, view: PersonalCardV2Contract.View) : MvpActivityPresenter<PersonalCardV2Contract.View>(context, view), PersonalCardV2Contract.Presenter {

    private val mUserManager: UserManager = UserManager.getInstance(context)

    override fun onDestroy() {

    }

    override fun getUserInfo() {
        val userDataBean = mUserManager.userInfo
        val customerTypeEnum = userDataBean.type
        val nickName = userDataBean?.nickName ?: "无"
        val userName = userDataBean?.name ?: ""
        val userId = userDataBean?.showId ?: ""
        //昵称
        mView.setUserNickName(nickName)

        //姓名和ID
        val userNameAndId: String = if (isCClass(customerTypeEnum)) {
            mContext.getString(R.string.qrcode_not_authentication) + "  ID:" + userId
        } else {
            mContext.getString(R.string.qrcode_authentication) + userName + "  ID:" + userId
        }
        mView.setUserNameAndID(userNameAndId)
        //头像
        val sexEnum = userDataBean.sex
        var resIdHeader = R.mipmap.uikit_icon_header_unknow
        var resIdSex: Int? = null
        if (sexEnum == SexEnum.MALE.value) {
            resIdHeader = R.mipmap.uikit_icon_header_man
            resIdSex = R.mipmap.uikit_ic_gender_man
        } else if (sexEnum == SexEnum.FEMALE.value) {
            resIdHeader = R.mipmap.uikit_icon_header_wuman
            resIdSex = R.mipmap.uikit_ic_gender_woman
        }
        val urlHeader = userDataBean.avatarUrl
        mView.setHeader(resIdHeader, urlHeader)
        if (resIdSex != null) {
            mView.setSexImageResId(resIdSex)
        }
        //二维码
        val length = mContext.dp2px(240)

        val qrCodeUrl = String.format("%s/userQrcode/index.html?showId=%s", BaseBizAppLike.getInstance().h5Server, userDataBean.showId)
        Logger.d("QrCodeUrl: $qrCodeUrl")
        val bitmap = CodeUtils.createImage(qrCodeUrl, length, length, null)
        mView.setQRCodeImage(bitmap)
    }

    companion object {

        fun isCClass(userType: Int): Boolean {
            if (userType == 0)
                return true
            return userType == CustomerTypeEnum.CSub.value || userType == CustomerTypeEnum.CPlus.value
        }
    }
}