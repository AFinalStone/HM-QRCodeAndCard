package com.hm.iou.qrcode;

import android.content.Context;
import android.content.Intent;

import com.hm.iou.qrcode.bean.IOUBriefMoney;


/**
 * @author syl
 * @time 2018/5/13 下午6:08
 */
public class NavigationHelper {

    public static void toMoneyReceiptInclude(Context context, IOUBriefMoney iouBriefMoney, String iouId) {
        try {
            Intent intent = new Intent(context, Class.forName("com.hm.iou.hmreceipt.ui.activity.receipt.MoneyReceiptIncludeActivity"));
            intent.putExtra("intent_iou_object", iouBriefMoney);
            intent.putExtra("intent_iou_id", iouId);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
