package grevi.msx.poketest.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseError
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

    private fun setToast(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
        datasRefrences.addValueEventListener( object : ValueEventListener {
            val mDataRef = datasRefrences.child("pokemon_${mObject?.num}")
            val removeRef : Query = mDataRef.ref.orderByChild("pokemon_num").equalTo(mObject?.num)

            override fun onCancelled(p0: DatabaseError) {
                try {
                    setSnackBar("Failed 404")
                } catch (e : Exception) {
                    Log.d(Common.FB_FAIL, e.message.toString())
                }
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (!p0.child("pokemon_${mObject?.num}").exists()) {
                    GlobalScope.launch(Dispatchers.Main) {
                        addPokemon()
                    }
                } else {
                    if (p0.exists()) {
                        setSnackBar("Pokemon Sudah Ditambahkan")
                    }
                }
            }

            private suspend fun addPokemon() {
                mDataRef.child("pokemon_num").setValue(mObject?.num)
                mDataRef.child("pokemon_name").setValue(mObject?.name)
                mDataRef.child("image_url").setValue(Common.IMAGE_URL+mObject?.num+".png")
                mDataRef.child("pokemon_candy").setValue(mObject?.candy)
                mDataRef.child("pokemon_type").setValue(mObject?.type)
                setSnackBar("${mObject?.name} ditambahkan ke dalam bag !")
                delay(1000)
            }
        })
    }

}
