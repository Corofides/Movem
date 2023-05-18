package models

data class LevelData {
    var tiles : ArrayList<ArrayList<Int>> = arrayListOf(),
    var boxes : ArrayList<ArrayList<Int>> = arrayListOf(),
    var goals : ArrayList<String>         = arrayListOf(),
    var start : ArrayList<Int>            = arrayListOf()
}
