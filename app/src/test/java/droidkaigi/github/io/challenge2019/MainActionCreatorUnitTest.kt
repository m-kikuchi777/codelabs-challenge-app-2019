package droidkaigi.github.io.challenge2019

import droidkaigi.github.io.challenge2019.data.api.HackerNewsApiResult
import droidkaigi.github.io.challenge2019.data.api.response.Item
import droidkaigi.github.io.challenge2019.flux.action.MainAction
import droidkaigi.github.io.challenge2019.flux.actioncreator.MainActionCreator
import droidkaigi.github.io.challenge2019.flux.dispatcher.Dispatcher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainActionCreatorUnitTest {

    lateinit var mainActionCreator: MainActionCreator
    val mockHackerNewsRepository = mockk<HackerNewsRepository>()
    val mockDispatcher = mockk<Dispatcher>(relaxed = true)

    @Test
    fun loadTopStoriesWithSuccess_DispatchRefreshTOpStoriesAction() {
        coEvery {
            mockHackerNewsRepository.fetchTopStories()
        } returns HackerNewsApiResult.Success<List<Long>>(listOf())
        mainActionCreator = MainActionCreator(mockHackerNewsRepository, mockDispatcher)

        mainActionCreator.loadTopStories()

        coVerify { mockDispatcher.dispatch(ofType(MainAction.RefreshTopStories::class)) }
    }

    @Test
    fun loadTopStoriesWithFailed_DispatchFailedFetchStoryAction() {
        coEvery { mockHackerNewsRepository.fetchTopStories() } returns HackerNewsApiResult.Failed(Throwable())
        mainActionCreator = MainActionCreator(mockHackerNewsRepository, mockDispatcher)

        mainActionCreator.loadTopStories()

        coVerify { mockDispatcher.dispatch(ofType(MainAction.FailedFetchStory::class)) }
    }

    @Test
    fun getItemWithSuccess_DispatchFetchStory() {
        coEvery { mockHackerNewsRepository.getItem(any()) } returns HackerNewsApiResult.Success<Item>(mockk())
        mainActionCreator = MainActionCreator(mockHackerNewsRepository, mockDispatcher)

        mainActionCreator.getItem(1)

        coVerify { mockDispatcher.dispatch(ofType(MainAction.FetchStory::class)) }
    }

    @Test
    fun getItemWithFailed_DispatchFetchStory() {
        coEvery { mockHackerNewsRepository.getItem(any()) } returns HackerNewsApiResult.Failed(Throwable())
        mainActionCreator = MainActionCreator(mockHackerNewsRepository, mockDispatcher)

        mainActionCreator.getItem(1)

        coVerify { mockDispatcher.dispatch(ofType(MainAction.FailedFetchStory::class)) }
    }

}