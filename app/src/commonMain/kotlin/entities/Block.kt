package entities

import Constants
import Enums.*
import Interfaces.*

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import singletons.*

/**
 * block
 *
 * @param mainImage
 * @param occupiedImage
 * @param callback
 */
inline fun Container.block(mainImage: BitmapSlice<Bitmap>, occupiedImage: BitmapSlice<Bitmap>, callback: @ViewDslMarker Block.() -> Unit = {}) =
    Block(mainImage, occupiedImage).addTo(this, callback)

/**
 * Block
 *
 * @param blockSprite
 * @param occupiedSprite
 */
class Block (
    blockSprite: BitmapSlice<Bitmap>,
    occupiedSprite: BitmapSlice<Bitmap>
) : Dense, Moveable, Container() {

    // Properties
    override var moving: Boolean = false
    private val image: Image = image(blockSprite)
    // private val occupiedImage: Image = image(occupiedSprite)
    private var currentMovementAmount: Int = 0
    private val allowedMovementAmount: Int = Constants.TILE_SIZE
    private var movementDirection: Direction = Direction.NORTH

    /**
     * init
     */
    init {
        image.anchor(.5, .5)
        image.scale(1)
        image.position(0, 0)
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
        }
    }

    /**
     * move
     *
     * @param direction
     * @return Boolean
     */
    override fun move(direction: Direction): Boolean {
        movementDirection = direction

        if (moving) {
            return false
        }

        EventManager.sendUpdateEvent(Event.BLOCKMOVED)
        moving = true
        movementDirection = direction
        currentMovementAmount = 0
        return true
    }
}
