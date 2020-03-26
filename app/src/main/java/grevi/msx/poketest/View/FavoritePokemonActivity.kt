package grevi.msx.poketest.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import grevi.msx.poketest.BR
import grevi.msx.poketest.R
import grevi.msx.poketest.databinding.ActivityFavoritePokemonBinding
import grevi.msx.poketest.mModel.FavoritePokemon
import kotlinx.android.synthetic.main.activity_favorite_pokemon.*
import kotlinx.coroutines.*

class FavoritePokemonActivity : AppCompatActivity() , View.OnClickListener{

    companion object {
        val FAV_POKEMON = "fav_pokemon"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_pokemon)
        setupDetailFav()
        back_arrow.setOnClickListener(this)
    }

    private fun setupDetailFav() {
        val mResult = intent.getParcelableExtra<FavoritePokemon>(FAV_POKEMON)
        val mBindng : ActivityFavoritePokemonBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite_pokemon)
        mBindng.setVariable(BR.mDataPokemonFav, mResult)
        Glide.with(this).load(mResult?.image_url).into(img_detail_fav)
        tv_weakness_fav.text = mResult?.pokemon_weakness.toString().replace("[","").replace("]","")
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.back_arrow -> {
                onBackPressed()
            }
        }
    }
}
