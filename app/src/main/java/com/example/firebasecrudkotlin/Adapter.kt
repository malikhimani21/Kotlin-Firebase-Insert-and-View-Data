package com.example.firebasecrudkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class Adapter(context: Context, val layoutResId: Int, val list: List<Model>) :
    ArrayAdapter<Model>(context, layoutResId, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.fetch_name)
        val textViewEmail = view.findViewById<TextView>(R.id.fetch_email)
        val textViewContact = view.findViewById<TextView>(R.id.fetch_contact)
        val textViewRating = view.findViewById<TextView>(R.id.fetch_rating)

        val i = list[position]

        textViewName.text = i.name
        textViewEmail.text = i.email
        textViewContact.text = i.contact
        textViewRating.text = i.rating.toString()

        return view
    }


}
