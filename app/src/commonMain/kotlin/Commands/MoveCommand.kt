package Commands

import Interfaces.*

class MoveCommand (
    actor: Moveable,
    x: Double,
    y: Double,
): Command {

    private val actor: Moveable = actor;
    private var x: Double = x;
    private var y: Double = y;

    private var _previousX: Double = 0.0;
    private var _previousY: Double = 0.0;

    override fun exec() {

        _previousX = actor.getPositionX();
        _previousY = actor.getPositionY();

        actor.move(x, y);

    }

    override fun undo() {

        x = _previousX;
        y = _previousY;

        exec();
    }

}
