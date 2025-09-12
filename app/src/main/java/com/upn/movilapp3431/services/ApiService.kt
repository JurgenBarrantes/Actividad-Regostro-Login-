package com.upn.movilapp3431.services

import com.upn.movilapp3431.entities.Contact
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    // GET -> https://68b5a32ae5dc090291afbd0d.mockapi.io/contacts?name=Juan
    @GET("/contacts")
    suspend fun getContacts(): List<Contact>

//    // GET -> https://68b5a32ae5dc090291afbd0d.mockapi.io/contacts/{id}
//    @GET("/contacts/{id}")
//    suspend fun findContactById(@Path("id") id: Int): Contact
//
//    // POST -> https://68b5a32ae5dc090291afbd0d.mockapi.io/contacts
//    @POST("/contacts")
//    suspend fun createContact(@Body contact: Contact): Contact
//
//    // PUT -> https://68b5a32ae5dc090291afbd0d.mockapi.io/contacts/{id}
//    @PUT("/contacts/{id}")
//    suspend fun updateContact(@Path("id") id: Int, @Body contact: Contact): Contact
//
//    // DELETE -> https://68b5a32ae5dc090291afbd0d.mockapi.io/contacts/{id}
//    @DELETE("/contacts/{id}")
//    suspend fun deleteContact(@Path("id") id: Int): Void
}