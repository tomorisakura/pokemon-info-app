package grevi.msx.poketest.mModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class FavoritePokemon(
    var image_url : String? = null,
    var pokemon_avg_spawn : Int? = null,
    var pokemon_candy : String? = null,
    var pokemon_candy_count : Int? = null,
    var pokemon_egg : String? = null,
    var pokemon_height : String? = null,
    var pokemon_name : String? = null,
    var pokemon_num : String? = null,
    var pokemon_spawn_chance : Double? = null,
    var pokemon_spawn_time : String? = null,
    var pokemon_type : List<String>? = null,
    var pokemon_weakness : List<String>? = null,
    var pokemon_weight : String? = null
) : Parcelable {
    constructor() : this("" , 0, "", 0,
        "", "", "", "", 0.0,
    "", emptyList(), emptyList(), "")
}

//var image_url : String = ""
//var pokemon_avg_spawn : Int = 0
//var pokemon_candy : String = ""
//var pokemon_candy_count : Int = 0
//var pokemon_egg : String? = null
//var pokemon_height : String? = null
//var pokemon_name : String = ""
//var pokemon_num : String = ""
//var pokemon_spawn_chance : Double = 0.0
//var pokemon_spawn_time : String? = null
//var pokemon_type : List<String>? = null
//var pokemon_weakness : List<String>? = null
//var pokemon_weight : String? = null

