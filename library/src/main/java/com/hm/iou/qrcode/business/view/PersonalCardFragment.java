package com.hm.iou.qrcode.business.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hm.iou.base.BaseFragment;
import com.hm.iou.qrcode.R;
import com.hm.iou.qrcode.R2;
import com.hm.iou.qrcode.business.PersonalCardContract;
import com.hm.iou.qrcode.business.presenter.PersonalCardPresenter;
import com.hm.iou.tools.ImageLoader;
import com.hm.iou.uikit.ShapedImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人名片
 *
 * @author AFinalStone
 */
public class PersonalCardFragment extends BaseFragment<PersonalCardPresenter> implements PersonalCardContract.View {


    @BindView(R2.id.iv_back)
    ImageView mIvBack;
    @BindView(R2.id.iv_header)
    ShapedImageView mIvHeader;
    @BindView(R2.id.rl_background)
    RelativeLayout mRlBackground;
    @BindView(R2.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R2.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R2.id.iv_qr_code)
    ImageView mIvQrCode;

    @Override
    protected int getLayoutId() {
        return R.layout.qrcode_fragment_my_card;
    }

    @Override
    protected PersonalCardPresenter initPresenter() {
        return new PersonalCardPresenter(getContext(), this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mPresenter.getUserInfo();
    }

    @Override
    public void setCardBackground(int imageResId) {
        mRlBackground.setBackgroundResource(imageResId);
    }

    @Override
    public void setHeader(int imageResIdDefault, String imageUrl) {
        ImageLoader.getInstance(getContext()).displayImage(imageUrl, mIvHeader, imageResIdDefault, imageResIdDefault);
    }

    @Override
    public void setUserNickName(String nickName) {
        mTvNickName.setText(nickName);
    }

    @Override
    public void setUserNameAndID(String nameAndID) {
        mTvUserName.setText(nameAndID);
    }

    @Override
    public void setQRCodeImage(Bitmap bitmap) {
        mIvQrCode.setImageBitmap(bitmap);
    }

    @OnClick(R2.id.iv_back)
    public void onClick() {
        getActivity().finish();
    }
}
