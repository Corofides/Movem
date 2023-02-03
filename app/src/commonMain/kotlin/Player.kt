import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

/**
 * player
 */
inline fun Container.player(mainImage: BitmapSlice<Bitmap>, callback: @ViewDslMarker Player.() -> Unit = {}) =
    Player(mainImage).addTo(this, callback)

/**
 * Player
 *
 * @param mainImage The image to display for the main Player
 * @return Container The view that is the Player
 */
class Player(
    mainImage: BitmapSlice<Bitmap>
): Container() {
    private val image: Image = image(mainImage);

    init {
        image.anchor(.5, .5);
        image.scale(0.8);
        image.position(0, 0);
    }

    fun Container.changeCell(bitmap: BmpSlice) {
        image.bitmap = bitmap
    }
}
