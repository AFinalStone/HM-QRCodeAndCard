package com.hm.iou.qrcode.business.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.qrcode.R;
import com.hm.iou.qrcode.business.ConfirmLoginConstract;
import com.hm.iou.qrcode.business.presenter.ConfirmLoginPresenter;
import com.hm.iou.uikit.HMTopBarView;

/**
 * Created by hjy on 2018/9/17.
 */

public class QRCodeConfirmLoginActivity extends BaseActivity<ConfirmLoginPresenter> implements ConfirmLoginConstract.View, View.OnClickListener{

    public static final String EXTRA_KEY_IP = "ip";
    public static final String EXTRA_KEY_UUID = "uuid";

    private String mIp;
    private String mUuid;

    private TextView mTvCountDown;

    @Override
    protected int getLayoutId() {
        return R.layout.qrcode_activity_confirm_login;
    }

    @Override
    protected ConfirmLoginPresenter initPresenter() {
        return new ConfirmLoginPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        mIp = getIntent().getStringExtra(EXTRA_KEY_IP);
        mUuid = getIntent().getStringExtra(EXTRA_KEY_UUID);
        if (mUuid == null && bundle != null) {
            mIp = bundle.getString(EXTRA_KEY_IP);
            mUuid = bundle.getString(EXTRA_KEY_UUID);
        }

        View topBar = findViewById(R.id.ll_topbar);
        int statusBarHeight = HMTopBarView.getStatusBarHeight(this);
        if (statusBarHeight > 0) {
            topBar.setPadding(0, statusBarHeight, 0, 0);
        }

        mTvCountDown = findViewById(R.id.tv_login_countdown);
        findViewById(R.id.tv_login_close).setOnClickListener(this);
        findViewById(R.id.btn_confirm_cancel).setOnClickListener(this);
        findViewById(R.id.btn_confirm_login).setOnClickListener(this);
        mPresenter.startCountDown();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_IP, mIp);
        outState.putString(EXTRA_KEY_UUID, mUuid);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_login_close) {
            finish();
        } else if (v.getId() == R.id.btn_confirm_cancel) {
            finish();
        } else if (v.getId() == R.id.btn_confirm_login) {
            mPresenter.doConfirmLogin(mIp, mUuid);
        }
    }

    @Override
    public void updateCountDownText(String text) {
        mTvCountDown.setText(text);
    }
}