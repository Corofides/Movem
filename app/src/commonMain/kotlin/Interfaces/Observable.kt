package Interfaces

/**
 * Observable
 */
interface Observable {

    val observers: ArrayList<Observer>

    /**
     * Add
     * @param observer
     */
    fun add(observer: Observer) {
        observers.add(observer);
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
    fun sendUpdateEvent(event: String) {
        observers.forEach { it.onNotify(event) }
    }

}
