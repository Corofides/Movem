package Commands

import Enums.*

import Interfaces.*

class MoveCommand (
    actor: Moveable,
    direction: Direction
): Command {

    private val actor: Moveable = actor;
    private var direction: Direction = direction;

    //Temping this as otherwise intellij is going to waste an hour indexing the project for some fucking reason.
    private var oppositeDirection: Direction = Direction.SOUTH; // = (direction + 2) % 4;

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
