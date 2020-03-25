package grevi.msx.poketest.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import grevi.msx.poketest.BR
import grevi.msx.poketest.R
import grevi.msx.poketest.databinding.ActivityFavoritePokemonBinding
import grevi.msx.poketest.mModel.FavoritePokemon
import kotlinx.android.synthetic.main.activity_favorite_pokemon.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoritePokemonActivity : AppCompatActivity() {

    companion object {
        val FAV_POKEMON = "fav_pokemon"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_pokemon)
        GlobalScope.launch(Dispatchers.Main) {
            setupDetailFav()
        }
    }

    private suspend fun setupDetailFav() {
        val mResult = intent.getParcelableExtra<FavoritePokemon>(FAV_POKEMON)
        val mBindng : ActivityFavoritePokemonBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite_pokemon)
        mBindng.setVariable(BR.mDataPokemonFav, mResult)
        Glide.with(this).load(mResult?.image_url).into(img_detail_fav)
        tv_weakness_fav.text = mResult?.pokemon_weakness.toString().replace("[","").replace("]","")
        delay(1000)
    }
}
