package com.hm.iou.qrcode.business.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpFragmentPresenter;
import com.hm.iou.qrcode.R;
import com.hm.iou.qrcode.business.PersonalCardContract;
import com.hm.iou.scancode.CodeUtils;
import com.hm.iou.sharedata.UserManager;
import com.hm.iou.sharedata.model.UserInfo;
import com.hm.iou.tools.DensityUtil;
import com.hm.iou.tools.StringUtil;

public class PersonalCardPresenter extends MvpFragmentPresenter<PersonalCardContract.View> implements PersonalCardContract.Presenter {

    //个人名片二维码暂时使用借条管家官网代替
    public static final String APP_OFFICIAL_WEBSITE_URL = "http://54jietiao.com/m-index.html";

    private UserManager mUserManager;

    public PersonalCardPresenter(@NonNull Context context, @NonNull PersonalCardContract.View view) {
        super(context, view);
        mUserManager = UserManager.getInstance(context);
    }

    @Override
    public void onDestroy() {

    }


    @Override
    public void getUserInfo() {
        UserInfo userDataBean = mUserManager.getUserInfo();
        String nickName = userDataBean.getNickName();
        String userName = userDataBean.getName();
        String userId = userDataBean.getShowId();
        String qrCodeUrl = APP_OFFICIAL_WEBSITE_URL;
        //昵称
        if (StringUtil.isEmpty(nickName)) {
            mView.setUserNickName(nickName);
        }
        //姓名和ID
        String userNameAndId;
        if (StringUtil.isEmpty(userName)) {
            userNameAndId = mContext.getString(R.string.qrcode_not_authentication) + " | " + userId;
        } else {
            userNameAndId = mContext.getString(R.string.qrcode_authentication) + userName + " | " + userId;
        }
        mView.setUserNickName(userNameAndId);
        //头像
        String sexEnum = userDataBean.getSex();
        int resIdHeader = R.mipmap.uikit_icon_header_unknow;
        int resIdCardBackground = R.mipmap.qrcode_background_my_card_unkown;
        if ("MALE".equals(sexEnum)) {
            resIdHeader = R.mipmap.uikit_icon_header_man;
            resIdCardBackground = R.mipmap.qrcode_background_my_card_man;
        } else if ("FEMALE".equals(sexEnum)) {
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