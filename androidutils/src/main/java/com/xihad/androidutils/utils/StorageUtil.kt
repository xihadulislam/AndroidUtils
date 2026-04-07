package com.xihad.androidutils.utils

import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import java.io.File

object StorageUtil {

    // ── Internal Storage ──────────────────────────────────────────────────────

    /** Returns total internal storage in bytes. */
    fun getTotalInternalStorage(): Long {
        val stat = StatFs(Environment.getDataDirectory().path)
        return stat.blockCountLong * stat.blockSizeLong
    }

    /** Returns free internal storage in bytes. */
    fun getFreeInternalStorage(): Long {
        val stat = StatFs(Environment.getDataDirectory().path)
        return stat.availableBlocksLong * stat.blockSizeLong
    }

    /** Returns used internal storage in bytes. */
    fun getUsedInternalStorage(): Long =
        getTotalInternalStorage() - getFreeInternalStorage()

    // ── External Storage ──────────────────────────────────────────────────────

    /** Returns true if external storage (SD card) is available and writable. */
    fun isExternalStorageAvailable(): Boolean =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    /** Returns total external storage in bytes, or -1 if not available. */
    fun getTotalExternalStorage(): Long {
        if (!isExternalStorageAvailable()) return -1L
        val stat = StatFs(Environment.getExternalStorageDirectory().path)
        return stat.blockCountLong * stat.blockSizeLong
    }

    /** Returns free external storage in bytes, or -1 if not available. */
    fun getFreeExternalStorage(): Long {
        if (!isExternalStorageAvailable()) return -1L
        val stat = StatFs(Environment.getExternalStorageDirectory().path)
        return stat.availableBlocksLong * stat.blockSizeLong
    }

    // ── App Cache ─────────────────────────────────────────────────────────────

    /** Returns the total size of the app's cache directory in bytes. */
    fun getCacheSize(context: Context): Long =
        getDirSize(context.cacheDir) + (context.externalCacheDir?.let { getDirSize(it) } ?: 0L)

    /** Clears the app's internal and external cache directories. */
    fun clearCache(context: Context) {
        deleteDir(context.cacheDir)
        context.externalCacheDir?.let { deleteDir(it) }
    }

    /** Returns the size of the app's files directory in bytes. */
    fun getFilesSize(context: Context): Long = getDirSize(context.filesDir)

    // ── Formatting ────────────────────────────────────────────────────────────

    /** Formats a byte count to a human-readable string (e.g. "1.2 GB"). */
    fun formatSize(bytes: Long): String {
        if (bytes < 0) return "N/A"
        if (bytes < 1024) return "$bytes B"
        val kb = bytes / 1024.0
        if (kb < 1024) return "%.1f KB".format(kb)
        val mb = kb / 1024.0
        if (mb < 1024) return "%.1f MB".format(mb)
        val gb = mb / 1024.0
        return "%.2f GB".format(gb)
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun getDirSize(dir: File): Long {
        if (!dir.exists()) return 0L
        var size = 0L
        dir.walkTopDown().forEach { file ->
            if (file.isFile) size += file.length()
        }
        return size
    }

    private fun deleteDir(dir: File) {
        if (dir.exists() && dir.isDirectory) {
            dir.listFiles()?.forEach { it.deleteRecursively() }
        }
    }
}
