package Commands

import Enums.*
import Interfaces.*

/**
 * Move Command
 *
 * @param actor
 * @param direction
 */
class MoveCommand (
    actor: Moveable,
    direction: Direction
): Command {
    private val actor: Moveable = actor
    private var direction: Direction = direction

    // Converting the direction to the opposite direction. Since we have 4 values 0, 1, 2, 3 and the
    // difference between the opposites is 2, we can just add 2 and mod by 4 to get the correct value.
    // This will allow us to undo our move if we ever need this.
    private var oppositeDirection: Direction = Direction.values()[(direction.ordinal + 2) % 4]

    /**
     * exec
     *
     * @return Boolean
     */
    override fun exec(): Boolean {
        return actor.move(direction)
    }

    /**
     * undo
     */
    override fun undo() {
        val curDirection = direction
        direction = oppositeDirection
        oppositeDirection = curDirection
        exec()
    }

}
