package entities

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
) :  Dense, Moveable, Container() {

    // Properties
    override var moving: Boolean = false
    private val image: Image = image(blockSprite)
    private var currentMovementAmount: Int = 0
    private val allowedMovementAmount: Int = 1
    private var movementDirection: Direction = Direction.NORTH

    /**
     * init
     */
    init {
        image.anchor(.5, .5);
        image.scale(1);
        image.position(0, 0);
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
     */
    override fun move(direction: Direction) {
        movementDirection = direction
        //cur
        println("Player moved object")

        if (moving) {
            return
        }

        moving = true
        movementDirection = direction
        currentMovementAmount = 0;

    }
}
