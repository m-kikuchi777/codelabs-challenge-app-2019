package droidkaigi.github.io.challenge2019

import droidkaigi.github.io.challenge2019.data.api.HackerNewsApi
import droidkaigi.github.io.challenge2019.data.api.HackerNewsApiResult
import droidkaigi.github.io.challenge2019.data.api.response.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch

class HackerNewsRepository(private val hackerNewsApi: HackerNewsApi) {

    fun fetchTopStories(): HackerNewsApiResult {
        try {
            val ids = hackerNewsApi.getTopStories().execute().body()?.take(20) ?: listOf()

            val itemMap = ConcurrentHashMap<Long, Item?>()
            val latch = CountDownLatch(ids.size)

            ids.forEach { id ->
                hackerNewsApi.getItem(id).enqueue(object : Callback<Item> {
                    override fun onResponse(call: Call<Item>, response: Response<Item>) {
                        response.body()?.let { item -> itemMap[id] = item }
                        latch.countDown()
                    }

                    override fun onFailure(call: Call<Item>, t: Throwable) {
                        latch.countDown()
                    }
                })
            }

            latch.await()

            return HackerNewsApiResult.Success(ids.map { itemMap[it] })
        } catch (e: Exception) {
            return HackerNewsApiResult.Failed(e)
        }
    }

    fun getItem(id: Long): HackerNewsApiResult {
        return try {
            HackerNewsApiResult.Success(hackerNewsApi.getItem(id).execute().body())
        } catch (e: Exception) {
            HackerNewsApiResult.Failed(e)
        }
    }
}