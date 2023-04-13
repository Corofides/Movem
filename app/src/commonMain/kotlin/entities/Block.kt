package entities

import Constants
import Enums.*
import Interfaces.*

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

/**
 * block
 *
 * @param mainImage
 * @param callback
 */
inline fun Container.block(mainImage: BitmapSlice<Bitmap>, callback: @ViewDslMarker Block.() -> Unit = {}) =
    Block(mainImage).addTo(this, callback)

/**
 * Block
 *
 * @param blockSprite
 */
class Block (
    blockSprite: BitmapSlice<Bitmap>
) :  Observable, Dense, Moveable, Container() {

    // Properties
    override var moving: Boolean = false
    private val image: Image = image(blockSprite)
    private var currentMovementAmount: Int = 0
    private val allowedMovementAmount: Int = Constants.TILE_SIZE
    private var movementDirection: Direction = Direction.NORTH
    override val observers: ArrayList<Observer> = ArrayList()

    /**
     * init
     */
    init {
        image.anchor(.5, .5)
        image.scale(1)
        image.position(0, 0)
    }

    /**
     * Add Block Move Observer
     * @param observer
     */
    fun addBlockMoveObserver(observer: Observer) {
        observers.add(observer)
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
        println("Player moved object")
        movementDirection = direction

        if (moving) {
            return false
        }

        sendUpdateEvent("blockMoved")
        moving = true
        movementDirection = direction
        currentMovementAmount = 0
        return true
    }
}
