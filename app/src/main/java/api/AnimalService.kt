package api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnimalService {
    const val BASE_URL = "https://raw.githubusercontent.com/CatalinStefan/animalApi/master/"
    //const val BASE_URL = "https://api.api-ninjas.com/v1"
    //oBDF6rzdWNQKqhJRBFpP8w==dh440dR8qCG63Xn4
    private fun getRetrofit() = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: AnimalApi = getRetrofit().create(AnimalApi::class.java)
}