package fr.epita.android.hellogames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        val fragmentTransaction = supportFragmentManager
            .beginTransaction().replace(R.id.main_container, HelloGames()).commit()
    }
}
