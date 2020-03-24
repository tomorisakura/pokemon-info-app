package grevi.msx.poketest.Static

import grevi.msx.poketest.mModel.FavoritePokemon
import grevi.msx.poketest.mModel.Pokemon

object Common {
    var pokemonList : List<Pokemon> = ArrayList()
    var favPokemon : ArrayList<FavoritePokemon> = ArrayList()
    val BASE_URL = "https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/"
    val IMAGE_URL = "http://www.serebii.net/pokemongo/pokemon/"
    val FB_FAIL = "404"
    val status = 11
    val TAG = "tag"
}