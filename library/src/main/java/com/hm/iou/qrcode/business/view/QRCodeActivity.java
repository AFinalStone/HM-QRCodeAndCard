package com.hm.iou.qrcode.business.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.qrcode.R;
import com.hm.iou.qrcode.R2;
import com.hm.iou.qrcode.business.QRCodeContract;
import com.hm.iou.qrcode.business.presenter.QRCodePresenter;
import com.hm.iou.scancode.CodeUtils;
import com.hm.iou.scancode.view.ScanCodeFragment;
import com.hm.iou.tools.ToastUtil;
import com.hm.iou.uikit.dialog.IOSAlertDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author syl
 * @time 2018/5/11 下午5:07
 */
public class QRCodeActivity extends BaseActivity<QRCodePresenter> implements QRCodeContract.View {

    public static final String EXTRA_KEY_SHOW_TYPE = "show_type";
    public static final String EXTRA_KEY_SHOW_MY_CARD = "show_my_card";
    public static final String EXTRA_KEY_SHOW_SCAN_CODE = "show_scan_code";


    ScanCodeFragment mScanCodeFragment;
    PersonalCardFragment mPersonalCardFragment;

    @BindView(R2.id.ll_sweepCode)
    LinearLayout mLlSweepCode;

    @BindView(R2.id.ll_myCard)
    LinearLayout mLlMyCard;

    /**
     * 二维码解析回调函数
     */
    private CodeUtils.AnalyzeCallback mAnalyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess( String result) {
            mPresenter.judgeData(result);
        }

        @Override
        public void onAnalyzeFailed() {
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.qrcode_activity_qrcode;
    }

    @Override
    protected QRCodePresenter initPresenter() {
        return new QRCodePresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        String showType = getIntent().getStringExtra(EXTRA_KEY_SHOW_TYPE);
        if (EXTRA_KEY_SHOW_MY_CARD.equals(showType)) {
            showMyCardFragment();
        } else if (EXTRA_KEY_SHOW_SCAN_CODE.equals(showType)) {
            showScanCodeFragment();
        }
    }

    @SuppressLint("InvalidR2Usage")
    @OnClick({R2.id.ll_sweepCode, R2.id.ll_myCard})
    public void onClick(View view) {
        if (R.id.ll_sweepCode == view.getId()) {
            showScanCodeFragment();
        } else if (R.id.ll_myCard == view.getId()) {
            showMyCardFragment();
        }
    }

    public void showScanCodeFragment() {
        if (mScanCodeFragment == null) {
            mScanCodeFragment = new ScanCodeFragment();
            mScanCodeFragment.setAnalyzeCallback(mAnalyzeCallback);
        }
        showFragment(mScanCodeFragment);
        mLlMyCard.setBackgroundColor(getResources().getColor(R.color.transparent));
        mLlSweepCode.setBackgroundResource(R.mipmap.qrcode_background_tab_select);
    }

    public void showMyCardFragment() {
        if (mPersonalCardFragment == null) {
            mPersonalCardFragment = new PersonalCardFragment();
        }
        showFragment(mPersonalCardFragment);
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
}
