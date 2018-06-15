package com.hm.iou.qrcode;

import android.content.Context;
import android.content.Intent;

import com.hm.iou.qrcode.bean.IOUBriefMoney;
import com.hm.iou.router.Router;


/**
 * @author syl
 * @time 2018/5/13 下午6:08
 */
public class NavigationHelper {

    public static void toMoneyReceiptInclude(Context context, String iouId) {
//        try {
//            Intent intent = new Intent(context, Class.forName("com.hm.iou.hmreceipt.ui.activity.receipt.MoneyReceiptIncludeActivity"));
//            intent.putExtra("iou_id", iouId);
//            context.startActivity(intent);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        Router.getInstance()
                .buildWithUrl("hmiou://m.54jietiao.com/iou_include/include_elec_borrow")
                .withString("iou_id", iouId)
                .navigation(context);
    }

}
