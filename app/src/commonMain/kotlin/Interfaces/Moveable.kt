package Interfaces

import Enums.*

/**
 * Moveable
 */
interface Moveable {
    var moving: Boolean
    fun move(direction: Direction): Boolean
    fun canMove(direction: Direction): Boolean
}
