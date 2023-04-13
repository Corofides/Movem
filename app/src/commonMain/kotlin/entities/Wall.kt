package entities

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

import Interfaces.*

/**
 * wall
 *
 * @param mainImage
 * @param callback
 */
inline fun Container.wall(mainImage: BitmapSlice<Bitmap>, callback: @ViewDslMarker Wall.() -> Unit = {}) =
    Wall(mainImage).addTo(this, callback)

/**
 * Wall
 *
 * @param sprite
 */
class Wall (
    sprite: BitmapSlice<Bitmap>
) : Dense, Container() {

    // Properties
    private val image: Image = image(sprite)

    /**
     * init
     */
    init {
        image.anchor(.5, .5)
        image.scale(1)
        image.position(0, 0)
    }

}
