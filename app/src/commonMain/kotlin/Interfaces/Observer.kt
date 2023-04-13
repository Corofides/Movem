package Interfaces

import Enums.*

/**
 * Observer
 */
interface Observer {
    fun onNotify(event: Event)
}
