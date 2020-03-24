package grevi.msx.poketest.mModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


class FavoritePokemon{
    var image_url : String = ""
    var pokemon_candy : String = ""
    var pokemon_name : String = ""
    var pokemon_num : String = ""
    var pokemon_type : List<String>? = null
}

