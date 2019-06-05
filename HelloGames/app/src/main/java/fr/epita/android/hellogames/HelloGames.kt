package fr.epita.android.hellogames

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random




class HelloGames : Fragment()
{
    lateinit var topLeft : Game
    lateinit var topRight : Game
    lateinit var botRight : Game
    lateinit var botLeft : Game

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(fragmentView: View, savedInstanceState: Bundle?)
    {
        val urlGames = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(urlGames).addConverterFactory(jsonConverter).build()
        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val wsCallback: Callback<List<Game>> = object : Callback<List<Game>>
        {
            override fun onFailure(call: Call<List<Game>>, t: Throwable)
            {
            }

            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>)
            {
                if (response.code() == 200)
                {
                    val responseData = response.body()
                    if (responseData != null)
                    {
                        val games = response.body() as ArrayList<Game>
                        var r = Random.nextInt(0, games.size - 1)
                        topLeft = games[r]
                        games.removeAt(r)
                        r = Random.nextInt(0, games.size - 1)
                        topRight = games[r]
                        games.removeAt(r)
                        r = Random.nextInt(0, games.size - 1)
                        botLeft = games[r]
                        games.removeAt(r)
                        r = Random.nextInt(0, games.size - 1)
                        botRight = games[r]

                        Glide.with(context as Context).load(topLeft.picture).into(app_top_left)
                        Glide.with(context as Context).load(topRight.picture).into(app_top_right)
                        Glide.with(context as Context).load(botLeft.picture).into(app_bot_left)
                        Glide.with(context as Context).load(botRight.picture).into(app_bot_right)
                    }
                }
            }
        }
        service.listGames().enqueue(wsCallback)
        app_top_left.setOnClickListener(View.OnClickListener { loadFragmentGame(topLeft.id) })
        app_top_right.setOnClickListener(View.OnClickListener { loadFragmentGame(topRight.id) })
        app_bot_left.setOnClickListener(View.OnClickListener { loadFragmentGame(botLeft.id) })
        app_bot_right.setOnClickListener(View.OnClickListener { loadFragmentGame(botRight.id) })
    }

    fun loadFragmentGame(id : Int)
    {
        val arguments = Bundle()
        arguments.putInt("id", id)
        val fragment = GameInfo()
        fragment.setArguments(arguments)
        fragmentManager!!.beginTransaction().replace(R.id.main_container, fragment).commit()
    }
}
