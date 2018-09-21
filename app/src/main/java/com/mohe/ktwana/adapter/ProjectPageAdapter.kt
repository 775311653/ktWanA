package com.mohe.ktwana.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.mohe.ktwana.bean.ProjectTreeBean
import com.mohe.ktwana.ui.fragment.ProjectListFragment

/**
 * Created by xiePing on 2018/9/21 0021.
 * Description:
 */
class ProjectPageAdapter(val datas:List<ProjectTreeBean> ,fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private val fragments= mutableListOf<ProjectListFragment>()

    init {
        datas.forEach{
            fragments.add(ProjectListFragment.getInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}