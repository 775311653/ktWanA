package com.mohe.ktwana.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.mohe.ktwana.R
import com.mohe.ktwana.base.BaseActivity
import com.mohe.ktwana.bean.LoginData
import com.mohe.ktwana.constant.Constant
import com.mohe.ktwana.event.LoginEvent
import com.mohe.ktwana.mvp.contract.LoginContract
import com.mohe.ktwana.mvp.presenter.LoginPresenter
import com.mohe.ktwana.utils.DialogUtils
import com.mohe.ktwana.utils.Preference
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseActivity(),LoginContract.View, View.OnClickListener {


    override fun attachLayoutRes(): Int =R.layout.activity_login

    private val mPresenter:LoginPresenter by lazy {
        LoginPresenter()
    }
    /**
     * local username
     */
    private var user: String by Preference(Constant.USERNAME_KEY, "")

    /**
     * local password
     */
    private var pwd: String by Preference(Constant.PASSWORD_KEY, "")

    override fun useEventBus(): Boolean =true

    override fun initData() {

    }

    override fun initView() {
        mPresenter.attachView(this)
        et_user_name.setText(user)
        btn_login.setOnClickListener(this)
        tv_sign_up.setOnClickListener(this)

    }

    override fun start() {

    }

    override fun loginSuccess(loginData: LoginData) {
        isLogin=true
        user=loginData.username
        pwd=loginData.password
        EventBus.getDefault().post(LoginEvent(true))
        finish()
    }

    override fun loginFail() {
        ToastUtils.showShort("登录失败")
    }

    private val loadingDialog by lazy {
        DialogUtils.getLoadingDialog(this,getString(R.string.login_ing))
    }

    override fun showLoading() {
        loadingDialog.show()
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
    }

    override fun showError(errorMsg: String) {
        ToastUtils.showShort(errorMsg)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_login->{
                if (validata()){
                    mPresenter.loginWanAndroid(et_user_name.text.toString(),et_password.text.toString())
                }
            }
            R.id.tv_sign_up->{
                ActivityUtils.startActivity(RegisterActivity::class.java)
            }
        }
    }

    private fun validata(): Boolean {
        var valid=true
        val userName=et_user_name.text.toString()
        val password=et_password.text.toString()
        if (StringUtils.isEmpty(userName)){
            et_user_name.error=getString(R.string.username_not_empty)
            valid=false
        }
        if (StringUtils.isEmpty(password)){
            et_password.error=getString(R.string.password_not_empty)
            valid=false
        }
        return valid
    }


}
