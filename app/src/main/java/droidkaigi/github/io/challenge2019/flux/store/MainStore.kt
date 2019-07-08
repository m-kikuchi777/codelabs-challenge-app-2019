package droidkaigi.github.io.challenge2019.flux.store

import droidkaigi.github.io.challenge2019.Schedulers
import droidkaigi.github.io.challenge2019.data.api.response.Item
import droidkaigi.github.io.challenge2019.flux.action.MainAction
import droidkaigi.github.io.challenge2019.flux.dispatcher.Dispatcher
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.processors.BehaviorProcessor

/**
 * 受け取ったActionをMainActivityに通知するためのクラス。
 */
class MainStore(
    dispatcher: Dispatcher,
    schedulers: Scheduler = Schedulers.Main) {

    private val _topStories
            = BehaviorProcessor.create<List<Item?>>()
    private val _story = BehaviorProcessor.create<Item>()
    private val _failed = BehaviorProcessor.create<Throwable>()
    val topStories: Flowable<List<Item?>> = _topStories
    val story: Flowable<Item> = _story
    val failed: Flowable<Throwable> = _failed

    init {
        dispatcher.on(MainAction.RefreshTopStories.TYPE)
            .map { (it as MainAction.RefreshTopStories).data }
            .observeOn(schedulers)
            .subscribe(_topStories)

        dispatcher.on(MainAction.FetchStory.TYPE)
            .map { (it as MainAction.FetchStory).data }
            .observeOn(schedulers)
            .subscribe(_story)

        dispatcher.on(MainAction.FailedFetchStory.TYPE)
            .map { (it as MainAction.FailedFetchStory).data }
            .observeOn(schedulers)
            .subscribe(_failed)
    }
}
