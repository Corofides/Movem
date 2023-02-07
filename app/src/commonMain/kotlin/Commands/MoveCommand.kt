package Commands

import Enums.*

import Interfaces.*

class MoveCommand (
    actor: Moveable,
    direction: Direction
): Command {

    private val actor: Moveable = actor;
    private var direction: Direction = direction;

    // Converting the direction to the opposite direction. Since we have 4 values 0, 1, 2, 3 and the
    // difference between the opposites is 2, we can just add 2 and mod by 4 to get the correct value.
    // This will allow us to undo our move if we ever need this.
    private var oppositeDirection: Direction = Direction.values()[(direction.ordinal + 2) % 4];

    //private var _previousX: Double = 0.0;
    //private var _previousY: Double = 0.0;

    override fun exec() {

        actor.move(direction);

        //_previousX = actor.getPositionX();
        //_previousY = actor.getPositionY();

        //actor.move(x, y);

    }

    override fun undo() {

        val curDirection = direction;
        direction = oppositeDirection;
        oppositeDirection = curDirection;
        //x = _previousX;
        //y = _previousY;

        exec();
    }

}
