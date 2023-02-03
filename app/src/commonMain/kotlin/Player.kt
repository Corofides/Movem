import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*

inline fun Container.player(mainImage: BitmapSlice<Bitmap>, callback: @ViewDslMarker Player.() -> Unit = {}) =
    Player(mainImage).addTo(this, callback)

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
