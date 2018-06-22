package com.baichang.android.kotlin.common.http

import android.text.TextUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part
import okhttp3.RequestBody
import java.io.File

/**
 * Created by iCong on 06/01/2018.
 */
object UploadConvert {
  private const val FILE_NOT_NULL = "文件不能为空"
  private const val FILE_PATH_NOT_NULL = "文件路径不能为空"

  fun getMultipartBody(path: String): MultipartBody.Part {
    if (TextUtils.isEmpty(path)) throw NullPointerException(FILE_PATH_NOT_NULL)
    val file = File(path)
    if (file.exists()) {
      val requestFile = RequestBody.create(MediaType.parse("application/octet-stream"), file)
      return Part.createFormData("file", file.name, requestFile)
    } else {
      throw NullPointerException(FILE_NOT_NULL)
    }
  }

  fun getMultipartBody(file: File): MultipartBody.Part {
    if (file.exists()) {
      val requestFile = RequestBody.create(MediaType.parse("application/octet-stream"), file)
      return Part.createFormData("file", file.name, requestFile)
    } else {
      throw NullPointerException(FILE_NOT_NULL)
    }
  }

  fun getMultipartBodysForFile(files: List<File>): List<MultipartBody.Part> {
    if (files.isEmpty()) throw NullPointerException(FILE_NOT_NULL)
    val builder = MultipartBody.Builder()
    for (file in files) {
      if (file.exists()) {
        val requestFile = RequestBody.create(
            MediaType.parse("application/octet-stream"),
            file
        )
        builder.addFormDataPart("file", file.name, requestFile)
      } else {
        throw NullPointerException(FILE_NOT_NULL)
      }
    }
    return builder.build()
        .parts()
  }

  fun getMultipartBodysForPath(paths: List<String>): List<MultipartBody.Part> {
    if (paths.isEmpty()) throw NullPointerException(FILE_PATH_NOT_NULL)
    val builder = MultipartBody.Builder()
    paths.map { File(it) }
        .forEach {
          if (it.exists()) {
            val requestFile = RequestBody.create(
                MediaType.parse("application/octet-stream"),
                it
            )
            builder.addFormDataPart("file", it.name, requestFile)
          } else {
            throw NullPointerException(FILE_NOT_NULL)
          }
        }
    return builder.build()
        .parts()
  }
}