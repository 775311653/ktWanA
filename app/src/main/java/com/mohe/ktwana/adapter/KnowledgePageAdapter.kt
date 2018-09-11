package com.mohe.ktwana.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.mohe.ktwana.bean.Knowledge
import com.mohe.ktwana.ui.fragment.KnowledgeFragment

/**
 * Created by xiePing on 2018/9/9 0009.
 * Description:
 */
class KnowledgePageAdapter(val list:List<Knowledge>,fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private val fragments:MutableList<Fragment> = mutableListOf()

    init {
        list.forEach {
            fragments.add(KnowledgeFragment.getInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return list.size
    }
}