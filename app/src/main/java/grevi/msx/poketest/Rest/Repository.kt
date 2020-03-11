package grevi.msx.poketest.Rest


import com.google.gson.GsonBuilder
import grevi.msx.poketest.Static.Common
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Repository {
    companion object {
        val gson = GsonBuilder().create()
        fun mRetrofit() : ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Common.BASE_URL)
                .build()
            return  retrofit.create(ApiService::class.java)
        }
    }
}