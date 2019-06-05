package fr.epita.android.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceInterface
{
    @GET("game/list")
    fun listGames(): Call<List<Game>>

    @GET("game/details")
    fun getGameData(@Query("game_id") game_id : Int) : Call<GameData>
}