package com.mohe.ktwana.widget.behavior

import android.animation.ObjectAnimator
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View

/**
 * Created by xiePing on 2018/8/26 0026.
 * Description:
 */
class BottomNavgationBehavior: CoordinatorLayout.Behavior<View> {
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet)

    lateinit var outAnimator:ObjectAnimator
    lateinit var inAnimator:ObjectAnimator

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes== ViewCompat.SCROLL_AXIS_VERTICAL ||super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        if (dyConsumed>0){//手势向上滑，内容往下走
            if (outAnimator==null){
                outAnimator=ObjectAnimator.ofFloat(child,"translationY",0f,child.height.toFloat())
                outAnimator.duration=200
            }
            if (!outAnimator.isRunning&&child.translationY<=0){
                outAnimator.start()
            }
        }else if (dyConsumed<0){
            if (inAnimator==null){
                inAnimator=ObjectAnimator.ofFloat(child,"translationY",child.height.toFloat(),0f)
                inAnimator.duration=200
            }
            if (!inAnimator.isRunning&&child.translationY>=child.height){
                inAnimator.start()
            }
        }
    }
}