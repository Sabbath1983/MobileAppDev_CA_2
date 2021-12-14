package org.wit.kid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.kid.databinding.CardKidBinding
import org.wit.kid.models.KidModel


interface KidListener {
    fun onKidClick(kid: KidModel)
}


class KidAdapter constructor(private var kids: List<KidModel>,
                             private val listener: KidListener) :
    RecyclerView.Adapter<KidAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardKidBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val kid = kids[holder.adapterPosition]
        holder.bind(kid, listener)
    }

    override fun getItemCount(): Int = kids.size

    class MainHolder(private val binding : CardKidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(kid: KidModel, listener: KidListener) {
            binding.name.text = kid.name
            binding.age.text = kid.age
            Picasso.get().load(kid.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onKidClick(kid) }
        }
    }
}