package com.mohe.ktwana.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import com.mohe.ktwana.R
import com.mohe.ktwana.bean.NavigationBean
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView

/**
 * Created by xiePing on 2018/9/17 0017.
 * Description:
 */
class NavigationTabAdapter(val context: Context,val list: MutableList<NavigationBean>):TabAdapter {

    override fun getIcon(position: Int): ITabView.TabIcon? =null

    override fun getBadge(position: Int): ITabView.TabBadge? =null

    override fun getBackground(position: Int): Int =-1

    override fun getTitle(position: Int): ITabView.TabTitle {
        return ITabView.TabTitle.Builder()
                .setContent(list[position].name)
                .setTextColor(ContextCompat.getColor(context, R.color.colorAccent),
                        ContextCompat.getColor(context,R.color.Grey500))
                .build()
    }

    override fun getCount(): Int =list.size
}