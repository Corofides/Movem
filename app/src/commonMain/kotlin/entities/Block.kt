package entities

import Constants
import Enums.*
import Interfaces.*
import helpers.*
import singletons.*

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

/**
 * block
 *
 * @param mainImage
 * @param occupiedImage
 * @param levelGrid
 * @param gridX
 * @param gridY
 * @param callback
 */
inline fun Container.block(
    mainImage: BitmapSlice<Bitmap>,
    occupiedImage: BitmapSlice<Bitmap>,
    levelGrid: LevelGrid,
    gridX: Int,
    gridY: Int,
    callback: @ViewDslMarker Block.() -> Unit = {}
) = Block(mainImage, occupiedImage, levelGrid, gridX, gridY).addTo(this, callback)

/**
 * Block
 *
 * @param blockSprite
 * @param occupiedSprite
 * @param levelGrid The grid used for collision checking
 * @param gridX Initial grid column
 * @param gridY Initial grid row
 */
class Block(
    blockSprite: BitmapSlice<Bitmap>,
    occupiedSprite: BitmapSlice<Bitmap>,
    private val levelGrid: LevelGrid,
    var gridX: Int,
    var gridY: Int
) : Dense, Moveable, Container() {

    // Properties
    override var moving: Boolean = false
    private val image: Image = image(blockSprite)
    private var currentMovementAmount: Int = 0
    private val allowedMovementAmount: Int = Constants.TILE_SIZE
    private var movementDirection: Direction = Direction.NORTH
    private var isOnHolder: Boolean = false

    /**
     * init
     */
    init {
        image.anchor(.5, .5)
        image.scale(1)
        image.position(0, 0)

        // Register block in the grid so the player and other blocks cannot enter this cell
        levelGrid.setEntityAt(gridX, gridY, this)

        // Check if block starts on a holder (e.g. a pre-solved level)
        if (levelGrid.isHolderAt(gridX, gridY)) {
            isOnHolder = true
            EventManager.sendUpdateEvent(Event.HOLDEROCCUPIED)
        }
    }

    /**
     * Movement Update Cycle
     */
    fun movementUpdateCycle() {
        if (moving) {
            when (movementDirection) {
                Direction.NORTH -> this.y -= 1
                Direction.SOUTH -> this.y += 1
                Direction.EAST  -> this.x += 1
                Direction.WEST  -> this.x -= 1
            }
            currentMovementAmount++
        }

        if (currentMovementAmount >= allowedMovementAmount) {
            moving = false
            currentMovementAmount = 0
            // Check whether the block just landed on a holder
            if (!isOnHolder && levelGrid.isHolderAt(gridX, gridY)) {
                isOnHolder = true
                EventManager.sendUpdateEvent(Event.HOLDEROCCUPIED)
            }
        }
    }

    override fun canMove(direction: Direction): Boolean {
        if (moving) return false
        val (tx, ty) = targetGridPosition(direction)
        return levelGrid.getEntityAt(tx, ty) == null
    }

    /**
     * move
     *
     * @param direction
     * @return Boolean
     */
    override fun move(direction: Direction): Boolean {
        if (moving) return false
        if (!canMove(direction)) return false

        val (tx, ty) = targetGridPosition(direction)

        // If moving off a holder, fire unoccupied event
        if (isOnHolder) {
            isOnHolder = false
            EventManager.sendUpdateEvent(Event.HOLDERUNOCCUPIED)
        }

        // Update grid: vacate current cell, occupy target cell
        levelGrid.setEntityAt(gridX, gridY, null)
        levelGrid.setEntityAt(tx, ty, this)
        gridX = tx
        gridY = ty

        EventManager.sendUpdateEvent(Event.BLOCKMOVED)
        moving = true
        movementDirection = direction
        currentMovementAmount = 0
        return true
    }

    /**
     * Returns the grid position one step ahead in the given direction.
     */
    private fun targetGridPosition(direction: Direction): Pair<Int, Int> = when (direction) {
        Direction.NORTH -> Pair(gridX, gridY - 1)
        Direction.SOUTH -> Pair(gridX, gridY + 1)
        Direction.EAST  -> Pair(gridX + 1, gridY)
        Direction.WEST  -> Pair(gridX - 1, gridY)
    }
}
