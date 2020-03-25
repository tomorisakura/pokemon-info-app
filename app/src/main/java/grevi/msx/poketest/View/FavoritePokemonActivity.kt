package grevi.msx.poketest.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import grevi.msx.poketest.R
import grevi.msx.poketest.databinding.ActivityFavoritePokemonBinding

class FavoritePokemonActivity : AppCompatActivity() {

    companion object {
        val FAV_POKEMON = "fav_pokemon"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_pokemon)
    }

    private fun setupDetailFav() {
        val mBindng : ActivityFavoritePokemonBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite_pokemon)
    }
}
