package Enums

enum class TileType( val letter: String) {
    PLASMACONTAINER("b"),
    ANCHOR("h"),
    FLOORTILE(""),
    DROID("p"), // AKA Dave.
    WALL("w"),
    EMPTY(" ");

    companion object {
        infix fun from(value: String): TileType? = TileType.values().firstOrNull { it.letter == value }
    }
//    // val plasmaContainer = TileType from "b"
}
