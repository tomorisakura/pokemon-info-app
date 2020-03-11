package grevi.msx.poketest.Rest





import grevi.msx.poketest.Model.PokemonResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("pokedex.json")
    fun getAllPokemon() : Observable<PokemonResponse>
}
