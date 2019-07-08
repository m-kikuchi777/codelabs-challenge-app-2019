package droidkaigi.github.io.challenge2019.flux.action

interface Action<out T> {
    val type: String
    val data: T
}
