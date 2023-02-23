package entities

import Commands.*
import Constants;
import Enums.*
import Interfaces.*

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

/**
 * Entities.player
 */
inline fun Container.player(mainImage: Array<BitmapSlice<Bitmap>>, callback: @ViewDslMarker Player.() -> Unit = {}) =
    Player(mainImage).addTo(this, callback)

/**
 * Entities.Player
 *
 * @param playerSprites The image to display for the main Entities.Player
 * @return Container The view that is the Entities.Player
 */
class Player (
    playerSprites: Array<BitmapSlice<Bitmap>>
) : Moveable, Container() {
    private val image: Image = image(playerSprites[Direction.NORTH.ordinal])
    private val playerSprites = playerSprites
    private val detectionArea = solidRect(Constants.TILE_SIZE, Constants.TILE_SIZE)

    // Replace with ID later.
    private var objectInFront: Moveable? = null

    override var moving: Boolean = false
    var preventMove: Boolean = false
    var objectCanMove = false
    private var movementDirection: Direction = Direction.NORTH
    private var currentMovementAmount: Int = 0
    private val allowedMovementAmount: Int = Constants.TILE_SIZE + Constants.TILE_BUFFER

    init {
        image.anchor(.5, .5)
        image.scale(1)
        image.position(0, 0)

        detectionArea.anchor(.5, .5)
        detectionArea.scale(1)
        detectionArea.position(0, -33)

        detectionArea.onCollision {

            onPlayerCollison(it);

        }

    }

    private fun Container.onPlayerCollison(it: View) {
        if (it !is Dense) {
            return
        }

        if (it !is Moveable) {
            preventMove = true
            return
        } else {
            //objectInFront = it
            it.move(movementDirection)
        }


        //objectCanMove = true;
    }

    private fun Container.changePlayerOrientation(direction: Direction) {
        image.bitmap = playerSprites[direction.ordinal]//bitmap

        when(direction) {
            Direction.NORTH -> {
                detectionArea.position(0, -(Constants.TILE_SIZE + Constants.TILE_BUFFER))
            }
            Direction.SOUTH -> {
                detectionArea.position(0, Constants.TILE_SIZE + Constants.TILE_BUFFER)
            }
            Direction.WEST -> {
                detectionArea.position(-(Constants.TILE_SIZE + Constants.TILE_BUFFER), 0)
            }
            Direction.EAST -> {
                detectionArea.position(Constants.TILE_SIZE + Constants.TILE_BUFFER, 0)
            }
        }

    }

    fun movementUpdateCycle() {

        if (moving) {
            when (movementDirection) {
                Direction.NORTH -> {
                    this.y -= 1
                }
                Direction.SOUTH -> {
                    this.y += 1
                }
                Direction.EAST -> {
                    this.x += 1
                }
                Direction.WEST -> {
                    this.x -= 1
                }
            }
            currentMovementAmount++;
        }

        if (currentMovementAmount == allowedMovementAmount) {
            moving = false;

            objectInFront = null
            objectCanMove = false
            preventMove = false;

        }

    }

    override fun move(direction: Direction) {

        if (moving) {
            return;
        }

        changePlayerOrientation(direction);

        if (preventMove) {
            return;
        }

        if (objectInFront !== null) {
            MoveCommand(objectInFront as Moveable, direction).exec();
        }


        moving = true;
        movementDirection = direction;
        currentMovementAmount = 0;

    }

}
