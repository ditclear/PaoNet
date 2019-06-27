package com.ditclear.paonet.view.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ditclear.paonet.helper.extens.*
import com.ditclear.paonet.model.data.UserModel
import com.ditclear.paonet.model.repository.UserRepository
import com.ditclear.paonet.viewmodel.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern



/**
 * 页面描述：LoginViewModel
 *
 * Created by ditclear on 2017/10/10.
 */
class LoginViewModel
constructor(private val repo: UserRepository) : BaseViewModel() {

    private val PASSWORD_PATTERN = "^[a-zA-Z0-9_]{6,16}$"
    private val EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"

    private val emailPattern = Pattern.compile(EMAIL_PATTERN)
    private val passwordPattern = Pattern.compile(PASSWORD_PATTERN)


    val showLogin = MutableLiveData<Boolean>().init(true)


    val showLogout = MutableLiveData<Boolean>().init(false)


    val email = MutableLiveData<String>().init("")

    val password = MutableLiveData<String>().init("")

    init {

        Flowable.combineLatest(password.toFlowable<String>(), email.toFlowable<String>(), BiFunction<String, String, Boolean> { t1, t2 ->
            return@BiFunction !(!emailPattern.matcher(t2).matches()
                    || !passwordPattern.matcher(t1).matches())
        }).doOnNext { isBtnEnabled.set(it) }.subscribe()

        showLogin.toFlowable().doOnNext {
            loading.set(!(showLogin.get(false) || showLogout.get(false)))
            loginVisibility.set(showLogin.get(false))
        }.subscribe()

        showLogout.toFlowable().doOnNext {
            loading.set(!(showLogin.get(false) || showLogout.get(false)))
            logoutVisibility.set(showLogout.get(false))
        }.subscribe()
    }

    val isBtnEnabled = MutableLiveData<Boolean>().init(false)
    val loading = MutableLiveData<Boolean>().init(false)


    val loginVisibility = MutableLiveData<Boolean>().init(true)

    val logoutVisibility = MutableLiveData<Boolean>().init(false)


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