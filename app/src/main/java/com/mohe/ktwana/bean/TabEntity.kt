package com.mohe.ktwana.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * Created by xiePing on 2018/8/27 0027.
 * Description:
 */
class TabEntity :CustomTabEntity{
    var title:String=""
    var selectIcon:Int=-1
    var unSelectIcon:Int=-1



    constructor(title: String, selectIcon: Int, unSelectIcon: Int) {
        this.title = title
        this.selectIcon = selectIcon
        this.unSelectIcon = unSelectIcon
    }

    constructor()


    override fun getTabUnselectedIcon(): Int =unSelectIcon

    override fun getTabSelectedIcon(): Int =selectIcon

    override fun getTabTitle(): String=title
}