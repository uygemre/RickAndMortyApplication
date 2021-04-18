package com.base.component.constant

import com.base.component.ui.characteritem.CharacterItemViewHolder
import com.base.component.ui.characteritemforgrid.CharacterItemForGridViewHolder

class RecyclerviewAdapterConstant {

    object TYPES {
        const val TYPE_NONE = 0
        const val TYPE_CHARACTER_ITEM = 1
        const val TYPE_CHARACTER_ITEM_FOR_GRID = 2
    }

    var binderFactoryMap = mutableMapOf(
            TYPES.TYPE_CHARACTER_ITEM to CharacterItemViewHolder.BinderFactory(),
            TYPES.TYPE_CHARACTER_ITEM_FOR_GRID to CharacterItemForGridViewHolder.BinderFactory()
    )

    var holderFactoryMap = mutableMapOf(
            TYPES.TYPE_CHARACTER_ITEM to CharacterItemViewHolder.HolderFactory(),
            TYPES.TYPE_CHARACTER_ITEM_FOR_GRID to CharacterItemForGridViewHolder.HolderFactory()
    )
}
