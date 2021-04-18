package com.base.component.ui.characteritem

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class CharacterItemViewHolder(var view: View) : ViewHolder<CharacterItemDTO>(view) {

    private lateinit var localPrefManager: LocalPrefManager

    private val rootView = view.findViewById<RelativeLayout>(R.id.rootView)
    private val imgCharacter = view.findViewById<ImageView>(R.id.iv_character)
    private val tvName = view.findViewById<TextView>(R.id.tv_name)
    private val tvStatus = view.findViewById<TextView>(R.id.tv_status)
    private val tvSpecies = view.findViewById<TextView>(R.id.tv_species)
    private val ivStatus = view.findViewById<ImageView>(R.id.iv_status)
    private val ibFavorite = view.findViewById<ImageButton>(R.id.ib_favorite)

    override fun bind(item: CharacterItemDTO) {

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
                CharacterItemViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                                R.layout.item_character_item,
                                parent,
                                false
                        )
                )
    }

    class BinderFactory @Inject constructor() : ViewHolderBinder {
        override fun bind(holder: RecyclerView.ViewHolder, item: DisplayItem) {
            (holder as CharacterItemViewHolder).bind(item as CharacterItemDTO)
        }
    }
}