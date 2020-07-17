package vlados.dudos.data

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import vlados.dudos.Models.*

interface Api {

    @GET("authentication/guest_session/new?api_key=f4247e4401183f4fd54b6f52c8e0b48c")
    fun guestSession(): Observable<GuestModel>

    @POST("movie/{movie_id}/rating?api_key=f4247e4401183f4fd54b6f52c8e0b48c")
    fun postValue(@Path("movie_id") movie_id: String, @Query("guest_session_id") guest_session_id: String, @Body rateBodyModel: RateBodyModel): Observable<RateResponseModel>

    @GET("movie/now_playing?api_key=f4247e4401183f4fd54b6f52c8e0b48c&language=ru-RU")
    fun getNowPlaing(): Observable<MovieModel>

    @GET("movie/popular?api_key=f4247e4401183f4fd54b6f52c8e0b48c&language=ru-RU")
    fun getPopular(@Query("page")page: Int): Observable<MovieModel>

    @GET("movie/upcoming?api_key=f4247e4401183f4fd54b6f52c8e0b48c&language=ru-RU")
    fun getSoon(@Query("page")page: Int): Observable<MovieModel>

    @GET("movie/{movie_id}/credits?api_key=f4247e4401183f4fd54b6f52c8e0b48c&language=ru-RU")
    fun getActor(@Path("movie_id") movie_id: String): Observable<ActorModel>

    @GET("genre/movie/list?api_key=f4247e4401183f4fd54b6f52c8e0b48c&language=ru-RU")
    fun getGenre():Observable<GenreModel>

    @GET("search/movie?api_key=f4247e4401183f4fd54b6f52c8e0b48c&language=ru-Ru&page=1&include_adult=true")
    fun searchAdult(@Query("query") query : String): Observable<SearchModel>

    @GET("movie/{movie_id}/videos?language=ru-RU&api_key=f4247e4401183f4fd54b6f52c8e0b48c")
    fun findTrailer(@Path("movie_id") movie_id: String): Observable<TrailerModel>


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