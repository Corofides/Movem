package Entities

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
 * @param mainImage The image to display for the main Entities.Player
 * @return Container The view that is the Entities.Player
 */
class Player (
    playerSprites: Array<BitmapSlice<Bitmap>>
) : Moveable, Container() {
    private val image: Image = image(playerSprites[Direction.NORTH.ordinal]);
    private val playerSprites = playerSprites;
    private val detectionArea = solidRect(32, 32);

    override var moving: Boolean = false;
    private var movementDirection: Direction = Direction.NORTH;
    private var currentMovementAmount: Int = 0;
    private val allowedMovementAmount: Int = 32;

    init {
        image.anchor(.5, .5);
        image.scale(1);
        image.position(0, 0);

        detectionArea.anchor(.5, .5);
        detectionArea.scale(1);
        detectionArea.position(0, -32);

        detectionArea.onCollision {
            //println("This is a test");
        }

    }

    private fun Container.changePlayerOrientation(direction: Direction) {
        image.bitmap = playerSprites[direction.ordinal]//bitmap

        when(direction) {
            Direction.NORTH -> {
                detectionArea.position(0, -32);
            }
            Direction.SOUTH -> {
                detectionArea.position(0, 32);
            }
            Direction.WEST -> {
                detectionArea.position(-32, 0);
            }
            Direction.EAST -> {
                detectionArea.position(32, 0);
            }
        }

    }

    fun movementUpdateCycle() {
        if (moving) {
            when (movementDirection) {
                Direction.NORTH -> {
                    this.y -= 1;
                }
                Direction.SOUTH -> {
                    this.y += 1;
                }
                Direction.EAST -> {
                    this.x += 1;
                }
                Direction.WEST -> {
                    this.x -= 1;
                }
            }
        }

        currentMovementAmount++;

        if (currentMovementAmount == allowedMovementAmount) {
            moving = false;
        }

    }

    override fun move(direction: Direction) {

        if (!moving) {
            changePlayerOrientation(direction);
            moving = true;
            movementDirection = direction;
            currentMovementAmount = 0;
        }

    }

}
