package droidkaigi.github.io.challenge2019.flux.dispatcher

import droidkaigi.github.io.challenge2019.flux.action.Action
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

/**
 * Dispatcherクラス。
 */
class Dispatcher {

    private val dispatcherProcessor: FlowableProcessor<Action<*>>
            = PublishProcessor.create<Action<*>>()

    fun <T> dispatch(action: Action<T>) {
        dispatcherProcessor.onNext(action)
    }

    fun on(type: String) = dispatcherProcessor
        .filter { it.type == type }
}
