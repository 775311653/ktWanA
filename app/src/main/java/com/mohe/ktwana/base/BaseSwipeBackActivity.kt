package com.mohe.ktwana.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.Utils
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

abstract class BaseSwipeBackActivity : BaseActivity(),SwipeBackActivityBase {

    private lateinit var mHelper: SwipeBackActivityHelper

    /**
     * SwipeBack Enable
     */
    open fun enableSwipeBack(): Boolean = true

    /**
     * 初始化 SwipeBack
     */
    private fun initSwipeBack() {
        setSwipeBackEnable(enableSwipeBack())
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHelper = SwipeBackActivityHelper(this)
        mHelper.onActivityCreate()
        initSwipeBack()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper.onPostCreate()
    }

    override fun getSwipeBackLayout(): SwipeBackLayout {
        return mHelper.getSwipeBackLayout()
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout.setEnableGesture(enable)
    }

    override fun scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this)
        swipeBackLayout.scrollToFinishActivity()
    }
}
