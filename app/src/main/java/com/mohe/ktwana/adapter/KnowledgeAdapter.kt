package com.mohe.ktwana.adapter

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mohe.ktwana.R
import com.mohe.ktwana.bean.ArticleBean

/**
 * Created by xiePing on 2018/9/10 0010.
 * Description:
 */
class KnowledgeAdapter(val context: Context,datas:List<ArticleBean>)
    :BaseQuickAdapter<ArticleBean,BaseViewHolder>(R.layout.item_home_list,datas) {
    override fun convert(helper: BaseViewHolder?, item: ArticleBean?) {
        item?:return
        helper?:return
        helper.setText(R.id.tv_article_title, item.title)
                .setText(R.id.tv_article_autor, item.author)
                .setText(R.id.tv_article_date, item.niceDate)
                .setImageResource(R.id.iv_like,
                        if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not
                )
                .addOnClickListener(R.id.iv_like)
        if (!TextUtils.isEmpty(item.chapterName)) {
            helper.setText(R.id.tv_article_chapterName, item.chapterName)
            helper.getView<TextView>(R.id.tv_article_chapterName).visibility = View.VISIBLE
        } else {
            helper.getView<TextView>(R.id.tv_article_chapterName).visibility = View.INVISIBLE
        }

        if (!TextUtils.isEmpty(item.envelopePic)) {
            helper.getView<ImageView>(R.id.iv_article_thumbnail)
                    .visibility = View.VISIBLE
            Glide.with(context).load(item.envelopePic).into(helper.getView(R.id.iv_article_thumbnail))
        } else {
            helper.getView<ImageView>(R.id.iv_article_thumbnail)
                    .visibility = View.GONE
        }
    }
}