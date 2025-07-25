package com.example.friendgarden

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

//    slots in grid
    private val plantList = arrayOfNulls<Plant>(6)

//    plant image layout
    private lateinit var plantImageViews: List<ImageView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        image views
        plantImageViews = listOf(
            findViewById(R.id.plant_image_0),
            findViewById(R.id.plant_image_1),
            findViewById(R.id.plant_image_2),
            findViewById(R.id.plant_image_3),
            findViewById(R.id.plant_image_4),
            findViewById(R.id.plant_image_5)
        )

//        event listener
        for (i in plantImageViews.indices) {
            plantImageViews[i].setOnClickListener {
                handlePlantClick(i)
            }
        }
        updateAllPlantImages()

    }

//    PLANT CLICK FUNCTION, adds plant or goes to neglect/nourish screen
    private fun handlePlantClick(index: Int) {
        val plant = plantList[index]
        if (plant == null) {
            showPlantPickerDialog(index)
        } else {
          showInteractionDialog(index)
        }
    }

//    loops visual updates for additions, health changes, etc
private fun updateAllPlantImages() {
    for (i in plantList.indices) {
        updatePlantImage(i)
    }
}

//    function that will loop to update plant images
    private fun updatePlantImage(index: Int) {
        val plant = plantList[index]
        val imageView = plantImageViews[index]

        if (plant == null) {
            imageView.setImageResource(R.drawable.plussign)
        } else {
            val health = plant.getHealthState().ordinal // ordinal but for enums?
            val plantType = plant.type.name.lowercase()
            val resName = "${plantType}${health}"
            val resId = resources.getIdentifier(resName, "drawable", packageName)
            imageView.setImageResource(resId)
        }
    }

//    dialog when picking plant
private fun showPlantPickerDialog(index: Int) {
    val dialog = PlantPickerDialogFragment(index, object : PlantPickerDialogFragment.PlantSelectedListener {
        override fun onPlantSelected(slotIndex: Int, type: PlantType) {
            plantList[slotIndex] = Plant(type)
            updatePlantImage(slotIndex)
        }
    })
    dialog.show(supportFragmentManager, "PlantPickerDialog")
}

//    interaction dialog for neglect/nourish
private fun showInteractionDialog(index:Int) {
    val dialog = InteractionDialogFragment(index, object: InteractionDialogFragment.InteractionListener {
        override fun onInteractionComplete(slotIndex: Int, scoreDelta: Int) {
            val plant = plantList[slotIndex]
            if (plant != null) {
                plant.score += scoreDelta
                updatePlantImage(slotIndex)
                Log.d("FriendGarden", "Score is now ${plant.score}")
            }
        }
    })
    dialog.show(supportFragmentManager, "InteractionDialog")
    }
}
