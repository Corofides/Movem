package entities

import Constants
import Enums.*
import Interfaces.*
import helpers.*

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

/**
 * Entities.player
 *
 * @param mainImage
 * @param levelGrid
 * @param gridX
 * @param gridY
 * @param callback
 */
inline fun Container.player(
    mainImage: Array<BitmapSlice<Bitmap>>,
    levelGrid: LevelGrid,
    gridX: Int,
    gridY: Int,
    callback: @ViewDslMarker Player.() -> Unit = {}
) = Player(mainImage, levelGrid, gridX, gridY).addTo(this, callback)

/**
 * Entities.Player
 *
 * @param playerSprites The image to display for the main Entities.Player
 * @param levelGrid The grid used for collision checking
 * @param gridX Initial grid column
 * @param gridY Initial grid row
 * @return Container The view that is the Entities.Player
 */
class Player(
    private val playerSprites: Array<BitmapSlice<Bitmap>>,
    private val levelGrid: LevelGrid,
    var gridX: Int,
    var gridY: Int
) : Moveable, Container() {

    // Properties
    private val image: Image = image(playerSprites[Direction.NORTH.ordinal])

    override var moving: Boolean = false
    private var movementDirection: Direction = Direction.NORTH
    private var currentMovementAmount: Int = 0
    private val allowedMovementAmount: Int = Constants.TILE_SIZE

    /**
     * init
     */
    init {
        image.anchor(.5, .5)
        image.scale(1)
        image.position(0, 0)

        // Register player in the grid so blocks cannot be pushed into the player's cell
        levelGrid.setEntityAt(gridX, gridY, this)
    }

    /**
     * Movement Update Cycle
     */
    fun movementUpdateCycle() {
        if (moving) {
            when (movementDirection) {
                Direction.NORTH -> this.y -= 1
                Direction.SOUTH -> this.y += 1
                Direction.EAST -> this.x += 1
                Direction.WEST -> this.x -= 1
            }
            currentMovementAmount++
        }

        if (currentMovementAmount >= allowedMovementAmount) {
            moving = false
            currentMovementAmount = 0
        }
    }

    override fun canMove(direction: Direction): Boolean {
        val (tx, ty) = targetGridPosition(direction)
        val entity = levelGrid.getEntityAt(tx, ty)
        return when {
            entity == null -> true
            entity is Block -> entity.canMove(direction)
            else -> false
        }
    }

    /**
     * move
     *
     * @param direction
     * @return boolean Whether the command was successful.
     */
    override fun move(direction: Direction): Boolean {
        if (moving) return false

        // Change player orientation regardless of whether we can move
        image.bitmap = playerSprites[direction.ordinal]

        val (tx, ty) = targetGridPosition(direction)
        val entity = levelGrid.getEntityAt(tx, ty)

        when {
            entity == null -> { /* target cell is empty – move freely */ }
            entity is Block -> {
                // Try to push the block first; if it cannot move, player cannot move either
                if (!entity.canMove(direction)) return false
                entity.move(direction)
            }
            else -> return false // Wall, boundary or other impassable entity
        }

        // Update grid: vacate current cell, occupy target cell
        levelGrid.setEntityAt(gridX, gridY, null)
        levelGrid.setEntityAt(tx, ty, this)
        gridX = tx
        gridY = ty

        // Start smooth movement animation
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
