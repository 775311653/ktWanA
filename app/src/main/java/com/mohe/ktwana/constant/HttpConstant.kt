package com.mohe.ktwana.constant

/**
 * Created by xiePing on 2018/9/1 0001.
 * Description:
 */
object HttpConstant {
    const val DEFAULT_TIMEOUT: Long = 15
    const val SAVE_USER_LOGIN_KEY="user/login"
    const val SAVE_USER_REGISTER_KEY="user/register"

    const val COLLECTIONS_WEBSITE = "lg/collect"
    const val UNCOLLECTIONS_WEBSITE = "lg/uncollect"
    const val ARTICLE_WEBSITE = "article"
    const val TODO_WEBSITE = "lg/todo"

    const val SET_COOKIE_KEY = "set-cookie"
    const val COOKIE_NAME = "Cookie"
    /**
     * 把响应体response里面的所有set-cookie的值取出来，删除重复的，给所有字段都加上;号
     */
    fun encodeCookie(cookies:List<String>):String{
        val sb=StringBuilder()
        val set=HashSet<String>()
        cookies
                .map {
                    it.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                }
                .forEach{
                    it.filterNot { set.contains(it) }.forEach{set.add(it)}
                }
        val ite=set.iterator()
        while (ite.hasNext()){
            val cookie=ite.next()
            sb.append(cookie).append(";")
        }
        //删除最后一个;号
        val last=sb.lastIndexOf(";")
        if (last==sb.length-1){
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }
}