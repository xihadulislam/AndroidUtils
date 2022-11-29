package com.xihad.androidutils.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

object ImageUtil {

    fun imageToBitmap(image: ImageView): ByteArray {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        return stream.toByteArray()
    }

    fun imageToBase64(image: ImageView): String {
        return Base64.encodeToString(imageToBitmap(image), Base64.NO_WRAP)
    }

    fun loadImage(imageView: ImageView, imageLink: String, placeholder: Int) {
        Glide.with(imageView.context)
            .load(imageLink)
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(placeholder)
            .placeholder(placeholder)
            .dontTransform()
            .into(imageView)
    }

    fun loadOfflineImage(imageView: ImageView, image: Int) {
        Glide.with(imageView.context).load(image).placeholder(image).into(imageView)
    }


    fun byteArrayToBase64(byteArray: ByteArray): String =
        Base64.encodeToString(byteArray, Base64.NO_WRAP)


}