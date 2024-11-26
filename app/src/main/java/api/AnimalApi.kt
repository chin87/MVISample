package api

import model.Animal
import retrofit2.http.GET
import retrofit2.http.Headers

interface AnimalApi {

    //@Headers("X-Api-Key: " + "oBDF6rzdWNQKqhJRBFpP8w==dh440dR8qCG63Xn4")
    //@GET("v1/animals?name=a")
    @GET("animals.json")
    suspend fun getAnimals(): List<Animal>
}