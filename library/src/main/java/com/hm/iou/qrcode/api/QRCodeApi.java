package com.hm.iou.qrcode.api;

import com.hm.iou.network.HttpReqManager;
import com.hm.iou.qrcode.bean.IOUBriefMoney;
import com.hm.iou.sharedata.model.BaseResponse;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author syl
 * @time 2018/5/13 下午5:52
 */
public class QRCodeApi {

    private static QRCodeService getService() {
        return HttpReqManager.getInstance().getService(QRCodeService.class);
    }


    /**
     * 通过公正id查询借条是否存在
     *
     * @param justiceId 借条id
     * @return
     */
    public static Flowable<BaseResponse<IOUBriefMoney>> getBriefMoneyIOUByJusticeId(String justiceId) {
        return getService().getBriefMoneyIOUByJusticeId(justiceId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


}