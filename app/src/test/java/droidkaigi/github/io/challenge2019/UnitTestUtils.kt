package droidkaigi.github.io.challenge2019

import kotlinx.coroutines.runBlocking

class UnitTestUtils {
    companion object {
        fun delay(timeMillis: Long) {
            runBlocking {
                delay(timeMillis)
            }
        }
    }
}