package com.hm.iou.qrcode;

import android.content.Context;
import android.content.Intent;

import com.hm.iou.qrcode.business.view.QRCodeConfirmLoginActivity;
import com.hm.iou.router.Router;


/**
 * @author syl
 * @time 2018/5/13 下午6:08
 */
public class NavigationHelper {

    public static void toMoneyReceiptInclude(Context context, String iouId) {
        Router.getInstance()
                .buildWithUrl("hmiou://m.54jietiao.com/iou_include/include_elec_borrow")
                .withString("iou_id", iouId)
                .navigation(context);
    }

    /**
     * 扫码之后跳转到确认登录页
     *
     * @param context
     * @param url
     */
    public static void toConfirmLoginActivity(Context context, String url) {
        Intent intent = new Intent(context, QRCodeConfirmLoginActivity.class);
        intent.putExtra(QRCodeConfirmLoginActivity.EXTRA_KEY_URL, url);
        context.startActivity(intent);
    }

}
