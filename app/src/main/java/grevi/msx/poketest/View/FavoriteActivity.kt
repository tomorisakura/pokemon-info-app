package grevi.msx.poketest.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import grevi.msx.poketest.FavoritePokemonAdapter
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
    }

    private fun setDatas() {
        val mDatas = FirebaseDatabase.getInstance().reference.child("favorite_list")
        mDatas.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e(Common.FB_FAIL, "Failed")
            }

            override fun onDataChange(p0: DataSnapshot) {
                GlobalScope.launch(Dispatchers.Main) {
                    getDatas(p0)
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

    private fun setToast(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
