package fr.epita.android.hellogames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.game_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide


class GameInfo : Fragment()
{
    lateinit var game : GameData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.game_info, container, false)
    }

    override fun onViewCreated(fragmentView: View, savedInstanceState: Bundle?)
    {
        val urlGames = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(urlGames).addConverterFactory(jsonConverter).build()
        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val wsCallback: Callback<GameData> = object : Callback<GameData>
        {
            override fun onFailure(call: Call<GameData>, t: Throwable)
            {
            }

            override fun onResponse(call: Call<GameData>, response: Response<GameData>)
            {
                if (response.code() == 200)
                {
                    val responseData = response.body()
                    if (responseData != null)
                    {
                        game = responseData
                        txt_description.text = responseData.description_en
                        name.text = responseData.name
                        players.text = responseData.players.toString()
                        type.text = responseData.type
                        year.text = responseData.year.toString()
                        Glide.with(context as Context).load(game.picture).into(game_picture)
                    }
                }
            }
        }
        if (getArguments()!!.containsKey("id"))
            service.getGameData(getArguments()!!.getInt("id")).enqueue(wsCallback)


        button.setOnClickListener(View.OnClickListener
        {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, game.url)
            startActivity(intent)
        })
    }
}