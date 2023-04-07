package com.oxymium.realestatemanager.utils

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.text.color


fun provideCompatColor(context: Context, colorId: Int) = ContextCompat.getColor(context, colorId)

fun provideCompatDrawable(context: Context, drawableId: Int) = AppCompatResources.getDrawable(context, drawableId)

fun provideSpannableStringBuilder(context: Context, colorId: Int, firstStringId: Int, secondStringId: Int) =
    SpannableStringBuilder()
        .append(context.getString(firstStringId))
        .color(ContextCompat.getColor(context, colorId)){
            // Added in between for space
            append(" - ")
            append(context.getString(secondStringId))
        }
