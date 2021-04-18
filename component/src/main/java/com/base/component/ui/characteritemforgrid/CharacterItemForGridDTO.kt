package com.base.component.ui.characteritemforgrid

import android.os.Parcelable
import com.base.component.constant.RecyclerviewAdapterConstant
import com.base.core.ui.recyclerview.DisplayItem
import com.base.data.response.Results
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterItemForGridDTO(
        val results: Results
) : Parcelable, DisplayItem(RecyclerviewAdapterConstant.TYPES.TYPE_CHARACTER_ITEM_FOR_GRID)