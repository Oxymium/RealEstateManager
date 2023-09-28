package com.oxymium.realestatemanager.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.oxymium.realestatemanager.R

class CustomSpinnerAdapter(context: Context, private val resLayout: Int, private val values: List<String>): ArrayAdapter<String>(context, resLayout, values) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Use the custom dropdown item layout for all dropdown items
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.spinner_dropdown_item, parent, false)
        val textView = view.findViewById<TextView>(R.id.text_dropdown)
        textView.text = values[position]
        return view
    }
}