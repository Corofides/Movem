package Entities

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

inline fun Container.block(mainImage: BitmapSlice<Bitmap>, callback: @ViewDslMarker Block.() -> Unit = {}) =
    Block(mainImage).addTo(this, callback)

class Block (
    blockSprite: BitmapSlice<Bitmap>
) : Container() {

    private val image: Image = image(blockSprite)

    init {
        image.anchor(.5, .5);
        image.scale(1);
        image.position(0, 0);
    }

}
