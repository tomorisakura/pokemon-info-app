package grevi.msx.poketest.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import grevi.msx.poketest.View.Adapter.FavoritePokemonAdapter
import grevi.msx.poketest.R
import grevi.msx.poketest.Static.Common
import grevi.msx.poketest.mModel.FavoritePokemon
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    lateinit var p_adapter : FavoritePokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        val toolbar : Toolbar = findViewById(R.id.toolbar_favorite)
        setSupportActionBar(toolbar)
        setDatas()
    }

    private fun setRecyclerView(fav : List<FavoritePokemon>) {
        rv_pokemon_favorite.layoutManager = GridLayoutManager(this, 2)
        p_adapter = FavoritePokemonAdapter(this, fav)
        rv_pokemon_favorite.adapter = p_adapter
        p_adapter.setOnItemClickCallBack(object : FavoritePokemonAdapter.OnItemClickCallBack {
            override fun onItemClicked(mFav: FavoritePokemon) {
                setDetailFav(mFav)
            }
        })
    }

    private fun setDatas() {
        val mDatas = FirebaseDatabase.getInstance().reference.child("favorite_list")
        mDatas.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e(Common.FB_FAIL, "Failed")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    GlobalScope.launch(Dispatchers.Main) {
                        getDatas(p0)
                    }
                } else {
                    setSnackBar("List Favorite Kosong !")
                }
            }

            private suspend fun getDatas(ds : DataSnapshot) {
                Common.favPokemon.clear()
                ds.children.forEach {
                    val a = it.getValue(FavoritePokemon::class.java) as FavoritePokemon
                    Common.favPokemon.add(a)
                    println(a)
                }
                setRecyclerView(Common.favPokemon)
                delay(1000)
            }
        })
    }

    private fun setSnackBar(message : String) {
        Snackbar.make(fav_cordinator, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setDetailFav(f : FavoritePokemon) {
        var mIntent = Intent(this, FavoritePokemonActivity::class.java)
        mIntent.putExtra(FavoritePokemonActivity.FAV_POKEMON, f)
        startActivity(mIntent)
    }
}
