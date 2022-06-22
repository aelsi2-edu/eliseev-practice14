package com.aelsi2.practice14

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import com.aelsi2.practice14.databinding.ActivityMainBinding
import com.aelsi2.practice14.utils.debouncers.OnClickDebouncer

class MainActivity : ComponentActivity(), View.OnClickListener {
    private lateinit var nextButton : View
    private lateinit var backButton : View
    private val onClickDebouncer = OnClickDebouncer(this, 500)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }
    private fun initViews() {
        backButton = findViewById(R.id.status_bar_back_button)
        nextButton = findViewById(R.id.next_button)
        backButton.visibility = View.GONE
        nextButton.setOnClickListener(onClickDebouncer)
    }

    private fun goToMenu() {
        intent = Intent(this, RecipeMenu::class.java)
        startActivity(intent)
    }

    override fun onClick(view: View?) {
        when (view) {
            nextButton -> {
                goToMenu()
            }
        }
    }
}