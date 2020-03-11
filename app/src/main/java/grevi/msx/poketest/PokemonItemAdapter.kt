package grevi.msx.poketest

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import grevi.msx.poketest.Model.Pokemon
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
                Glide.with(itemView.context).load(url).placeholder(R.drawable.ic_launcher_foreground).dontAnimate().into(item_image)
                level_pokemon.text = pokemon.candy
                for (i in 0 until pokemon.type!!.size) {
                    Log.d("type", pokemon.type.get(i))
                    tv_type.text =  "Type : ${pokemon.type.get(0).toString()}" 
                }

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(pokemon) }

                btn_favorite.setOnClickListener {
                    if (btn_favorite.isPressed) {
                        Toast.makeText(mContext, "Favorite a ${pokemon.name} " , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}