package com.ditclear.paonet

import io.reactivex.Maybe
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        val a:String?=null
        Maybe.just(1)
                .map { a }
                .map { t:String? -> t?.let { return@let "$t name"  }}
                .subscribe({t: String? -> print(t?:"none") }
                        ,{t: Throwable? -> t?.printStackTrace() })

    }
}