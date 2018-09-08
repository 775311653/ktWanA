package com.mohe.ktwana.ui.activity

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.mohe.ktwana.R
import com.mohe.ktwana.base.BaseActivity
import com.mohe.ktwana.bean.LoginData
import com.mohe.ktwana.constant.Constant
import com.mohe.ktwana.event.LoginEvent
import com.mohe.ktwana.mvp.contract.RegisterContract
import com.mohe.ktwana.mvp.presenter.RegisterPresenter
import com.mohe.ktwana.utils.DialogUtils
import com.mohe.ktwana.utils.Preference
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_regist.*
import org.greenrobot.eventbus.EventBus

class RegisterActivity : BaseActivity(),RegisterContract.View {

    val loadingDialog:ProgressDialog by lazy {
        DialogUtils.getLoadingDialog(this,getString(R.string.register_ing))
    }
    val mPresenter:RegisterPresenter by lazy {
        RegisterPresenter()
    }

    /**
     * local username
     */
    private var user: String by Preference(Constant.USERNAME_KEY, "")

    /**
     * local password
     */
    private var pwd: String by Preference(Constant.PASSWORD_KEY, "")

    override fun attachLayoutRes(): Int =R.layout.activity_regist

    override fun initData() {
    }

    override fun initView() {
        mPresenter.attachView(this)
        tv_register.setOnClickListener{
            if(validata()){
                mPresenter.registerWanAnadroid(et_register_userName.text.toString(),
                        et_register_password.text.toString(),
                        et_register_repassword.text.toString())
            }
        }
        tv_sign_in.setOnClickListener {
            finish()
        }
    }

    private fun validata(): Boolean {
        var valid=true
        val username: String = et_register_userName.text.toString()
        val password: String = et_register_password.text.toString()
        val password2: String = et_register_repassword.text.toString()
        if (username.isEmpty()) {
            et_register_userName.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            et_register_password.error = getString(R.string.password_not_empty)
            valid = false
        }
        if (password2.isEmpty()) {
            et_register_repassword.error = getString(R.string.confirm_password_not_empty)
            valid = false
        }
        if (password != password2) {
            et_register_repassword.error = getString(R.string.password_cannot_match)
            valid = false
        }
        return valid
    }

    override fun start() {
    }

    override fun showRegisterSuccess(loginData: LoginData) {
        isLogin=true
        user=loginData.username
        pwd=loginData.password
        EventBus.getDefault().post(LoginEvent(true))
        ToastUtils.showShort(getString(R.string.register_success))
        ActivityUtils.finishActivity(LoginActivity::class.java)
        finish()
    }

    override fun showRegisterFail() {
        isLogin=false
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

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}
