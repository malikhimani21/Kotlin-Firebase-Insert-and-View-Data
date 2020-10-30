package com.example.firebasecrudkotlin

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.view.*

class Adapter(context: Context, val layoutResId: Int, val list: List<Model>) :
    ArrayAdapter<Model>(context, layoutResId, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.fetch_name)
        val textViewUpdate = view.findViewById<TextView>(R.id.textViewUpdate)
        val textViewEmail = view.findViewById<TextView>(R.id.fetch_email)
        val textViewContact = view.findViewById<TextView>(R.id.fetch_contact)
        val textViewRating = view.findViewById<TextView>(R.id.fetch_rating)

        val i = list[position]

        textViewName.text = i.name
        textViewEmail.text = i.email
        textViewContact.text = i.contact
        textViewRating.text = i.rating.toString()

        textViewUpdate.setOnClickListener() {
            showUpdateDialog(i)
        }

        return view
    }

    fun showUpdateDialog(i: Model) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Update Info")

        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.layout_update_dialog, null)

        val textViewNameUpdate = view.findViewById<TextView>(R.id.editTextNameUpdate)
        val textViewEmailUpdate = view.findViewById<TextView>(R.id.editTextEmailUpdate)
        val textViewContactUpdate = view.findViewById<TextView>(R.id.editTextContactUpdate)
        val textViewRatingUpdate = view.findViewById<RatingBar>(R.id.ratingBarUpdate)

        textViewNameUpdate.setText(i.name)
        textViewEmailUpdate.setText(i.email)
        textViewContactUpdate.setText(i.contact)
        textViewRatingUpdate.rating = i.rating.toFloat()

        builder.setView(view)

        builder.setPositiveButton("Update") { p0, p1 ->

            val dbI = FirebaseDatabase.getInstance().getReference("user")

            val name = textViewNameUpdate.text.toString().trim()
            val email = textViewEmailUpdate.text.toString().trim()
            val contact = textViewContactUpdate.text.toString().trim()

            if(name.isEmpty()){
                textViewNameUpdate.error = "Please enter name"
                textViewNameUpdate.requestFocus()
                return@setPositiveButton
            }

            val model1 = Model(i.id, name, email,contact, textViewRatingUpdate.rating.toInt())

            dbI.child(i.id!!).setValue(model1)

            Toast.makeText(context, "Data updated", Toast.LENGTH_LONG).show()

        }

        builder.setNegativeButton("No") { p0, p1 ->


        }

        val alert = builder.create()
        alert.show()

    }
}
