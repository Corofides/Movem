package helpers

import com.soywiz.korma.geom.*
import com.soywiz.korui.layout.*

class LevelGrid(cellWidth: Int, cellHeight: Int, val rowCount: Int = 20, val colCount: Int = 20) {

    private val objectsInLevel = mutableListOf<Any>()

    private val cellWidth = cellWidth //Constants.TILE_SIZE // 32
    private val cellHeight = cellHeight //Constants.TILE_SIZE // 32

    val totalCells = colCount * rowCount

    private val boardWidth = cellWidth * colCount
    private val boardHeight = cellHeight * rowCount

    fun addObjectToLevelGrid(position: Point, item: Any) {
        val cellIndex = getIndexForCell(position.x.toInt(), position.y.toInt())
        println("CellIndex: ${cellIndex}")

        // Need one of those weird class things
        objectsInLevel.add(item)
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

    // We can use this to store an item against an index.
    fun getIndexForCell(x: Int, y: Int): Int {
        return x + (y * colCount);
    }
}
