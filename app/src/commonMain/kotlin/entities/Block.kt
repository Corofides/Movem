package entities

import Constants
import Enums.*
import Interfaces.*
import singletons.*

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*

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
    private val detectionArea = solidRect(4, 4, Colors.WHITE)

    var preventMove: Boolean = false

    /**
     * init
     */
    init {
        image.anchor(.5, .5)
        image.scale(1)
        image.position(0, 0)

        // Is the thing it collides with detecting the collision instead of the detection area?
        onCollision {
            // Block A -> Detection Area A -> Block B -> Block B detects collision with Detection Area of Block A
            // Need reverse?
            // Block A -> Detection Area A (Reposition) -> Block B detects collision, fire event, to thing colliding.
            // Block A detection area registers event preventing movement.

            if (it is Player) {
                //println("Block Collided with Player");
            }

            //println("Collision");
        }

        detectionArea.anchor(.5, .5)
        detectionArea.scale(1)
        detectionArea.position(0, -(Constants.TILE_SIZE))


        detectionArea.onCollision(filter = { it != this }) {

            if (it is Player || it is Dense || it is Floor || it is Block) {
                println("detectionArea onCollision if player")
                onBlockCollision(it)
            }
        }

//        image.onDescendantCollision {
//            println("desc")
//        }
    }

    /**
     * On Block Collision
     *
     * @param it
     */
    private fun Container.onBlockCollision(it: View) {
        var shouldMove = false
        //preventMove = true
        if (it is Stage) {
            return;
        }

//        if (it is Wall) {
//            shouldMove = false
//            return
//        }

        if (it !is Dense) {

            println(it::class.simpleName)
            //println(it::class.qualifiedName)

            // If it is not dense, don't do anything. i.e. floor.
            shouldMove = true
            //return
        }

        preventMove = !shouldMove
    }

    /**
     * Detection Area Reposition
     *
     * @param direction
     */
    fun detectionAreaReposition(direction: Direction) {
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
        if (!canMove(movementDirection)) {
            moving = false
        }

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

    override fun canMove(direction: Direction): Boolean {
        detectionAreaReposition(direction)
        //onBlockCollision();
        return !preventMove
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
