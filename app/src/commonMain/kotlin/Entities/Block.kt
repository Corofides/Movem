package entities

import Enums.*
import Interfaces.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

inline fun Container.block(mainImage: BitmapSlice<Bitmap>, callback: @ViewDslMarker Block.() -> Unit = {}) =
    Block(mainImage).addTo(this, callback)

class Block (
    blockSprite: BitmapSlice<Bitmap>
) :  Dense, Moveable, Container() {

    override var moving: Boolean = false
    private val image: Image = image(blockSprite)
    private var movementDirection: Direction = Direction.NORTH
    //private val

    init {
        image.anchor(.5, .5);
        image.scale(1);
        image.position(0, 0);
    }

    override fun move(direction: Direction) {

        movementDirection = direction;
        //cur

        println("Player moved object");
    }

}
