import Enums.*
import Interfaces.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

/**
 * player
 */
inline fun Container.player(mainImage: Array<BitmapSlice<Bitmap>>, callback: @ViewDslMarker Player.() -> Unit = {}) =
    Player(mainImage).addTo(this, callback)

/**
 * Player
 *
 * @param mainImage The image to display for the main Player
 * @return Container The view that is the Player
 */
class Player (
    playerSprites: Array<BitmapSlice<Bitmap>>
) : Moveable, Container() {
    private val image: Image = image(playerSprites[Direction.NORTH.ordinal]);
    private val playerSprites = playerSprites;

    init {
        image.anchor(.5, .5);
        image.scale(0.8);
        image.position(0, 0);
    }

    private fun Container.changeCell(direction: Direction) {
        image.bitmap = playerSprites[direction.ordinal]//bitmap
    }

    override fun move(direction: Direction) {

        changeCell(direction);

        when(direction) {
            Direction.NORTH -> {
                this.y -= 1;
            }
            Direction.SOUTH -> {
                this.y += 1;
            }
            Direction.EAST -> {
                this.x += 1;
            }
            Direction.WEST -> {
                this.x -= 1;
            }
        }

    }

    override fun move(x: Double, y: Double) {
        this.x += x;
        this.y += y;
    }

    override fun getPositionX(): Double {
        return this.x;
    }

    override fun getPositionY(): Double {
        return this.y;
    }

}
