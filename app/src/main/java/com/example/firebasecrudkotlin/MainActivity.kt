package com.example.firebasecrudkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var editTextName: EditText
    lateinit var editTextEmail: EditText
    lateinit var editTextContact: EditText
    lateinit var ratingBar: RatingBar
    lateinit var buttonSave: Button
    lateinit var ref: DatabaseReference
    lateinit var list: MutableList<Model>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextContact = findViewById(R.id.editTextContact)
        ratingBar = findViewById(R.id.ratingBar)
        buttonSave = findViewById(R.id.buttonSave)
        listView = findViewById(R.id.listview1)

        ref = FirebaseDatabase.getInstance().getReference("user")
        list = mutableListOf()


        buttonSave.setOnClickListener() {
            saveInfo()
        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    list.clear()
                    for (h in p0.children) {
                        val i = h.getValue(Model::class.java)
                        list.add(i!!)
                    }

                    val adapter = Adapter(this@MainActivity, R.layout.custom_layout, list)
                    listView.adapter = adapter
                }

            }
        });
    }

    private fun saveInfo() {
        val name = editTextName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val contact = editTextContact.text.toString().trim()
        if (name.isEmpty()) {
            editTextName.error = "Please enter value"
        }
        if (email.isEmpty()) {
            editTextEmail.error = "Please enter value"
        }
        if (contact.isEmpty()) {
            editTextContact.error = "Please enter value"
        }
        val id = ref.push().key
//        val model = Model(id, name, email, contact, ratingBar.numStars)
        val model = Model(id, name, email, contact, ratingBar.rating.toInt())
        ref.child(id!!).setValue(model).addOnCompleteListener {
            Toast.makeText(this, "Data save successfully", Toast.LENGTH_LONG).show()
        }
    }
}