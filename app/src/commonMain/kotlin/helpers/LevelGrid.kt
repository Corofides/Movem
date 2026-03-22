package helpers

import com.soywiz.korma.geom.*

class LevelGrid(cellWidth: Int, cellHeight: Int, val rowCount: Int = 20, val colCount: Int = 20) {

    private val cellWidth = cellWidth
    private val cellHeight = cellHeight

    val totalCells = colCount * rowCount

    private val boardWidth = cellWidth * colCount
    private val boardHeight = cellHeight * rowCount

    // Grid that stores entity references at each cell (null = empty)
    private val entityGrid = arrayOfNulls<Any>(totalCells)

    // Sentinel returned when querying out-of-bounds cells (treated as impassable)
    private val boundary = object {}

    /**
     * Store an entity at the given grid cell.
     * Pass null to clear the cell.
     */
    fun setEntityAt(x: Int, y: Int, entity: Any?) {
        if (x < 0 || x >= colCount || y < 0 || y >= rowCount) return
        entityGrid[getIndexForCell(x, y)] = entity
    }

    /**
     * Return the entity at the given grid cell, or null if the cell is empty.
     * Returns a non-null boundary sentinel for out-of-bounds positions.
     */
    fun getEntityAt(x: Int, y: Int): Any? {
        if (x < 0 || x >= colCount || y < 0 || y >= rowCount) return boundary
        val index = getIndexForCell(x, y)
        return entityGrid[index]
    }

    // x, y are acceptable because maths.
    // Add ability to get X, Y in opposite direction
    fun getCellPosition(x: Int, y: Int, reverseX: Boolean = false, reverseY: Boolean = false): Point {
        // Here be dragons, also globals
        var positionX = x * cellWidth
        var positionY = y * cellHeight

        if (reverseX) {
            // Get the boardWidth minus the position + the cell height to ensure 0
            positionX = boardWidth - (positionX + cellWidth)
        }

        if (reverseY) {
            // Get the boardHeight minus the position + the cell height to ensure 0
            positionY = boardHeight - (positionY + cellHeight)
        }

        return Point(positionX, positionY)
    }

    // Set of holder positions for block-occupancy detection
    private val holderPositions = mutableSetOf<Pair<Int, Int>>()

    /**
     * Register a holder at the given grid cell so that blocks can detect when
     * they are resting on it.
     */
    fun registerHolder(x: Int, y: Int) {
        holderPositions.add(Pair(x, y))
    }

    /**
     * Returns true if a Holder is registered at (x, y).
     */
    fun isHolderAt(x: Int, y: Int): Boolean = Pair(x, y) in holderPositions

    // We can use this to store an item against an index.
    fun getIndexForCell(x: Int, y: Int): Int {
        return x + (y * colCount)
    }
}
