package com.aelsi2.practice14

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.aelsi2.practice14.utils.debouncers.OnClickDebouncer
import org.json.JSONArray
import org.json.JSONObject

class Recipe : ComponentActivity(), View.OnClickListener  {
    private lateinit var backButton : View
    private lateinit var recipeIcon : ImageView
    private lateinit var recipeImage : ImageView
    private lateinit var recipeName : TextView
    private lateinit var recipeText : TextView
    private var recipeIndex : Int = 0
    private val onClickDebouncer = OnClickDebouncer(this, 500)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        initViews()
        recipeIndex = intent.extras?.getInt("recipeIndex") ?: 0
        lifecycleScope.launchWhenCreated {
            readRecipe()
        }
    }
    private fun initViews() {
        backButton = findViewById(R.id.status_bar_back_button)
        recipeIcon = findViewById(R.id.recipe_icon)
        recipeImage = findViewById(R.id.recipe_image)
        recipeName = findViewById(R.id.recipe_name)
        recipeText = findViewById(R.id.recipe_text)
        backButton.setOnClickListener(onClickDebouncer)
    }
    private fun readRecipe() {
        val entriesPath = "entries.json"
        val jsonStr = application.assets.open(entriesPath).bufferedReader(Charsets.UTF_8).readText()
        val jsonEntries = JSONArray(jsonStr)
        if (recipeIndex >= jsonEntries.length()) return
        val jsonRecipe = jsonEntries.get(recipeIndex) as JSONObject
        val recipeNameString = jsonRecipe.getString("name")
        val iconPath = jsonRecipe.getString("icon")
        val recipePath = jsonRecipe.getString("recipe")
        val imagePath = jsonRecipe.getString("image")
        val iconStream = application.assets.open(iconPath)
        val iconDrawable = Drawable.createFromStream(iconStream, null)
        val imageStream = application.assets.open(imagePath)
        val imageDrawable = Drawable.createFromStream(imageStream, null)
        val recipeTextString = application.assets.open(recipePath).bufferedReader(Charsets.UTF_8).readText()
        recipeIcon.setImageDrawable(iconDrawable)
        recipeImage.setImageDrawable(imageDrawable)
        recipeName.text = recipeNameString
        recipeText.text = recipeTextString
    }
    override fun onClick(view: View?) {
        when (view) {
            backButton -> finish()
        }
    }
}