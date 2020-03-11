package grevi.msx.poketest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import grevi.msx.poketest.Model.Pokemon
import grevi.msx.poketest.Rest.ApiService
import grevi.msx.poketest.Rest.Repository
import grevi.msx.poketest.Static.Common
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var pokemonAdapter : PokemonItemAdapter
    private var apiService : ApiService

    init {
        val retrofit = Repository.mRetrofit()
        apiService = retrofit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchingData()
    }

    private fun fetchingData() {
        apiService.getAllPokemon().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe{
                result-> Common.pokemonList = result.pokemon!!
                createRecyclerView(Common.pokemonList)
            }

    }

    private fun createRecyclerView(pokemon: List<Pokemon>) {
        rv_pokemon_item.layoutManager = GridLayoutManager(this, 2)
        pokemonAdapter = PokemonItemAdapter(this, pokemon)
        pokemonAdapter.notifyDataSetChanged()
        rv_pokemon_item.adapter = pokemonAdapter

        pokemonAdapter.setOnItemClickCallback(object : PokemonItemAdapter.OnItemClickCallback {
            override fun onItemClicked(mPokemon: Pokemon) {
                setupDetail(mPokemon)
            }
        })
    }

    private fun setupDetail(pokemon: Pokemon) {
        val mIntent = Intent(this, PokemonActivity::class.java)
        mIntent.putExtra(PokemonActivity.POKEMON_OBJECT, pokemon)
        startActivity(mIntent)
    }
}
