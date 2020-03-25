package grevi.msx.poketest.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import grevi.msx.poketest.R
import grevi.msx.poketest.mModel.FavoritePokemon
import kotlinx.android.synthetic.main.favorite_list.view.*

class FavoritePokemonAdapter(val mContext: Context, val favPokemon : List<FavoritePokemon>) : RecyclerView.Adapter<FavoritePokemonAdapter.FavoriteViewHolder>() {

    private var itemClickCallBack : OnItemClickCallBack? = null

    interface OnItemClickCallBack {
        fun onItemClicked(mFav : FavoritePokemon)
    }

    fun setOnItemClickCallBack(onItemCLik : OnItemClickCallBack) {
        this.itemClickCallBack = onItemCLik
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.favorite_list, parent, false)
        return FavoriteViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return favPokemon.size
    }

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int
    ) {
        holder.bind(favPokemon[position])
    }

    inner class FavoriteViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(f : FavoritePokemon) {
            with(itemView) {
                Glide.with(itemView.context).load(f.image_url).override(120, 120).into(item_image_fav)
                name_pokemon_fav.text = f.pokemon_name
                tv_type_fav.text = f.pokemon_type.toString().replace("[","").replace("]","")
                itemView.setOnClickListener { itemClickCallBack?.onItemClicked(f) }
            }
        }
    }

}