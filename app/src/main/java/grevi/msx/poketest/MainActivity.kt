package grevi.msx.poketest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import grevi.msx.poketest.mModel.Pokemon
import grevi.msx.poketest.Rest.ApiService
import grevi.msx.poketest.Rest.Repository
import grevi.msx.poketest.Static.Common
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pokemon_item.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var pokemonAdapter : PokemonItemAdapter
    private var apiService : ApiService

    init {
        val retrofit = Repository.mRetrofit()
        apiService = retrofit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipe_refresh.setOnRefreshListener(this)
        supportActionBar?.hide()
        fetchingData()
    }

    private fun fetchingData() {
        progressBar(false)
        GlobalScope.launch(Dispatchers.Main) {
            apiService.getAllPokemon().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe{
                        result-> Common.pokemonList = result.pokemon!!
                    progressBar(true)
                    createRecyclerView(Common.pokemonList)
                }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favorite_icon -> Toast.makeText(this, "Favorite Pokemon", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun progressBar(state : Boolean)  {
        if (state) {
            main_progress_bar.visibility = View.GONE
        } else {
            main_progress_bar.visibility = View.VISIBLE
        }
    }

    private fun setupDetail(pokemon: Pokemon) {
        val mIntent = Intent(this, PokemonActivity::class.java)
        mIntent.putExtra(PokemonActivity.POKEMON_OBJECT, pokemon)
        startActivity(mIntent)
    }

    override fun onRefresh() {
        Handler().postDelayed({
            swipe_refresh.setRefreshing(false)
        }, 2000)
    }


}
