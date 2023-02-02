import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*

class Player(
    mainImage: BitmapSlice<Bitmap>
): Container() {

    private val image: Image = image(mainImage);

    init {

        //val manUpBitmap = resourcesVfs["Pieces/Man/Man [Up] [T].png"].readBitmapSlice() //.readBitmap()

        /* position(256, 256) */

        image.anchor(.5, .5);
        image.scale(0.8);
        image.position(0, 0);

        /*image(mainImage) {
            //rotation = maxDegrees
            anchor(.5, .5)
            scale(0.8)
            position(0, 0)
        }*/

    }

    fun Container.changeCell(bitmap: BmpSlice) {
        image.bitmap = bitmap
    }

}
