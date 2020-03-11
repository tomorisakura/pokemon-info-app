package grevi.msx.poketest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import grevi.msx.poketest.Model.Pokemon
import grevi.msx.poketest.Static.Common
import kotlinx.android.synthetic.main.activity_pokemon.*

class PokemonActivity : AppCompatActivity() {

    companion object {
        val POKEMON_OBJECT = "pokemon_object"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)
        setupDetail()
    }

    private fun setupDetail() {
        val mObject = intent.getParcelableExtra<Pokemon>(POKEMON_OBJECT)
        det_pokemon_name.text = mObject.name
        Glide.with(this).load(Common.IMAGE_URL+mObject.num+".png")
            .placeholder(R.drawable.ic_launcher_foreground)
            .dontAnimate()
            .into(img_detail)
    }
}
