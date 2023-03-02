package entities

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

inline fun Container.holder(mainImage: BitmapSlice<Bitmap>, callback: @ViewDslMarker Holder.() -> Unit = {}) =
    Holder(mainImage).addTo(this, callback);

/**
 * Holder
 *
 * @param sprite
 */
class Holder (
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
