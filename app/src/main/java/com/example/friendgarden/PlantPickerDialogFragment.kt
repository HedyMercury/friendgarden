package com.example.friendgarden

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class PlantPickerDialogFragment(
    private val slotIndex: Int,
    private val listener: PlantSelectedListener
) : DialogFragment() {
    interface PlantSelectedListener {
        fun onPlantSelected(slotIndex: Int, type: PlantType)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.instructions_pickplant))
            .setItems(arrayOf(
                getString(R.string.rose),
                getString(R.string.sunflower),
                getString(R.string.snake)
            )) {_, which ->
                val type = when (which) {
                    0 -> PlantType.ROSE
                    1 -> PlantType.SUNFLOWER
                    2 -> PlantType.SNAKE
                    else -> throw IllegalArgumentException("Oops, I don't understand your choice?")
                }
                listener.onPlantSelected(slotIndex, type)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
    }
}