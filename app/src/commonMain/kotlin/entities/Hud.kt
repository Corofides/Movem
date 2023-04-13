package entities

import Constants
import Interfaces.Observer

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*

/**
 * Container Hud
 *
 * @param mainImage
 * @param level
 * @param callback
 */
inline fun Container.hud(mainImage: BitmapSlice<Bitmap>, level: Int, callback: @ViewDslMarker Hud.() -> Unit = {}) =
    Hud(mainImage, level).addTo(this, callback)

/**
 * Hud
 *
 * @param sprite
 * @param level
 */
class Hud (
    sprite: BitmapSlice<Bitmap>,
    level: Int
) : Observer, Container() {
    // 643 x 44

    // Properties
    private val hudImage: Image = image(sprite)
    private val level: Int = level
    private var pushes: Int = 0

    /**
     * init
     */
    init {
        hudImage.anchor(0, 0)
        hudImage.scale(1)
        hudImage.position(0, 0)
    }

    /**
     * paint
     *
     * @param moves
     * @param font
     */
    fun Container.paint(moves: Int, font: TtfFont) {
        removeChildren()

        this.addChild(hudImage)

        // Level 00
        val levelImage = NativeImage(643, 44).apply {
            val levelString = level.toString().padStart(2, '0')
            levelString.forEachIndexed  { index, element ->
                getContext2d().fillText("" + element, x = 577.0 + (32.0 * index), y = 28.0, font, textSize = Constants.FONT_SIZE, color = Colors.PURPLE)
            }
        }
        image(levelImage)

        // Pushes 0000
        val pushesImage = NativeImage(643, 44).apply {
            val totalPushesString = pushes.toString().padStart(4, '0')
            totalPushesString.forEachIndexed  { index, element ->
                getContext2d().fillText("" + element, x = 337.0 + (32.0 * index), y = 28.0, font, textSize = Constants.FONT_SIZE, color = Colors.PURPLE)
            }
        }
        image(pushesImage)

        // Moves 0000
        val movesImage = NativeImage(643, 44).apply {
            val totalMovesString = moves.toString().padStart(4, '0')
            totalMovesString.forEachIndexed  { index, element ->
                getContext2d().fillText("" + element, x = 97.0 + (32.0 * index), y = 28.0, font, textSize = Constants.FONT_SIZE, color = Colors.PURPLE)
            }
        }
        image(movesImage)
    }

    //region Observer

    /**
     * On Notify
     * @param event
     */
    override fun onNotify(event: String) {
        pushes += 1
    }

    //endregion
}
