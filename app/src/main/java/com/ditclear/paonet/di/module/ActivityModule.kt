package com.ditclear.paonet.di.module

import android.support.v7.app.AppCompatActivity
import com.ditclear.paonet.di.component.FragmentComponent
import dagger.Module
import dagger.Provides

/**
 * 页面描述：ActivityModule
 *
 * Created by ditclear on 2017/9/26.
 */
@Module(subcomponents = [(FragmentComponent::class)])
class ActivityModule(private val activity: AppCompatActivity){

    @Provides
    fun provideActivity():AppCompatActivity=activity


}