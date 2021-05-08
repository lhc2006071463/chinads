package com.tiens.comonlibrary.request

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @author: lhc
 * @date: 2020-03-19 21:48
 * @description ApiService 管理类
 */
interface ApiService {
    @POST
    @FormUrlEncoded
    suspend fun post(@Url url: String, @FieldMap maps: Map<String, Any>): Response<ResponseBody>

    @POST
    suspend fun post(@Url url: String): Response<ResponseBody>

    @POST
    suspend fun postBody(@Url url: String, @Body obj: Any): Response<ResponseBody>

    @POST
    suspend fun postBody(@Url url: String, @Body body: RequestBody): Response<ResponseBody>

    @POST
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun postJson(
        @Url url: String,
        @Body jsonBody: RequestBody
    ): Response<ResponseBody>

    // GET请求
    //无参
    @GET
    suspend fun get(@Url url: String): Response<ResponseBody>

    //有参
    @GET
    suspend fun get(
        @Url url: String,
        @QueryMap maps: Map<String, @JvmSuppressWildcards Any>
    ): Response<ResponseBody>

    //DELETE请求
    @DELETE
    suspend fun delete(
        @Url url: String,
        @QueryMap maps: Map<String, Any>
    ): Response<ResponseBody>

    @DELETE
    suspend fun delete(@Url url: String): Response<ResponseBody>

    @DELETE
    suspend fun deleteBody(
        @Url url: String,
        @Body obj: Any
    ): Response<ResponseBody>

    @DELETE
    suspend fun deleteBody(
        @Url url: String,
        @Body body: RequestBody
    ): Response<ResponseBody>

    @HTTP(method = "DELETE", hasBody = true)
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun deleteJson(
        @Url url: String,
        @Body jsonBody: RequestBody
    ): Response<ResponseBody>

    // PUT请求
    @PUT
    suspend fun put(
        @Url url: String,
        @QueryMap maps: Map<String, Any>
    ): Response<ResponseBody>

    @PUT
    suspend fun putBody(
        @Url url: String,
        @Body obj: Any
    ): Response<ResponseBody>

    @PUT
    suspend fun putBody(
        @Url url: String,
        @Body body: RequestBody
    ): Response<ResponseBody>

    @PUT
    @Headers("Content-Type: application/json", "Accept: application/json")
    suspend fun putJson(
        @Url url: String,
        @Body jsonBody: RequestBody
    ): Response<ResponseBody>

    //文件上传下载
    @Multipart
    @POST
    suspend fun uploadFiles(
        @Url url: String,
        @PartMap maps: Map<String, RequestBody>?
    ): Response<ResponseBody>

    @Multipart
    @POST
    suspend fun uploadFiles(
        @Url url: String,
        @Part part: MultipartBody.Part?
    ): Response<ResponseBody>

    @Multipart
    @POST
    suspend fun uploadFiles(
        @Url url: String,
        @Part("strategy") description: RequestBody,
        @Part partList: List<MultipartBody.Part?>?
    ): Response<ResponseBody>

    @Multipart
    @POST
    suspend fun uploadFiles(
        @Url url: String,
        @Part("strategy") description: RequestBody,
        @Part("hash_value") hashValue: RequestBody,
        @Part partList: List<MultipartBody.Part?>?
    ): Response<ResponseBody>

    @Multipart
    @POST
    suspend fun uploadFiles(
        @Url url: String,
        @Part("strategy") description: RequestBody,
        @Part part: MultipartBody.Part?
    ): Response<ResponseBody>

    @Multipart
    @POST
    suspend fun uploadFiles(
        @Url url: String,
        @Part("strategy") description: RequestBody,
        @Part("hash_value") hashValue: RequestBody,
        @Part part: MultipartBody.Part?
    ): Response<ResponseBody>

    @Multipart
    @POST
    suspend fun uploadHeadIcon(
        @Url url: String,
        @Part("folder") description: RequestBody,
        @Part("hash_value") hashValue: RequestBody,
        @Part part: MultipartBody.Part?
    ): Response<ResponseBody>

    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String): Response<ResponseBody>
}