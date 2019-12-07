package ru.fluffydreams.cardography.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.fluffydreams.cardography.R
import ru.fluffydreams.cardography.ui.cards.list.CardsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CardsFragment.newInstance())
                .commitNow()
        }
    }

}
