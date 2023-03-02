package entities

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

/**
 * wall
 *
 * @param mainImage
 * @param callback
 */
inline fun Container.floor(mainImage: BitmapSlice<Bitmap>, callback: @ViewDslMarker Floor.() -> Unit = {}) =
    Floor(mainImage).addTo(this, callback);

/**
 * Wall
 *
 * @param sprite
 */
class Floor (
    sprite: BitmapSlice<Bitmap>
) : Container() {

    // Properties
    private val image: Image = image(sprite);

    /**
     * init
     */
    init {
        image.anchor(.5, .5);
        image.scale(1);
        image.position(0, 0);
    }

}
