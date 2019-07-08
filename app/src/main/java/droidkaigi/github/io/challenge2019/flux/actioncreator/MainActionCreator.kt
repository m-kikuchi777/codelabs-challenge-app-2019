package droidkaigi.github.io.challenge2019.flux.actioncreator

import droidkaigi.github.io.challenge2019.HackerNewsRepository
import droidkaigi.github.io.challenge2019.data.api.HackerNewsApiResult
import droidkaigi.github.io.challenge2019.data.api.response.Item
import droidkaigi.github.io.challenge2019.flux.action.MainAction
import droidkaigi.github.io.challenge2019.flux.dispatcher.Dispatcher
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActionCreator(
    private val hackerNewsRepository: HackerNewsRepository,
    private val dispatcher: Dispatcher) : CoroutineScope {

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = CoroutineScope(Dispatchers.IO + job).coroutineContext

    /**
     * TopStoriesを取得する。
     */
    fun loadTopStories() {
        launch {
            when (val result = hackerNewsRepository.fetchTopStories()) {
                is HackerNewsApiResult.Success<*> -> {
                    dispatcher.dispatch(MainAction.RefreshTopStories(result.data as List<Item?>))
                }
                is HackerNewsApiResult.Failed -> {
                    dispatcher.dispatch(MainAction.FailedFetchStory(result.throwable))
                }
            }
        }
    }

    /**
     * 特定のアイテムを取得する。
     */
    fun getItem(id: Long) {
        launch {
            when (val result = hackerNewsRepository.getItem(id)) {
                is HackerNewsApiResult.Success<*> -> {
                    dispatcher.dispatch(MainAction.FetchStory(result.data as Item))
                }
                is HackerNewsApiResult.Failed -> {
                    dispatcher.dispatch(MainAction.FailedFetchStory(result.throwable))
                }
            }
        }
    }

    /**
     * 実行されているリクエストをキャンセルする。
     */
    fun cancel() {
        job.cancel()
        job = Job()
    }
}