package com.baichang.android.kotlin.common.http

import com.google.gson.Gson
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
/**
 * Created by iCong on 2017/6/29.
 */
class HttpResultConverter_ : Converter.Factory() {

  override fun responseBodyConverter(
    type: Type?,
    annotations: Array<out Annotation>?,
    retrofit: Retrofit?
  ): Converter<ResponseBody, *>? {
    return ResponseBodyConverter<Any>(type)
  }

  override fun requestBodyConverter(
    type: Type?,
    parameterAnnotations: Array<out Annotation>?,
    methodAnnotations: Array<out Annotation>?,
    retrofit: Retrofit?
  ): Converter<*, RequestBody>? {
    return GsonConverterFactory.create()
        .requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
  }

  class ResponseBodyConverter<T>(private val type: Type?) : Converter<ResponseBody, T> {

    private val SERVICE_ERROR = "请求服务器异常"
    private val DATA_ERROR = "请求数据异常"
    private val TOKEN_ERROR = "token 失效"
    private val RESULT_SUCCESS = 4_0000
    private val RESULT_ERROR_TOKEN = 3_0000
    private val gson = Gson()

    override fun convert(responseBody: ResponseBody?): T {
      responseBody ?: throw HttpError(SERVICE_ERROR)

      val response = responseBody.string() ?: throw HttpError(DATA_ERROR)
      val responseData = gson.fromJson(response, HttpResponse_::class.java)

      when (responseData.state) {
        1    -> {
          val data = responseData.data ?: throw HttpError(SERVICE_ERROR)
          when (data.code) {
            RESULT_ERROR_TOKEN -> throw HttpError(TOKEN_ERROR)
            RESULT_SUCCESS     -> return gson.fromJson(gson.toJson(data.data), type)
            else               -> throw HttpError(data.msg)
          }
        }
        else -> throw HttpError(SERVICE_ERROR)
      }
    }

  }
}
