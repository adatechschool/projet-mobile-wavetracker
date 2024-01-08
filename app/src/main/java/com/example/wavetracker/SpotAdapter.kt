package com.example.wavetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.wavetracker.Spot

class SpotAdapter(private val context: Context, private val dataSource: List<Spot>) : ArrayAdapter<Spot>(context, 0, dataSource)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_spot, parent, false)

        val spot = getItem(position)

        val imageView = view.findViewById<ImageView>(R.id.imageViewSpot)
        val textViewNom = view.findViewById<TextView>(R.id.textViewNom)
        val textViewLieu = view.findViewById<TextView>(R.id.textViewLieu)

        spot?.let {
            imageView.setImageResource(spot.image)
            textViewNom.text = spot.nom
            textViewLieu.text = spot.lieu
        }

        return view
    }
}