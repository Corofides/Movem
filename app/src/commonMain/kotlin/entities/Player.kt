package entities

import Commands.*
import Constants
import Enums.*
import Interfaces.*

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*

/**
 * Entities.player
 *
 * @param mainImage
 * @param callback
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
    // Properties
    private val image: Image = image(playerSprites[Direction.NORTH.ordinal])
    private val playerSprites = playerSprites
    private val detectionArea = solidRect(4, 4, Colors.WHITE)

    // Replace with ID later.
    private var objectInFront: Moveable? = null

    override var moving: Boolean = false
    var preventMove: Boolean = false
    var objectCanMove = false
    private var movementDirection: Direction = Direction.NORTH
    private var currentMovementAmount: Int = 0
    private val allowedMovementAmount: Int = Constants.TILE_SIZE //+ Constants.TILE_BUFFER

    /**
     * init
     */
    init {
        image.anchor(.5, .5)
        image.scale(1)
        image.position(0, 0)

        detectionArea.anchor(.5, .5)
        detectionArea.scale(1)
        detectionArea.position(0, -(Constants.TILE_SIZE / 2))

        detectionArea.onCollision {
            onPlayerCollision(it)
        }
    }

    /**
     * On Player Collision
     *
     * @param it
     */
    private fun Container.onPlayerCollision(it: View) {
        preventMove = false

        if (it !is Dense) {
            // If it is not dense, don't do anything. i.e. floor.
            return
        }
        if (it !is Moveable) {
            // If it is dense and cannot be moved, prevent the player from moving i.e. wall
            moving = false
            preventMove = true
            return
        } else {
            // If it is dense and can move, move the object
            //objectInFront = it
            it.move(movementDirection)
        }
        //objectCanMove = true;
    }

    /**
     * Change Player Orientation
     *
     * @param direction
     */
    private fun Container.changePlayerOrientation(direction: Direction) {
        image.bitmap = playerSprites[direction.ordinal] //bitmap

        // detectionArea.anchor(.5, .5)
        // detectionArea.scale(1)
        // detectionArea.position(0, 0)
//        detectionArea.position(0, -(Constants.TILE_SIZE / 2))

        when(direction) {
            Direction.NORTH -> {
                detectionArea.position(0, -(Constants.TILE_SIZE))
            }
            Direction.SOUTH -> {
                detectionArea.position(0, Constants.TILE_SIZE)
            }
            Direction.WEST -> {
                detectionArea.position(-(Constants.TILE_SIZE), 0)
            }
            Direction.EAST -> {
                detectionArea.position(Constants.TILE_SIZE, 0)
            }
        }
    }

    /**
     * Movement Update Cycle
     */
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
            currentMovementAmount++
        }

        if (currentMovementAmount == allowedMovementAmount) {
            moving = false
            objectInFront = null
            objectCanMove = false
            preventMove = false
        }
    }

    /**
     * move
     *
     * @param direction
     * @return boolean Whether the command was successfully.
     */
    override fun move(direction: Direction): Boolean {
        if (objectInFront !== null) {
            val command = MoveCommand(objectInFront as Moveable, direction)
            command.exec()
        }

        if (moving) {
            return false
        }

        changePlayerOrientation(direction)

        if (preventMove) {
            return false
        }

        moving = true
        movementDirection = direction
        currentMovementAmount = 0
        return true
    }

}
