package com.sergeenko.alexey.titangym

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import java.io.IOException

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun AssetManager.assetsToBitmap(fileName: String): Bitmap?{
    return try {
        with(open(fileName)){
            BitmapFactory.decodeStream(this)
        }
    } catch (e: IOException) { null }
}