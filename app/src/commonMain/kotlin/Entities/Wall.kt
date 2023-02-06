package Entities

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

inline fun Container.wall(mainImage: BitmapSlice<Bitmap>, callback: @ViewDslMarker Wall.() -> Unit = {}) =
    Wall(mainImage).addTo(this, callback);

class Wall (
    sprite: BitmapSlice<Bitmap>
) : Container() {

    private val image: Image = image(sprite);

    init {
        image.anchor(.5, .5);
        image.scale(1);
        image.position(0, 0);
    }

}
