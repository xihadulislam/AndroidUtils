package com.xihad.androidutils.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

object ImageUtil {

    fun ImageView.imageToBitmap(): ByteArray {
        val bitmap = (this.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        return stream.toByteArray()
    }

    fun ImageView.imageToBase64(): String {
        return Base64.encodeToString(this.imageToBitmap(), Base64.NO_WRAP)
    }

    fun ImageView.loadImage(imageLink: String, placeholder: Int) {
        Glide.with(this.context)
            .load(imageLink)
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(placeholder)
            .placeholder(placeholder)
            .dontTransform()
            .into(this)
    }

    fun ImageView.loadOfflineImage(image: Int) {
        Glide.with(this.context).load(image).placeholder(image).into(this)
    }


    fun ByteArray.byteArrayToBase64(): String =
        Base64.encodeToString(this, Base64.NO_WRAP)


}