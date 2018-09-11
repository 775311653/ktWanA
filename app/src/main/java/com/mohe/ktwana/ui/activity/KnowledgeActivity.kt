package com.mohe.ktwana.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import com.mohe.ktwana.R
import com.mohe.ktwana.adapter.KnowledgePageAdapter
import com.mohe.ktwana.base.BaseSwipeBackActivity
import com.mohe.ktwana.bean.Knowledge
import com.mohe.ktwana.bean.KnowledgeTreeBody
import com.mohe.ktwana.constant.Constant
import kotlinx.android.synthetic.main.activity_knowledge.*

class KnowledgeActivity : BaseSwipeBackActivity() {
    override fun attachLayoutRes(): Int =R.layout.activity_knowledge

    lateinit var adapter:FragmentStatePagerAdapter
    val knowledges= mutableListOf<Knowledge>()
    val titles= arrayListOf<String>()

    lateinit var toobarTitle:String
    override fun initData() {
        intent.extras.run {
            toobarTitle=getString(Constant.CONTENT_TITLE_KEY)
            val data:KnowledgeTreeBody=getSerializable(Constant.CONTENT_DATA_KEY) as KnowledgeTreeBody
            knowledges.addAll(data.children)
        }
        knowledges.forEach{
            titles.add(it.name)
        }
    }

    override fun initView() {
        adapter=KnowledgePageAdapter(knowledges,supportFragmentManager)

        viewPager.run {
            adapter=this@KnowledgeActivity.adapter
        }

        toolbar.run {
            title=toobarTitle
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }


        tabLayout.setViewPager(viewPager,titles.toTypedArray())


    }




    override fun start() {
    }
}
