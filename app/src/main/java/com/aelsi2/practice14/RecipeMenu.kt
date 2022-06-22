package com.aelsi2.practice14

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableRecyclerView
import com.aelsi2.practice14.utils.debouncers.OnClickDebouncer
import com.aelsi2.practice14.utils.debouncers.OnItemClickDebouncer
import kotlinx.coroutines.delay
import org.json.JSONArray
import org.json.JSONObject

class RecipeMenu : ComponentActivity(), OnItemClickListener, View.OnClickListener {
    private val recipes = ArrayList<RecipeMenuEntry>()
    private lateinit var backButton : View
    private lateinit var recipeView : WearableRecyclerView
    private val onItemClickDebouncer = OnItemClickDebouncer(this, 500)
    private val onClickDebouncer = OnClickDebouncer(this, 500)
    private val menuAdapter = RecipeMenuAdapter(recipes, onItemClickDebouncer)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_menu)
        initViews()
        lifecycleScope.launchWhenCreated {
            readRecipes()
        }
    }
    private fun initViews() {
        backButton = findViewById(R.id.status_bar_back_button)
        recipeView = findViewById(R.id.recipe_list_view)
        recipeView.adapter = menuAdapter
        recipeView.layoutManager = LinearLayoutManager(this)
        backButton.setOnClickListener(onClickDebouncer)
    }
    private fun readRecipes() {
        val entriesPath = "entries.json"
        val jsonStr = application.assets.open(entriesPath).bufferedReader(Charsets.UTF_8).readText()
        val jsonEntries = JSONArray(jsonStr)
        for (i in 0 until jsonEntries.length()) {
            val jsonRecipe = jsonEntries.get(i) as JSONObject
            val recipeName = jsonRecipe.getString("name")
            val iconPath = jsonRecipe.getString("icon")
            val recipePath = jsonRecipe.getString("recipe")
            val imagePath = jsonRecipe.getString("image")
            val iconStream = application.assets.open(iconPath)
            val drawable = Drawable.createFromStream(iconStream, null)
            recipes.add(RecipeMenuEntry(recipeName, drawable, recipePath, imagePath))
        }
        menuAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(index: Int) {
        val intent = Intent(this, Recipe::class.java)
        intent.putExtra("recipeIndex", index)
        startActivity(intent)
    }

    override fun onClick(view: View?) {
        when (view) {
            backButton -> {
                finish()
            }
        }
    }
}