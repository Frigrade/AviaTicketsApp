package com.example.utils

import android.content.Context
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


object FileUtil {

    fun createAndWriteToCache(
        context: Context,
        fileName: String,
        data: String,
        isSuccess: (Boolean) -> (Unit)
    ) {

        val cacheDir: File = context.cacheDir
        val myFile = File(cacheDir, fileName)

        try {
            BufferedOutputStream(FileOutputStream(myFile)).use { bos ->
                bos.write(data.toByteArray())
                isSuccess(true)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            isSuccess(false)
        }
    }

    fun readFromCache(context: Context, fileName: String): String {

        val cacheDir: File = context.cacheDir
        val myFile = File(cacheDir, fileName)

        val stringBuilder = StringBuilder()

        try {
            BufferedInputStream(FileInputStream(myFile)).use { bis ->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (bis.read(buffer).also { bytesRead = it } != -1) {
                    stringBuilder.append(String(buffer, 0, bytesRead))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }

}
