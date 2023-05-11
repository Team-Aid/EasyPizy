package com.example.easypizy.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.easypizy.databinding.DialogPlusCigaBinding
import com.example.easypizy.presentation.view_model.SmokeMemoViewModel
import java.time.LocalDate

class PlusCigaDialog : DialogFragment() {
    private val viewModel by activityViewModels<SmokeMemoViewModel>()
    private lateinit var binding: DialogPlusCigaBinding

    private lateinit var dateTimeSpinner: DatePicker
    private lateinit var editText: EditText
    private lateinit var cancelButton: Button
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogPlusCigaBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateTimeSpinner = binding.dateSpinner
        editText = binding.editTextCigaNum
        cancelButton = binding.cancleButton
        confirmButton = binding.confirmButton

        cancelButton.setOnClickListener {
            this.dismiss()
        }

        confirmButton.setOnClickListener {
            val date: LocalDate = LocalDate.of(dateTimeSpinner.year, dateTimeSpinner.month+1, dateTimeSpinner.dayOfMonth)
            val count = editText.text.toString().toInt()
            viewModel.plusCigarette(date, count)
            this.dismiss()
        }
    }
}