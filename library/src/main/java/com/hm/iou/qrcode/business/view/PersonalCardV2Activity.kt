package com.hm.iou.qrcode.business.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import com.hm.iou.base.BaseActivity
import com.hm.iou.base.file.FileUtil
import com.hm.iou.logger.Logger
import com.hm.iou.qrcode.R
import com.hm.iou.qrcode.business.PersonalCardV2Contract
import com.hm.iou.qrcode.business.presenter.PersonalCardV2Presenter
import com.hm.iou.socialshare.business.UMShareUtil
import com.hm.iou.tools.ImageLoader
import com.hm.iou.tools.kt.clickWithDuration
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.qrcode_activity_personal_card_v2.*
import java.lang.ref.WeakReference

/**
 * 个人名片
 *
 * @author AFinalStone
 */
class PersonalCardV2Activity : BaseActivity<PersonalCardV2Presenter>(), PersonalCardV2Contract.View {

    private var mUMShareUtil: UMShareUtil? = null
    private var mBitmap: WeakReference<Bitmap>? = null

    override fun initEventAndData(p0: Bundle?) {
        ll_share_qq.clickWithDuration {
            if (mUMShareUtil == null) {
                mUMShareUtil = UMShareUtil(mContext)
            }
            mUMShareUtil!!.setShareListener(object : UMShareListener {
                override fun onResult(p0: SHARE_MEDIA?) {
                }

                override fun onCancel(p0: SHARE_MEDIA?) {
                }

                override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                }

                override fun onStart(p0: SHARE_MEDIA?) {
                }
            })
            if (mBitmap == null || mBitmap?.get() == null) {
                mBitmap = getBitmap()
            }
            mUMShareUtil?.sharePicture(SHARE_MEDIA.QQ, mBitmap?.get())
        }
        ll_share_wx.clickWithDuration {
            if (mUMShareUtil == null) {
                mUMShareUtil = UMShareUtil(mContext)
            }
            mUMShareUtil!!.setShareListener(object : UMShareListener {
                override fun onResult(p0: SHARE_MEDIA?) {
                }

                override fun onCancel(p0: SHARE_MEDIA?) {
                }

                override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                }

                override fun onStart(p0: SHARE_MEDIA?) {
                }
            })
            if (mBitmap == null || mBitmap?.get() == null) {
                mBitmap = getBitmap()
            }
            mUMShareUtil?.sharePicture(SHARE_MEDIA.WEIXIN, mBitmap?.get())
        }
        ll_share_save.clickWithDuration {
            if (mBitmap == null || mBitmap?.get() == null) {
                mBitmap = getBitmap()
            }
            FileUtil.savePicture(mContext, mBitmap?.get())
        }
        mPresenter.getUserInfo()
    }

    override fun initPresenter(): PersonalCardV2Presenter {
        return PersonalCardV2Presenter(mContext, this)
    }

    override fun getLayoutId(): Int {
        return R.layout.qrcode_activity_personal_card_v2
    }

    override fun onDestroy() {
        super.onDestroy()
        mUMShareUtil?.onDestroy()
    }

    override fun setHeader(imageResIdDefault: Int, imageUrl: String) {
        ImageLoader.getInstance(mContext).displayImage(imageUrl, iv_header, imageResIdDefault, imageResIdDefault)
    }

    override fun setSexImageResId(imageResId: Int) {
        iv_sex.setImageResource(imageResId)
    }

    override fun setUserNickName(nickName: String) {
        tv_nick_name.text = nickName
    }

    override fun setUserNameAndID(nameAndID: String) {
        tv_name_and_id.text = nameAndID
    }

    override fun setQRCodeImage(bitmap: Bitmap) {
        iv_qr_code.setImageBitmap(bitmap)
    }


    private fun getBitmap(): WeakReference<Bitmap> {
        val w = rl_personal_card.width
        val h = rl_personal_card.height
        Logger.d("width = $w, height = $h")
        val bmp: WeakReference<Bitmap> = WeakReference(Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888))
        val canvas = Canvas(bmp.get())
        canvas.drawColor(Color.WHITE)
        rl_personal_card.draw(canvas)
        return bmp
    }

}
