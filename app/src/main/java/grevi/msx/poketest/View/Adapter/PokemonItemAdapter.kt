package grevi.msx.poketest.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import grevi.msx.poketest.R
import grevi.msx.poketest.mModel.Pokemon
import kotlinx.android.synthetic.main.pokemon_item.view.*

class PokemonItemAdapter(private val mContext : Context, private val  pokemonObj : List<Pokemon>) : RecyclerView.Adapter<PokemonItemAdapter.PokemonViewHolder>() {

    private var onItemClickCallback : OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(mPokemon : Pokemon)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        return PokemonViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return pokemonObj.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonObj[position])
    }

    inner class PokemonViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(pokemon : Pokemon) {
            with(itemView) {
                val num = pokemon.num
                val url = "http://www.serebii.net/pokemongo/pokemon/$num.png"

                name_pokemon.text = pokemon.name
                Glide.with(itemView.context).load(url).placeholder(R.drawable.ic_egg).override(120 , 120).dontAnimate().into(item_image)
                tv_type.text = pokemon.type.toString().replace("[", "").replace("]", "")
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(pokemon) }
            }
        }
    }
}