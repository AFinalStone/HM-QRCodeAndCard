package com.hm.iou.qrcode.business.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.utils.TraceUtil;
import com.hm.iou.qrcode.NavigationHelper;
import com.hm.iou.qrcode.R;
import com.hm.iou.qrcode.R2;
import com.hm.iou.qrcode.business.QRCodeContract;
import com.hm.iou.qrcode.business.presenter.QRCodePresenter;
import com.hm.iou.scancode.CodeUtils;
import com.hm.iou.scancode.view.ScanCodeFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author syl
 * @time 2018/5/11 下午5:07
 */
public class RealQRCodeActivity extends BaseActivity<QRCodePresenter> implements QRCodeContract.View {

    ScanCodeFragment mScanCodeFragment;
    PersonalCardFragment mPersonalCardFragment;

    @BindView(R2.id.ll_sweepCode)
    LinearLayout mLlSweepCode;

    @BindView(R2.id.ll_myCard)
    LinearLayout mLlMyCard;

    private String mShowType;
    /**
     * 二维码解析回调函数
     */
    private CodeUtils.AnalyzeCallback mAnalyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(String result) {
            mPresenter.judgeData(result);
        }

        @Override
        public void onAnalyzeFailed() {
        }
    };

    @Override
    protected void initStatusBarDarkFont(boolean isDarkFont) {
        super.initStatusBarDarkFont(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.qrcode_activity_scan_code;
    }

    @Override
    protected QRCodePresenter initPresenter() {
        return new QRCodePresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mShowType = getIntent().getStringExtra(QRCodeActivity.EXTRA_KEY_SHOW_TYPE);
        if (savedInstanceState != null) {
            mShowType = savedInstanceState.getString(QRCodeActivity.EXTRA_KEY_SHOW_TYPE);
        }
        if (QRCodeActivity.SHOW_TYPE_MY_CARD.equals(mShowType)) {
            showMyCardFragment();
            return;
        }
        if (QRCodeActivity.SHOW_TYPE_SCAN_CODE.equals(mShowType)) {
            showScanCodeFragment();
            return;
        }
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(QRCodeActivity.EXTRA_KEY_SHOW_TYPE, mShowType);
    }

    @OnClick({R2.id.ll_sweepCode, R2.id.ll_myCard})
    public void onClick(View view) {
        if (R.id.ll_sweepCode == view.getId()) {
            TraceUtil.onEvent(mContext, "card_page_scan");
            showScanCodeFragment();
        } else if (R.id.ll_myCard == view.getId()) {
            TraceUtil.onEvent(mContext, "card_page_card");
            showMyCardFragment();
        }
    }

    public void showScanCodeFragment() {
        if (mScanCodeFragment == null) {
            mScanCodeFragment = new ScanCodeFragment();
            mScanCodeFragment.setAnalyzeCallback(mAnalyzeCallback);
        } else {
            mScanCodeFragment.startCamera();
        }
        showFragment(mScanCodeFragment);
        mLlMyCard.setBackgroundColor(getResources().getColor(com.hm.iou.qrcode.R.color.transparent));
        mLlSweepCode.setBackgroundResource(com.hm.iou.qrcode.R.mipmap.qrcode_background_tab_select);
    }

    public void showMyCardFragment() {
        if (mPersonalCardFragment == null) {
            mPersonalCardFragment = new PersonalCardFragment();
        }
        showFragment(mPersonalCardFragment);
        if (mScanCodeFragment != null) {
            mScanCodeFragment.stopCamera();
        }
        mLlSweepCode.setBackgroundColor(getResources().getColor(R.color.transparent));
        mLlMyCard.setBackgroundResource(R.mipmap.qrcode_background_tab_select);
    }

    private void showFragment(Fragment newFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        Fragment fromFragment = fm.findFragmentById(R.id.container);
        if (fromFragment != null) {
            tx.hide(fromFragment);
        }
        if (!newFragment.isAdded()) {    // 先判断是否被add过
            tx.add(R.id.container, newFragment); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            tx.show(newFragment); // 隐藏当前的fragment，显示下一个
        }
        tx.commit();
    }

    @Override
    public void toQRCodeLoginConfirmPage(String url) {
        NavigationHelper.toConfirmLoginActivity(this, url);
    }
}
