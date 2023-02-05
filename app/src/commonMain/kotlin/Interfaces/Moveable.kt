package Interfaces

import Enums.*

interface Moveable {

    fun move(direction: Direction);

    fun move(x: Double, y:Double);
    fun getPositionX(): Double;
    fun getPositionY(): Double;

}
