package grevi.msx.poketest.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import grevi.msx.poketest.BR
import grevi.msx.poketest.R
import grevi.msx.poketest.mModel.Pokemon
import grevi.msx.poketest.Static.Common
import grevi.msx.poketest.databinding.ActivityPokemonBinding
import kotlinx.android.synthetic.main.activity_pokemon.*
import kotlinx.coroutines.*

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
        val mBinding : ActivityPokemonBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_pokemon
        )
        Glide.with(this).load(Common.IMAGE_URL+mObject?.num+".png")
            .placeholder(R.drawable.ic_egg)
            .dontAnimate()
            .override(120, 120)
            .into(img_detail)
        mBinding.setVariable(BR.mDataPokemon, mObject)
        mBinding.executePendingBindings()
        tv_type.text = mObject?.type.toString().replace("[", "").replace("]", "")
        tv_weakness.text = mObject?.weak.toString().replace("[","").replace("]", "")
        pokemonName = mObject?.name
    }

    private fun setSnackBar(message: String) {
        Snackbar.make(cordinator, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onClick(p0: View) {
        when(p0.id) {
            R.id.btn_add_bag ->  {
                savePokemon()
            }
            R.id.back_arrow -> {
                onBackPressed()
            }
        }
    }

    private fun savePokemon() {
        val mObject = intent.getParcelableExtra<Pokemon>(POKEMON_OBJECT)
        val datas = FirebaseDatabase.getInstance()
        val datasRefrences = datas.getReference("favorite_list")
        val mDataRef = datasRefrences.child("pokemon_${mObject?.num}")
        datasRefrences.addListenerForSingleValueEvent( object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                Log.e(Common.FB_FAIL, p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (!p0.child("pokemon_${mObject?.num}").exists()) {
                    GlobalScope.launch(Dispatchers.Main) {
                        addPokemon()
                    }
                    setSnackBar("Pokemon ${mObject?.name} Ditambahkan")
                } else {
                    GlobalScope.launch(Dispatchers.IO) {
                        deleteDatas()
                    }
                }
            }

            private suspend fun addPokemon() {
                delay(1000L)
                mDataRef.child("pokemon_num").setValue(mObject?.num)
                mDataRef.child("pokemon_name").setValue(mObject?.name)
                mDataRef.child("image_url").setValue(Common.IMAGE_URL+mObject?.num+".png")
                mDataRef.child("pokemon_candy").setValue(mObject?.candy)
                mDataRef.child("pokemon_type").setValue(mObject?.type)
                mDataRef.child("pokemon_candy_count").setValue(mObject?.candy_count)
                mDataRef.child("pokemon_egg").setValue(mObject?.egg)
                mDataRef.child("pokemon_avg_spawn").setValue(mObject?.avg_spawns)
                mDataRef.child("pokemon_spawn_chance").setValue(mObject?.spawn_chance)
                mDataRef.child("pokemon_spawn_time").setValue(mObject?.spawn_time)
                mDataRef.child("pokemon_height").setValue(mObject?.height)
                mDataRef.child("pokemon_weight").setValue(mObject?.weight)
                mDataRef.child("pokemon_weakness").setValue(mObject?.weak)
            }

            private suspend fun deleteDatas() {
                delay(1000L)
                mDataRef.removeValue()
                setSnackBar("Pokemon Telah Dihapus Dari List !")
            }
        })
    }

}
