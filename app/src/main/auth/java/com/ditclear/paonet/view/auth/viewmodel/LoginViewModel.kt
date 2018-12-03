package com.ditclear.paonet.view.auth.viewmodel

import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.ditclear.paonet.helper.extens.async
import com.ditclear.paonet.helper.extens.getOriginData
import com.ditclear.paonet.model.data.UserModel
import com.ditclear.paonet.model.repository.UserRepository
import com.ditclear.paonet.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import javax.inject.Inject


/**
 * 页面描述：LoginViewModel
 *
 * Created by ditclear on 2017/10/10.
 */
class LoginViewModel @Inject constructor(private val repo: UserRepository) : BaseViewModel() {

    private val PASSWORD_PATTERN = "^[a-zA-Z0-9_]{6,16}$"
    private val EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"

    private val emailPattern = Pattern.compile(EMAIL_PATTERN)
    private val passwordPattern = Pattern.compile(PASSWORD_PATTERN)


    val showLogin = ObservableBoolean(true)


    val showLogout = ObservableBoolean()


    val email = ObservableField<String>("")

    val password = ObservableField<String>("")

    private val callback: Observable.OnPropertyChangedCallback by lazy {
        object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (sender) {
                    password, email -> {
                        isBtnEnabled.set(isBtnEnabled())
                    }
                    showLogin -> {
                        loading.set(!(showLogin.get() || showLogout.get()))
                        loginVisibility.set(showLogin.get())
                    }
                    showLogout -> {
                        loading.set(!(showLogin.get() || showLogout.get()))
                        logoutVisibility.set(showLogout.get())
                    }

                }
            }

        }
    }


    init {
        password.addOnPropertyChangedCallback(callback)
        email.addOnPropertyChangedCallback(callback)
        showLogin.addOnPropertyChangedCallback(callback)
        showLogout.addOnPropertyChangedCallback(callback)
    }

    val isBtnEnabled = ObservableBoolean()
    val loading = ObservableBoolean()

    fun isBtnEnabled(): Boolean {
        return !(!emailPattern.matcher(email.get()).matches()
                || !passwordPattern.matcher(password.get()).matches())
    }

    val loginVisibility = ObservableBoolean(true)

    val logoutVisibility = ObservableBoolean(false)


    fun attemptToLogIn() = repo.login(email.get() ?: "", password.get() ?: "")
            .subscribeOn(Schedulers.io())
            .delay(1, TimeUnit.SECONDS)
            .getOriginData()
            .flatMap { repo.myProfile().map { t: UserModel -> t.model } }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                showLogin.set(false)
                showLogout.set(false)
            }
            .doAfterTerminate { showLogin.set(true) }


    fun attemptToLogout() = repo.logout().getOriginData().async(1000)
            .doOnSubscribe {
                showLogin.set(false)
                showLogout.set(false)
            }
            .doFinally { showLogout.set(true) }


}