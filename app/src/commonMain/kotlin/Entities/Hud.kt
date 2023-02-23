package entities

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*

/**
 * Container Hud
 */
inline fun Container.hud(mainImage: BitmapSlice<Bitmap>, callback: @ViewDslMarker Hud.() -> Unit = {}) =
    Hud(mainImage).addTo(this, callback);

/**
 * Hud
 */
class Hud (
    sprite: BitmapSlice<Bitmap>
) : Container() {
    // 643 x 44
    private val hudImage: Image = image(sprite);

    init {
        hudImage.anchor(0, 0);
        hudImage.scale(1);
        hudImage.position(0, 0);
    }

    public fun Container.paint(moves: Int, font: TtfFont) {

        val fontsize = 32.0
        removeChildren()

        this.addChild(hudImage)
        // Moves 0000
        val image = NativeImage(643, 44).apply {
            val totalMovesString = moves.toString().padStart(4, '0')
            totalMovesString.forEachIndexed  { index, element ->
                getContext2d().fillText("" + element, x = 97.0 + (32.0 * index), y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)
            }
        }
        image(image)

    }

    // Moves 0000
    // Pushes 0000
    // Level 00

//    val font = TtfFont(resourcesVfs["Movem.ttf"].readAll())
//    val bitmap = NativeImage(643, 44).apply {
//        getContext2d().fillText("0 0 0 0", x = 100.0, y = 100.0, font, textSize = 100.0, color = Colors.RED)
//    }

}
