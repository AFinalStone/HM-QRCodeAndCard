package com.hm.iou.qrcode.business.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hm.iou.base.BaseBizAppLike;
import com.hm.iou.base.mvp.MvpFragmentPresenter;
import com.hm.iou.logger.Logger;
import com.hm.iou.qrcode.R;
import com.hm.iou.qrcode.business.PersonalCardContract;
import com.hm.iou.scancode.CodeUtils;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.CustomerTypeEnum;
import com.hm.iou.sharedata.model.SexEnum;
import com.hm.iou.sharedata.model.UserInfo;
import com.hm.iou.tools.DensityUtil;
import com.hm.iou.tools.StringUtil;

public class PersonalCardPresenter extends MvpFragmentPresenter<PersonalCardContract.View> implements PersonalCardContract.Presenter {

    private UserManager mUserManager;

    public PersonalCardPresenter(@NonNull Context context, @NonNull PersonalCardContract.View view) {
        super(context, view);
        mUserManager = UserManager.getInstance(context);
    }

    @Override
    public void onDestroy() {

    }

    public static boolean isCClass(int userType) {
        if (userType == 0)
            return true;
        if (userType == CustomerTypeEnum.CSub.getValue() || userType == CustomerTypeEnum.CPlus.getValue())
            return true;
        return false;
    }

    @Override
    public void getUserInfo() {
        UserInfo userDataBean = mUserManager.getUserInfo();
        int customerTypeEnum = userDataBean.getType();
        String nickName = StringUtil.getUnnullString(userDataBean.getNickName());
        String userName = StringUtil.getUnnullString(userDataBean.getName());
        String userId = StringUtil.getUnnullString(userDataBean.getShowId());
        //昵称
        mView.setUserNickName(TextUtils.isEmpty(nickName) ? "无" : nickName);

        //姓名和ID
        String userNameAndId;
        if (isCClass(customerTypeEnum)) {
            userNameAndId = mContext.getString(R.string.qrcode_not_authentication) + "  ID:" + userId;
        } else {
            userNameAndId = mContext.getString(R.string.qrcode_authentication) + userName + "  ID:" + userId;
        }
        mView.setUserNameAndID(userNameAndId);
        //头像
        int sexEnum = userDataBean.getSex();
        int resIdHeader = R.mipmap.uikit_icon_header_unknow;
        int resIdCardBackground = R.mipmap.qrcode_background_my_card_unkown;
        if (sexEnum == SexEnum.MALE.getValue()) {
            resIdHeader = R.mipmap.uikit_icon_header_man;
            resIdCardBackground = R.mipmap.qrcode_background_my_card_man;
        } else if (sexEnum == SexEnum.FEMALE.getValue()) {
            resIdHeader = R.mipmap.uikit_icon_header_wuman;
            resIdCardBackground = R.mipmap.qrcode_background_my_card_wuman;
        }
        String urlHeader = userDataBean.getAvatarUrl();
        mView.setHeader(resIdHeader, urlHeader);
        mView.setCardBackground(resIdCardBackground);

        //二维码
        int length = DensityUtil.dip2px(mContext, 114);

        String qrCodeUrl = String.format("%s/userQrcode/index.html?showId=%s", BaseBizAppLike.getInstance().getH5Server(),
                userDataBean.getShowId());
        Logger.d("QrCodeUrl: " + qrCodeUrl);
        Bitmap bitmap = CodeUtils.createImage(qrCodeUrl, length, length, null);
        mView.setQRCodeImage(bitmap);
    }
}