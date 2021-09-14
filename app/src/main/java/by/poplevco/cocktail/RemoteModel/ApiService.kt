package by.poplevco.cocktail.RemoteModel


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val BASE_URL = "https:/the-cocktail-db.p.rapidapi.com/"
const val KEY = ""

interface ApiService {

    @Headers("Content-Type: application/json", KEY)
    @GET("filter.php?c=Cocktail")
    suspend fun getCocktails(): DrinkList

    @Headers("Content-Type: application/json", KEY)
    @GET("lookup.php?")
    suspend fun getFullDetails(@Query("i") i: String): DrinkList

    @Headers("Content-Type: application/json", KEY)
    @GET("filter.php?a=Alcoholic")
    suspend fun getAlcCocktails(): DrinkList

    @Headers("Content-Type: application/json", KEY)
    @GET("filter.php?a=Non_Alcoholic")
    suspend fun getNotAlcCocktails(): DrinkList

    companion object Factory{
        fun create(): ApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}