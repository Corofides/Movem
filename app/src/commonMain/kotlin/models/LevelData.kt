package models

data class LevelData(
    var tiles: ArrayList<ArrayList<Int>>,
    var boxes: ArrayList<ArrayList<Int>>,
    var goals: ArrayList<String>,
    var start: ArrayList<Int>
)
