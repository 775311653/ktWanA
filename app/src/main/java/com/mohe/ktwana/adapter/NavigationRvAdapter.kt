package com.mohe.ktwana.adapter

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.ActivityUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mohe.ktwana.R
import com.mohe.ktwana.bean.ArticleBean
import com.mohe.ktwana.bean.NavigationBean
import com.mohe.ktwana.constant.Constant
import com.mohe.ktwana.ui.activity.ContentActivity
import com.mohe.ktwana.utils.CommonUtils
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * Created by xiePing on 2018/9/17 0017.
 * Description:
 */
class NavigationRvAdapter(val context: Context,data:MutableList<NavigationBean>)
        : BaseQuickAdapter<NavigationBean, BaseViewHolder>(R.layout.item_navigation_list,data) {
    override fun convert(helper: BaseViewHolder?, item: NavigationBean?) {
        item?:return
        helper?.setText(R.id.tv_item_navigation,item.name)
        val flowLayout: TagFlowLayout? =helper?.getView(R.id.tagFlow_item_navigation)
        val articles: List<ArticleBean> = item.articles
        flowLayout?.adapter=object : TagAdapter<ArticleBean>(item.articles){
            override fun getView(parent: FlowLayout?, position: Int, t: ArticleBean?): View? {
                val tvTag:TextView= LayoutInflater.from(context).inflate(R.layout.flow_layout_tv,null) as TextView
                t ?: return null
                tvTag.text = t.title
                tvTag.setTextColor(CommonUtils.randomColor())
                flowLayout?.setOnTagClickListener { view,position,parent ->
                    val options:ActivityOptions= ActivityOptions.makeScaleUpAnimation(tvTag,
                            tvTag.width/2,
                            tvTag.height/2,
                            0,
                            0)
                    val intent=Intent(context,ContentActivity::class.java)
                    var data: ArticleBean =articles [position]
                    intent.run {
                        putExtra(Constant.CONTENT_URL_KEY, data.link)
                        putExtra(Constant.CONTENT_TITLE_KEY, data.title)
                        putExtra(Constant.CONTENT_ID_KEY, data.id)
                        ActivityUtils.startActivity(this,options.toBundle())
                    }
                    true
                }
                return tvTag
            }

        }
    }
}