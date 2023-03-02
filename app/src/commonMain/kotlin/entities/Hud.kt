package entities

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*

/**
 * Container Hud
 *
 * @param mainImage
 * @param callback
 */
inline fun Container.hud(mainImage: BitmapSlice<Bitmap>, callback: @ViewDslMarker Hud.() -> Unit = {}) =
    Hud(mainImage).addTo(this, callback)

/**
 * Hud
 *
 * @param sprite
 */
class Hud (
    sprite: BitmapSlice<Bitmap>
) : Container() {
    // 643 x 44

    // Properties
    private val hudImage: Image = image(sprite);

    /**
     * init
     */
    init {
        hudImage.anchor(0, 0);
        hudImage.scale(1);
        hudImage.position(0, 0);
    }

    /**
     * paint
     *
     * @param moves
     * @param font
     */
    fun Container.paint(moves: Int, font: TtfFont) {

        val fontsize = 32.0
        removeChildren()

        this.addChild(hudImage)

        // Pushes 0000
        val pushes = NativeImage(643, 44).apply {
            getContext2d().fillText("0", x = 337.0, y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)
            getContext2d().fillText("0", x = 369.0, y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)
            getContext2d().fillText("0", x = 401.0, y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)
            getContext2d().fillText("0", x = 433.0, y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)

//            val totalMovesString = moves.toString().padStart(4, '0')
//            totalMovesString.forEachIndexed  { index, element ->
//                getContext2d().fillText("" + element, x = 337.0 + (32.0 * index), y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)
//            }
        }
        image(pushes)

        val level = NativeImage(643, 44).apply {
            getContext2d().fillText("0", x = 577.0, y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)
            getContext2d().fillText("1", x = 609.0, y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)
        }
        image(level)

        // Moves 0000
        val image = NativeImage(643, 44).apply {
            val totalMovesString = moves.toString().padStart(4, '0')
            totalMovesString.forEachIndexed  { index, element ->
                getContext2d().fillText("" + element, x = 97.0 + (32.0 * index), y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)
            }
        }
        image(image)

    }

//    val font = TtfFont(resourcesVfs["Movem.ttf"].readAll())
//    val bitmap = NativeImage(643, 44).apply {
//        getContext2d().fillText("0 0 0 0", x = 100.0, y = 100.0, font, textSize = 100.0, color = Colors.RED)
//    }

}
