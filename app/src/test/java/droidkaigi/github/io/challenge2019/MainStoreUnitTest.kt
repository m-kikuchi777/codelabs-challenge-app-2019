package droidkaigi.github.io.challenge2019

import droidkaigi.github.io.challenge2019.data.api.response.Item
import droidkaigi.github.io.challenge2019.flux.action.MainAction
import droidkaigi.github.io.challenge2019.flux.dispatcher.Dispatcher
import droidkaigi.github.io.challenge2019.flux.store.MainStore
import io.mockk.mockk
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MainStoreUnitTest {
    private lateinit var mainStore: MainStore
    private lateinit var dispatcher: Dispatcher

    @Before
    fun setUp() {
        dispatcher = Dispatcher()
        mainStore = MainStore(dispatcher, Schedulers.IO)
    }

    @Test
    fun onRefreshTopStoriesAction_ObserveItemList() {
        val itemList = listOf(mockk<Item>())
        dispatcher.dispatch(MainAction.RefreshTopStories(itemList))
        runBlocking { delay(500) }

        val testSubscriber = TestSubscriber.create<List<Item?>>()
        mainStore.topStories.subscribe(testSubscriber)

        testSubscriber.assertSubscribed()
        testSubscriber.assertValueCount(1)
        assertEquals(itemList, testSubscriber.values()[0])
    }

    @Test
    fun onFetchStoryAction_ObserveItem() {
        val item = mockk<Item>()
        dispatcher.dispatch(MainAction.FetchStory(item))
        runBlocking { delay(500) }

        val testSubscriber = TestSubscriber.create<Item>()
        mainStore.story.subscribe(testSubscriber)

        testSubscriber.assertSubscribed()
        testSubscriber.assertValueCount(1)
        assertEquals(item, testSubscriber.values()[0])
    }

    @Test
    fun onFailedFetchStoryAction_ObserveThrowable() {
        val throwable = mockk<Throwable>()
        dispatcher.dispatch(MainAction.FailedFetchStory(throwable))
        runBlocking { delay(500) }

        val testSubscriber = TestSubscriber.create<Throwable>()
        mainStore.failed.subscribe(testSubscriber)

        testSubscriber.assertSubscribed()
        testSubscriber.assertValueCount(1)
        assertEquals(throwable, testSubscriber.values()[0])
    }
}