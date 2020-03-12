package grevi.msx.poketest.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Pokemon(
    @SerializedName("id") val id : Int? = null,
    @SerializedName("num") val num : String? = null,
    @SerializedName("name") val name : String? = null,
    @SerializedName("img") val img : String? = null,
    @SerializedName("type") val type : ArrayList<String>? = null,
    @SerializedName("candy") val candy : String? = null,
    @SerializedName("candy_count") val candy_count : Double? = null,
    @SerializedName("height") val height : String? = null,
    @SerializedName("weight") val weight : String? = null,
    @SerializedName("egg") val egg : String? = null
) : Parcelable