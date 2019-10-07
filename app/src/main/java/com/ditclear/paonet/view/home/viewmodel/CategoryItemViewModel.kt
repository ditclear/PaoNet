package com.ditclear.paonet.view.home.viewmodel

import com.ditclear.paonet.model.data.Category
import com.ditclear.paonet.model.data.WCategory

/**
 * 页面描述：CategoryItemViewModel
 *
 * Created by ditclear on 2017/11/5.
 */
class CategoryItemViewModel(cate: Category) {

    var catename = cate.catename
    var value: Int? = cate.value
    var count: Int? = cate.count

}

class CategoryItemViewModelWrapper(cate: WCategory) {

    var catename = cate.name
    var value: Int? = cate.id
    var count: Int? = 0

}