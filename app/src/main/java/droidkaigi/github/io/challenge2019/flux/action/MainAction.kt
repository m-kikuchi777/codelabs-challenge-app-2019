package droidkaigi.github.io.challenge2019.flux.action

import droidkaigi.github.io.challenge2019.data.api.response.Item

sealed class MainAction<out T>(override val type: String) : Action<T> {

    class FetchStory(override val data: Item) : MainAction<Item>(TYPE) {
        companion object {
            const val TYPE = "MainAction.FetchStory"
        }
    }

    class FailedFetchStory(override val data: Throwable) : MainAction<Throwable>(TYPE) {
        companion object {
            const val TYPE = "MainAction.FailedFetchStory"
        }
    }

    class RefreshTopStories(override val data: List<Item?>) : MainAction<List<Item?>>(TYPE) {
        companion object {
            const val TYPE = "MainAction.RefreshTopStories"
        }
    }
}
