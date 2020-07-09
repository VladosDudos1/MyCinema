package vlados.dudos.data

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vlados.dudos.Models.ActorModel
import vlados.dudos.Models.GenreModel
import vlados.dudos.Models.MovieModel
import vlados.dudos.Models.SearchModel

interface Api {


    @GET("movie/now_playing?api_key=f4247e4401183f4fd54b6f52c8e0b48c&language=ru-RU")
    fun getNowPlaing(): Observable<MovieModel>

    @GET("movie/popular?api_key=f4247e4401183f4fd54b6f52c8e0b48c&language=ru-RU")
    fun getPopular(): Observable<MovieModel>

    @GET("movie/upcoming?api_key=f4247e4401183f4fd54b6f52c8e0b48c&language=ru-RU")
    fun getSoon(): Observable<MovieModel>

    @GET("movie/{movie_id}/credits?api_key=f4247e4401183f4fd54b6f52c8e0b48c&language=ru-RU")
    fun getActor(@Path("movie_id") movie_id: String): Observable<ActorModel>

    @GET("genre/movie/list?api_key=f4247e4401183f4fd54b6f52c8e0b48c&language=ru-RU")
    fun getGenre():Observable<GenreModel>

    @GET("search/movie?api_key=f4247e4401183f4fd54b6f52c8e0b48c&language=ru-Ru&page=1&include_adult=true")
    fun search(@Query("query") query : String): Observable<SearchModel>

    companion object {
        fun createApi(): Api {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(Api::class.java)
        }
    }
}