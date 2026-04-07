package com.xihad.androidutils.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object BitmapUtil {

    /** Resizes a bitmap to [width] x [height]. */
    fun Bitmap.resize(width: Int, height: Int): Bitmap =
        Bitmap.createScaledBitmap(this, width, height, true)

    /** Scales a bitmap keeping aspect ratio so the longest side equals [maxSize]. */
    fun Bitmap.scaleToFit(maxSize: Int): Bitmap {
        val ratio = maxSize.toFloat() / maxOf(this.width, this.height)
        return resize((this.width * ratio).toInt(), (this.height * ratio).toInt())
    }

    /** Rotates a bitmap by [degrees]. */
    fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

    /** Flips a bitmap horizontally or vertically. */
    fun Bitmap.flip(horizontal: Boolean = true, vertical: Boolean = false): Bitmap {
        val matrix = Matrix().apply {
            postScale(
                if (horizontal) -1f else 1f,
                if (vertical) -1f else 1f,
                width / 2f, height / 2f
            )
        }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

    /** Crops a bitmap to a circle. */
    fun Bitmap.toCircle(): Bitmap {
        val size = minOf(width, height)
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(this, Rect(0, 0, width, height), Rect(0, 0, size, size), paint)
        return output
    }

    /** Crops a bitmap to a rounded rectangle with [radius] corner radius. */
    fun Bitmap.toRoundedCorners(radius: Float): Bitmap {
        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), radius, radius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(this, 0f, 0f, paint)
        return output
    }

    /** Compresses a bitmap to a JPEG byte array at [quality] (0–100). */
    fun Bitmap.toJpegByteArray(quality: Int = 80): ByteArray =
        ByteArrayOutputStream().also { compress(Bitmap.CompressFormat.JPEG, quality, it) }.toByteArray()

    /** Compresses a bitmap to a PNG byte array. */
    fun Bitmap.toPngByteArray(): ByteArray =
        ByteArrayOutputStream().also { compress(Bitmap.CompressFormat.PNG, 100, it) }.toByteArray()

    /** Converts a byte array back to a Bitmap. */
    fun ByteArray.toBitmap(): Bitmap = BitmapFactory.decodeByteArray(this, 0, size)

    /** Captures a [View] as a Bitmap. */
    fun View.toBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }

    /**
     * Saves a bitmap to the device gallery (Pictures folder).
     * Returns the URI of the saved image, or null on failure.
     */
    fun Bitmap.saveToGallery(
        context: Context,
        fileName: String = "img_${System.currentTimeMillis()}",
        quality: Int = 90
    ): Uri? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.jpg")
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                uri?.let { context.contentResolver.openOutputStream(it)?.use { os -> compress(Bitmap.CompressFormat.JPEG, quality, os) } }
                uri
            } else {
                val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                dir.mkdirs()
                val file = File(dir, "$fileName.jpg")
                FileOutputStream(file).use { compress(Bitmap.CompressFormat.JPEG, quality, it) }
                Uri.fromFile(file)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /** Saves a bitmap to the app's internal cache directory. Returns the File. */
    fun Bitmap.saveToCache(context: Context, fileName: String = "img_${System.currentTimeMillis()}.jpg"): File {
        val file = File(context.cacheDir, fileName)
        FileOutputStream(file).use { compress(Bitmap.CompressFormat.JPEG, 90, it) }
        return file
    }

    /** Returns the file size of the compressed bitmap in bytes. */
    fun Bitmap.fileSize(quality: Int = 90): Int = toJpegByteArray(quality).size
}
