package com.example.friendgarden

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class InteractionDialogFragment(
    private val slotIndex: Int,
            private val listener: InteractionListener
) : DialogFragment() {

    interface InteractionListener {
        fun onInteractionComplete(slotIndex: Int, scoreDelta: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val actions = arrayOf(
            context.getString(R.string.nourish),
            context.getString(R.string.neglect)
        )

        return AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.instructions_nn))
            .setItems(actions) { _, which ->
                val isNourish = which == 0
                showIntensityDialog(isNourish)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
    }

    private fun showIntensityDialog(isNourish: Boolean) {
        val context = requireContext()
        val labels = arrayOf(
            context.getString(R.string.teeny),
            context.getString(R.string.smol),
            context.getString(R.string.big),
            context.getString(R.string.chonk)
        )
        val deltas = arrayOf(1, 3, 5, 10).map {if (isNourish) it else -it }

        AlertDialog.Builder(context)
            .setTitle(
                if(isNourish) R.string.instructions_nourishimpact else R.string.instructions_neglectimpact
            )
            .setItems(labels) {_, which ->
                val delta = deltas[which]
                listener.onInteractionComplete(slotIndex, delta)
                dismiss()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()

    }
}