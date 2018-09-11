package com.mohe.ktwana.http

import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.mohe.ktwana.BuildConfig
import com.mohe.ktwana.api.ApiService
import com.mohe.ktwana.constant.Constant
import com.mohe.ktwana.constant.HttpConstant
import com.mohe.ktwana.utils.Preference
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by xiePing on 2018/9/1 0001.
 * Description:
 */
object RetrofitHelper {
    private var retrofit: Retrofit? =null

    val service:ApiService by lazy { getRetrofit()!!.create(ApiService::class.java) }

    private fun getRetrofit(): Retrofit? {
        if (retrofit==null){
            synchronized(RetrofitHelper::class.java){
                if (retrofit==null){
                    retrofit=Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .client(getOkHttpClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build()
                }
            }
        }
        return retrofit
    }

    private fun getOkHttpClient(): OkHttpClient {
        val builder=OkHttpClient.Builder()
        val httpLoggingInterceptor=HttpLoggingInterceptor()
        httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
//        if (BuildConfig.DEBUG){
//            httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
//        }else httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.NONE
        val cacheFile=FileUtils.getFileByPath(Utils.getApp().cacheDir.absolutePath+"/retrofitCache/")
        val cache=Cache(cacheFile,1024*1024*50)//50M的缓存
        builder.run {
            addInterceptor(getHttpHeaderInterceptor())
            addInterceptor(getCacheInterceptor())
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(getSaveCookieInterceptor())
            addInterceptor {
                val request = it.request()
                val builder = request.newBuilder()
                val domain = request.url().host()
                val url = request.url().toString()
                if (domain.isNotEmpty() && (url.contains(HttpConstant.COLLECTIONS_WEBSITE)
                                || url.contains(HttpConstant.UNCOLLECTIONS_WEBSITE)
                                || url.contains(HttpConstant.ARTICLE_WEBSITE)
                                || url.contains(HttpConstant.TODO_WEBSITE))) {
                    val spDomain: String by Preference(domain, "")
                    val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
                    if (cookie.isNotEmpty()) {
                        builder.addHeader(HttpConstant.COOKIE_NAME, cookie)
                    }
                }
                it.proceed(builder.build())
            }
            cache(cache)
            connectTimeout(HttpConstant.DEFAULT_TIMEOUT,TimeUnit.SECONDS)
            writeTimeout(HttpConstant.DEFAULT_TIMEOUT,TimeUnit.SECONDS)
            readTimeout(HttpConstant.DEFAULT_TIMEOUT,TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
        }
        return builder.build()
    }


    private fun getSaveCookieInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request=chain.request()
            val response=chain.proceed(request)
            val url=request.url().toString()
            val domain=request.url().host()
            if ((url.contains(HttpConstant.SAVE_USER_LOGIN_KEY)||
                            url.contains(HttpConstant.SAVE_USER_REGISTER_KEY))
                            &&!response.headers(HttpConstant.SET_COOKIE_KEY).isEmpty()){
                val cookies=response.headers(HttpConstant.SET_COOKIE_KEY)
                saveCookie(url,domain,HttpConstant.encodeCookie(cookies))
            }

            response
        }
    }

    /**
     * 设置clien的请求头
     */
    private fun getHttpHeaderInterceptor():Interceptor{
        return Interceptor {chain ->
            val builder=chain.request().newBuilder()
            val request=builder.addHeader("Content-type","application/json;chatset=utf-8").build()
            chain.proceed(request)
        }
    }

    private fun getCacheInterceptor():Interceptor{
        return Interceptor { chain ->
            var request=chain.request()
            if (!NetworkUtils.isConnected()){
                request=request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val response=chain.proceed(request)
            if (NetworkUtils.isConnected()){
                val maxAge=0
                // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                response.newBuilder()
                        .header("Cache-Control","public,max-age=$maxAge")
                        .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build()
            }else{
                // 无网络时，设置超时为4周  只对get有用,post没有缓冲
                val maxStale=60*60*24*28
                response.newBuilder()
                        .header("Cache-Control","public,only-if-cache,max-stale=$maxStale")
                        .removeHeader("nyn")
                        .build()
            }
            response
        }
    }

    /**
     * @param url 完整地址
     * @param domain url的主机地址
     * @param cookies 返回的内容体头部的cookie
     */
    private fun saveCookie(url:String?,domain:String?,cookies:String){
        url ?: return
        var spUrl:String by Preference(url,cookies)
        spUrl=cookies
        domain?:return
        var spDomain:String by Preference(domain,cookies)
        spDomain=cookies
    }

}