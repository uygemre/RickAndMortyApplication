package com.base.component.ui.characteritemforgrid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.base.component.R
import com.base.core.extensions.gone
import com.base.core.extensions.loadImage
import com.base.core.helpers.LocalPrefManager
import com.base.core.ui.recyclerview.DisplayItem
import com.base.core.ui.recyclerview.ViewHolder
import com.base.core.ui.recyclerview.ViewHolderBinder
import com.base.core.ui.recyclerview.ViewHolderFactory
import javax.inject.Inject

class CharacterItemForGridViewHolder(var view: View) : ViewHolder<CharacterItemForGridDTO>(view) {

    private lateinit var localPrefManager: LocalPrefManager

    private val rootView = view.findViewById<LinearLayout>(R.id.rootView)
    private val imgCharacter = view.findViewById<ImageView>(R.id.iv_character)
    private val tvName = view.findViewById<TextView>(R.id.tv_name)
    private val tvSpecies = view.findViewById<TextView>(R.id.tv_species)
    private val tvStatus = view.findViewById<TextView>(R.id.tv_status)
    private val ivStatus = view.findViewById<ImageView>(R.id.iv_status)
    private val ibFavorite = view.findViewById<ImageButton>(R.id.ib_favorites)

    override fun bind(item: CharacterItemForGridDTO) {

        if (view.context is AppCompatActivity) {
            val holder = view.context as AppCompatActivity
            val sharedPreferences =
                    holder.getSharedPreferences("rickandmorty", Context.MODE_PRIVATE)
            localPrefManager = LocalPrefManager(sharedPreferences)
        }

        imgCharacter.loadImage(url = item.results.image ?: "")
        tvName.text = item.results.name
        tvSpecies.text = item.results.species
        tvStatus.text = item.results.status
        rootView.setOnClickListener {
            itemClickListener?.invoke(item, adapterPosition)
        }
        ibFavorite.setOnClickListener {
            itemViewClickListener?.invoke(it, item, adapterPosition)
        }

        when (item.results.status) {
            "Alive" -> {
                ivStatus.loadImage(R.drawable.ic_alive)
            }
            "Dead" -> {
                ivStatus.loadImage(R.drawable.ic_dead)
            }
            "unknown" -> {
                ivStatus.loadImage(R.drawable.ic_unknown)
            }
            else -> ivStatus.gone()
        }

        if (localPrefManager.pull(item.results.name ?: "", "") == "") {
            ibFavorite.setBackgroundResource(R.drawable.ic_favorite_unselected_16)
        } else {
            ibFavorite.setBackgroundResource(R.drawable.ic_favorite_selected_16)
        }
    }

    class HolderFactory @Inject constructor() : ViewHolderFactory {
        override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
                CharacterItemForGridViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                                R.layout.item_character_item_for_grid,
                                parent,
                                false
                        )
                )
    }

    class BinderFactory @Inject constructor() : ViewHolderBinder {
        override fun bind(holder: RecyclerView.ViewHolder, item: DisplayItem) {
            (holder as CharacterItemForGridViewHolder).bind(item as CharacterItemForGridDTO)
        }
    }
}