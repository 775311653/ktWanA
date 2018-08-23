package com.mohe.ktwana.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.blankj.utilcode.util.NetworkUtils
import com.mohe.ktwana.event.NetworkChangeEvent
import org.greenrobot.eventbus.EventBus

/**
 * Created by xiePing on 2018/8/23 0023.
 * Description:网络变化接收器
 */
class NetworkChangeReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            EventBus.getDefault().post(NetworkChangeEvent(NetworkUtils.isConnected()))
        }
    }
}