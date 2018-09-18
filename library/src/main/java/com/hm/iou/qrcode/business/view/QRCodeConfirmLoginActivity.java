package com.hm.iou.qrcode.business.view;

import android.os.Bundle;
import android.view.View;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.qrcode.R;
import com.hm.iou.qrcode.business.ConfirmLoginConstract;
import com.hm.iou.qrcode.business.presenter.ConfirmLoginPresenter;

/**
 * Created by hjy on 2018/9/17.
 */

public class QRCodeConfirmLoginActivity extends BaseActivity<ConfirmLoginPresenter> implements ConfirmLoginConstract.View, View.OnClickListener{

    public static final String EXTRA_KEY_URL = "url";

    private String mUrl;

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
        mUrl = getIntent().getStringExtra(EXTRA_KEY_URL);
        if (mUrl == null && bundle != null) {
            mUrl = bundle.getString(EXTRA_KEY_URL);
        }

        findViewById(R.id.tv_login_close).setOnClickListener(this);
        findViewById(R.id.btn_confirm_cancel).setOnClickListener(this);
        findViewById(R.id.btn_confirm_login).setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_URL, mUrl);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_login_close) {
            finish();
        } else if (v.getId() == R.id.btn_confirm_cancel) {
            finish();
        } else if (v.getId() == R.id.btn_confirm_login) {
            mPresenter.doConfirmLogin(mUrl);
        }
    }

}