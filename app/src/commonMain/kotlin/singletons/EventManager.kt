package singletons

import Enums.*
import Interfaces.*

/**
 * Event Manager
 */
object EventManager {
    private val observers: ArrayList<Observer> = ArrayList()

    /**
     * Add
     * @param observer
     */
    fun add(observer: Observer) {
        observers.add(observer)
    }

    /**
     * Remove
     * @param observer
     */
    fun remove(observer: Observer) {
        observers.remove(observer)
    }

    /**
     * Send Update Event
     * @param event
     */
    fun sendUpdateEvent(event: Event) {
        observers.forEach { it.onNotify(event) }
    }

}

