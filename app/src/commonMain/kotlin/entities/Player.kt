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
    private val playerSprites: Array<BitmapSlice<Bitmap>>
) : Moveable, Container() {
    // Properties
    private val image: Image = image(playerSprites[Direction.NORTH.ordinal])
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

        // Temp
        val playerBackground = solidRect(32, 32, Colors.RED)
        playerBackground.anchor(.5, .5)
        playerBackground.scale(1)
        playerBackground.position(0, 0)
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
        }

        if (!it.canMove(movementDirection)) {
            moving = false
            preventMove = true
            return
        } else {
            // If it is dense and can move, move the object
            if (!moving) return
            it.move(movementDirection)
        }
    }

    /**
     * Change Player Orientation
     *
     * @param direction
     */
    private fun Container.changePlayerOrientation(direction: Direction) {
        image.bitmap = playerSprites[direction.ordinal]

        when(direction) {
            Direction.NORTH -> {
                detectionArea.position(0, -(Constants.TILE_SIZE / 2))
            }
            Direction.SOUTH -> {
                detectionArea.position(0, Constants.TILE_SIZE / 2)
            }
            Direction.WEST -> {
                detectionArea.position(-(Constants.TILE_SIZE / 2), 0)
            }
            Direction.EAST -> {
                detectionArea.position(Constants.TILE_SIZE / 2, 0)
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

    override fun canMove(direction: Direction): Boolean {
        return true;
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
