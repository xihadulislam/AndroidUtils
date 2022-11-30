package com.xihad.androidutils.observers

/**
 * created by @ziad ( 30/11/2022  )
 */
interface Subject {

    fun register(spectator: Spectator?)

    fun unregister(spectator: Spectator?)

    fun notifyObservers(message: String)

}