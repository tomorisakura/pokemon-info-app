package grevi.msx.poketest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import grevi.msx.poketest.Model.Pokemon
import grevi.msx.poketest.Static.Common
import kotlinx.android.synthetic.main.activity_pokemon.*

class PokemonActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        val POKEMON_OBJECT = "pokemon_object"
    }

    private var pokemonName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)
        supportActionBar?.hide()
        setupDetail()
        btn_add_bag.setOnClickListener(this)
        back_arrow.setOnClickListener(this)
    }

    private fun setupDetail() {
        val mObject = intent.getParcelableExtra<Pokemon>(POKEMON_OBJECT)
        pokemonName = mObject?.name
        det_pokemon_name.text = mObject?.name
        Glide.with(this).load(Common.IMAGE_URL+mObject?.num+".png")
            .placeholder(R.drawable.ic_egg)
            .dontAnimate()
            .into(img_detail)
        tv_type.text = mObject?.type.toString().replace("[", "").replace("]", "")
        if (mObject?.egg == Common.none_egg) {
            tv_egg.text = Common.evolution
        } else {
            tv_egg.text = mObject?.egg
        }
        tv_candy_count.text = mObject?.candy_count.toString()
        tv_candy.text = mObject?.candy
        tv_spawn.text = mObject?.avg_spawns.toString()
        tv_spawn_time.text = mObject?.spawn_time
        tv_height.text = mObject?.height
        tv_weight.text = mObject?.weight
        tv_weakness.text = mObject?.weak.toString().replace("[","").replace("]", "")
    }

    private fun setToast(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(p0: View) {
        when(p0.id) {
            R.id.btn_add_bag -> {
                setToast("$pokemonName ditambahkan kedalam bag !")
            }
            R.id.back_arrow -> {
                onBackPressed()
            }
        }
    }
}
