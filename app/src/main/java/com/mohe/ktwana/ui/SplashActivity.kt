package com.mohe.ktwana.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.mohe.ktwana.MainActivity
import com.mohe.ktwana.R
import com.mohe.ktwana.base.BaseActivity
import com.mohe.ktwana.constant.Constant
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {
    override fun attachLayoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        Constant.isLogin=SPUtils.getInstance().getBoolean(Constant.LOGIN_KEY)
    }

    override fun initView() {
        showSplashView()
    }

    override fun start() {
    }

    private fun showSplashView() {
        var anim = ObjectAnimator.ofFloat(splash_rl_parent, "alpha", 0.3f, 1.0f)
                .setDuration(2000)
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                ActivityUtils.startActivity(MainActivity::class.java)
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                finish()
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }
        })
        anim.start()
    }


}
