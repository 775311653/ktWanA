package com.mohe.ktwana.adapter

import com.blankj.utilcode.util.StringUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mohe.ktwana.R
import com.mohe.ktwana.bean.ArticleBean

/**
 * Created by xiePing on 2018/9/3 0003.
 * Description:
 */
class HomeAdapter:BaseQuickAdapter<ArticleBean,BaseViewHolder> {

    constructor(resLayout:Int,articles:List<ArticleBean>):super(resLayout,articles)

    override fun convert(helper: BaseViewHolder?, item: ArticleBean?) {
        item?:return
        helper?:return
        helper.run {
            setText(R.id.tv_article_title,item.title)
            setText(R.id.tv_article_autor,item.author)
            setText(R.id.tv_article_date,item.niceDate)
            if (!StringUtils.isEmpty(item.chapterName)){
                setText(R.id.tv_article_chapterName,item.chapterName)
                setVisible(R.id.tv_article_chapterName,true)
            }else{
                setVisible(R.id.tv_article_chapterName,false)
            }
            setImageResource(R.id.iv_like,if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not)
            addOnClickListener(R.id.iv_like)
            if (item.envelopePic.isNotEmpty()){
                setVisible(R.id.iv_article_thumbnail,true)
                Glide.with(mContext).load(item.envelopePic).into(getView(R.id.iv_article_thumbnail))
            }else{
                setGone(R.id.iv_article_thumbnail,false)
            }
            if (item.fresh){
                setGone(R.id.tv_article_fresh,true)
            }else setGone(R.id.tv_article_fresh,false)
        }

    }


}