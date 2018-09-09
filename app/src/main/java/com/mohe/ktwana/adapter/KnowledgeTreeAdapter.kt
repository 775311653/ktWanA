package com.mohe.ktwana.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mohe.ktwana.R
import com.mohe.ktwana.bean.KnowledgeTreeBody

/**
 * Created by xiePing on 2018/9/9 0009.
 * Description:
 */
class KnowledgeTreeAdapter(layoutResId: Int, data: MutableList<KnowledgeTreeBody>?)
    : BaseQuickAdapter<KnowledgeTreeBody, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: KnowledgeTreeBody?) {
        helper?.setText(R.id.tv_title_first,item?.name)
        item?.children?.run {
            helper?.setText(R.id.tv_title_second,
                    joinToString("     ", transform = {
                        it.name
                    }))
        }
    }

}