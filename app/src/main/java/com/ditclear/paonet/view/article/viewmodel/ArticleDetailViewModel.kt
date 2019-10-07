package com.ditclear.paonet.view.article.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.helper.SpUtil
import com.ditclear.paonet.helper.Utils
import com.ditclear.paonet.helper.extens.async
import com.ditclear.paonet.helper.extens.getOriginData
import com.ditclear.paonet.helper.extens.init
import com.ditclear.paonet.helper.extens.set
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.data.BaseResponse
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.model.repository.UserRepository
import com.ditclear.paonet.viewmodel.BaseViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * 页面描述：ArticleDetailViewModel
 *
 * Created by ditclear on 2017/10/3.
 */
class  ArticleDetailViewModel constructor(val title:String) : BaseViewModel() {


}
