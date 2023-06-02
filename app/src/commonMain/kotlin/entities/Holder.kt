package entities

import Enums.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import singletons.*

inline fun Container.holder(mainImage: BitmapSlice<Bitmap>, callback: @ViewDslMarker Holder.() -> Unit = {}) =
    Holder(mainImage).addTo(this, callback)

/**
 * Holder
 *
 * @param sprite
 */
class Holder(
    sprite: BitmapSlice<Bitmap>,
) : Container() {

    // Properties
    private val image: Image = image(sprite)
    private var isOccupied: Boolean = false

    /**
     * init
     */
    init {
        image.anchor(.5, .5)
        image.scale(1)
        image.position(0, 0)

        image.onCollision {
            onHolderCollision(it)
        }

        image.onCollisionExit {
            onHolderCollisionExit(it)
        }
    }

    /**
     * On Holder
     */
    private fun onHolderCollisionExit(it: View) {
        if (it !is Block) {
            return
        }
        isOccupied = false
        EventManager.sendUpdateEvent(Event.HOLDERUNOCCUPIED)
    }

    /**
     * On Holder Collision
     *
     * @param it
     */
    private fun onHolderCollision(it: View) {
        // Is a Block on top of a Holder?
        if (it !is Block) {
            return
        }

        if (isOccupied) {
            return
        }

        isOccupied = true
        EventManager.sendUpdateEvent(Event.HOLDEROCCUPIED)
    }
}
