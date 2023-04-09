package com.xihad.androidutils.observers

object Observer : Subject {

    private val observerList: MutableSet<Spectator?> = HashSet()

    override fun register(spectator: Spectator?) {
        observerList.add(spectator)
    }

    override fun unregister(spectator: Spectator?) {
        observerList.remove(spectator)
    }

    override fun notifyObservers(key: String, data: Any) {
        for (spectator in observerList) {
            spectator?.update(Pair(key, data))
        }
    }

    override fun clear() {
        observerList.clear()
    }


}