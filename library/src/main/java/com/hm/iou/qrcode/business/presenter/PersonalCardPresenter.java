package com.hm.iou.qrcode.business.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hm.iou.base.mvp.MvpFragmentPresenter;
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

    //个人名片二维码暂时使用条管家官网代替
    private String APP_OFFICIAL_WEBSITE_URL;

    private UserManager mUserManager;

    public PersonalCardPresenter(@NonNull Context context, @NonNull PersonalCardContract.View view) {
        super(context, view);
        APP_OFFICIAL_WEBSITE_URL = context.getString(R.string.base_official_website_url);
        mUserManager = UserManager.getInstance(context);
    }

    @Override
    public void onDestroy() {

    }

    public static boolean isCClass(int userType) {
        if (userType == CustomerTypeEnum.CSub.getValue() || userType == CustomerTypeEnum.CPlus.getValue())
            return true;
        return false;
    }

    @Override
    public void getUserInfo() {
        UserInfo userDataBean = mUserManager.getUserInfo();
        int customerTypeEnum = userDataBean.getType();
        String nickName = userDataBean.getNickName();
        String userName = userDataBean.getName();
        String userId = userDataBean.getShowId();
        String qrCodeUrl = APP_OFFICIAL_WEBSITE_URL;
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
        Bitmap bitmap = CodeUtils.createImage(qrCodeUrl, length, length, null);
        mView.setQRCodeImage(bitmap);
    }
}