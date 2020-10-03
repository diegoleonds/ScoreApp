package com.example.scoreapp.ui.dialog

import android.app.Dialog
import android.content.DialogInterface.BUTTON_POSITIVE
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.scoreapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.dialog_game.*

class GameDialog(
    var title: String,
    val click: DialogClick,
    val actualText: String = ""
) : DialogFragment() {

    lateinit var textField: TextInputEditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = (requireActivity().layoutInflater)
        val view = inflater.inflate(R.layout.dialog_game, null)
        textField = view.findViewById(R.id.game_dialog_textfield)
        textField.setText(actualText)
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                click.negativeClick()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                if (!textField.text.toString().isEmpty()) {
                    click.positiveClick(textField.text.toString())
                }
            }
            .setView(view)
            .create()
    }
}