package com.base.rickandmorty.ui.pages.main.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.base.rickandmorty.R
import com.base.rickandmorty.ui.pages.main.viewmodel.MainActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_filter.*

class DialogFilter(
    private val viewModel: MainActivityViewModel
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_filter, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)

        btn_filter_submit.setOnClickListener {
            var status = ""
            when (radioGroup.checkedRadioButtonId) {
                rb_alive.id -> {
                    status  = "Alive"
                }
                rb_dead .id -> {
                    status = "Dead"
                }
                rb_unknown.id -> {
                    status = "unknown"
                }
            }

            viewModel.getCharactersWithFilters(name = edt_name.text.toString(), status = status)
            dialog?.dismiss()
        }

        tv_clear.setOnClickListener {
            viewModel
            viewModel.getCharacter()
            dialog?.dismiss()
        }
    }
}