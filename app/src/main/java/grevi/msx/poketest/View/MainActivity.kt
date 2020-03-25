package grevi.msx.poketest.View

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.FirebaseDatabase
import grevi.msx.poketest.R
import grevi.msx.poketest.mModel.Pokemon
import grevi.msx.poketest.Rest.ApiService
import grevi.msx.poketest.Rest.Repository
import grevi.msx.poketest.Static.Common
import grevi.msx.poketest.View.Adapter.PokemonItemAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var pokemonAdapter : PokemonItemAdapter
    private var apiService : ApiService

    init {
        val retrofit = Repository.mRetrofit()
        apiService = retrofit
    }

    companion object {
        var MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 10
        var MY_PERMISSIONS_NETWORK_ACCESS = 101
        val PERMISSION = "permission"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val toolbar : Toolbar = findViewById(R.id.toolbar_material)
        setSupportActionBar(toolbar)
        checkStoragePermission()
        fetchingData()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Helo Salamandr !")
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
        pokemonAdapter =
            PokemonItemAdapter(this, pokemon)
        pokemonAdapter.notifyDataSetChanged()
        rv_pokemon_item.adapter = pokemonAdapter

        pokemonAdapter.setOnItemClickCallback(object :
            PokemonItemAdapter.OnItemClickCallback {
            override fun onItemClicked(mPokemon: Pokemon) {
                setupDetail(mPokemon)
            }
        })
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //is not granted
            Log.d(PERMISSION, "permission deny")
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE
                )
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET),
                    MY_PERMISSIONS_NETWORK_ACCESS
                )
            }
        } else {
            Log.d(PERMISSION, "permission granted")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favorite_icon -> {
                val mIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(mIntent)
            }
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

    private fun setToast(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
