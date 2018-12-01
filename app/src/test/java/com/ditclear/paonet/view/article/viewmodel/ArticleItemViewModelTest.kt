package com.ditclear.paonet.view.article.viewmodel

import com.ditclear.paonet.model.data.Article
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * 页面描述：ArticleItemViewModelTest
 *
 * Created by ditclear on 2018/11/19.
 */
class ArticleItemViewModelTest {


    private lateinit var mArticleItemViewModel:ArticleItemViewModel

    @Mock
    lateinit var mDetail:Article

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

    }

    @Test
    fun getCodeDateAndClicks() {
        Mockito.`when`(mDetail.click).thenReturn(2)
        Mockito.`when`(mDetail.stow).thenReturn(3)
        Mockito.`when`(mDetail.pubDate).thenReturn("2018/11/19")
        mArticleItemViewModel = ArticleItemViewModel(mDetail)
        Assert.assertEquals("2 查看  3 收藏\t2018/11/19",mArticleItemViewModel.codeDateAndClicks)
    }
}