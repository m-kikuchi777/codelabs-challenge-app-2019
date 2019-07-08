package droidkaigi.github.io.challenge2019

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object Schedulers {
    val Main get() = AndroidSchedulers.mainThread()
    val IO get() = Schedulers.io()
}