package com.example.scoreapp.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.scoreapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * @param title of the dialog
 * @param events interface for click events
 * @param actualText (optional) text that will be in textfield when dialog is open
 */
class GameDialog(
    val title: String,
    val events: DialogEvents,
    var actualText: String = ""
) : DialogFragment() {
    lateinit var textField: TextInputEditText
    lateinit var textLayout: TextInputLayout
    lateinit var confirmButton: Button
    lateinit var declineButton: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        /**
         * if activity is null a exception will be throw
         */
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.dialog_game, null)

            initViews(view)
            setClicks()
            observeTextField()

            builder.setView(view)
            builder.setTitle(title)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /**
     * set buttons clicks with the interface
     */
    private fun setClicks() {
        confirmButton.setOnClickListener {
            events.positiveClick(textField.text.toString())
        }
        declineButton.setOnClickListener { events.negativeClick() }
    }

    private fun initViews(view: View) {
        confirmButton = view.findViewById(R.id.confirmButton)
        declineButton = view.findViewById(R.id.declineButton)
        textField = view.findViewById(R.id.game_dialog_textfield_info)
        textField.setText(actualText)
        textLayout = view.findViewById(R.id.game_dialog_textlayout_info)
    }

    /**
     * observe textfield text
     */
    private fun observeTextField() {
        textField.addTextChangedListener(object : TextWatcher {
            /**
             * when the text is changed the error will be removed
             */
            override fun afterTextChanged(s: Editable?) {
                textLayout.isErrorEnabled = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    fun setError(errorMessage: String) {
        textLayout.isErrorEnabled = true
        textLayout.error = errorMessage
    }
}