package grevi.msx.poketest.mModel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Pokemon(
    @SerializedName("id") val id : Int? = null,
    @SerializedName("num") val num : String? = null,
    @SerializedName("name") val name : String? = null,
    @SerializedName("img") val img : String? = null,
    @SerializedName("type") val type : ArrayList<String>,
    @SerializedName("candy") val candy : String? = null,
    @SerializedName("candy_count") val candy_count : Double? = null,
    @SerializedName("height") val height : String? = null,
    @SerializedName("weight") val weight : String? = null,
    @SerializedName("egg") val egg : String? = null,
    @SerializedName("weaknesses") val weak : List<String>,
    @SerializedName("spawn_chance") val spawn_chance : Double? = null,
    @SerializedName("avg_spawns") val avg_spawns : Double? = null,
    @SerializedName("spawn_time") val spawn_time : String? = null
) : Parcelable