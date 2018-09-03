package com.mohe.ktwana.widget

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mohe.ktwana.R
import com.youth.banner.loader.ImageLoader

/**
 * Created by xiePing on 2018/9/2 0002.
 * Description:
 */
class BannerGlideLoad:ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        if (context!=null&&imageView != null) {
            Glide.with(context)
                    .load(path)
                    .apply(RequestOptions().placeholder(R.drawable.icon_nopic))
                    .into(imageView)
        }
    }
}