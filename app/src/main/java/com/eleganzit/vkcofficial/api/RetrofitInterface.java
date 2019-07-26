package com.eleganzit.vkcofficial.api;



import com.eleganzit.vkcofficial.model.AllArticleResponse;
import com.eleganzit.vkcofficial.model.AllPOResponse;
import com.eleganzit.vkcofficial.model.AllVendorListResponse;
import com.eleganzit.vkcofficial.model.ArticleWiseDefectResponse;
import com.eleganzit.vkcofficial.model.CompletePoNotificationResponse;
import com.eleganzit.vkcofficial.model.LoginResponse;
import com.eleganzit.vkcofficial.model.OTPResponse;
import com.eleganzit.vkcofficial.model.PendingPOResponse;
import com.eleganzit.vkcofficial.model.ReportResponse;
import com.eleganzit.vkcofficial.model.SearchDataResponse;
import com.eleganzit.vkcofficial.model.SearchPOResponse;
import com.eleganzit.vkcofficial.model.TotalVendorDefectResponse;
import com.eleganzit.vkcofficial.model.VendorMessageResponse;
import com.eleganzit.vkcofficial.model.griddata.POGridResponse;
import com.eleganzit.vkcofficial.model.p_grid.PendingPoGridResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by eleganz on 30/4/19.
 */

public interface RetrofitInterface {
    @FormUrlEncoded()
    @POST("/VKC-API/loginOffical")
    Call<LoginResponse> loginOffical(
            @Field("email_id") String email_id,
            @Field("password") String password,
            @Field("device_token") String device_token

    );

    @GET("/Vkc-AdminPanel/sendCode.php")
    Call<OTPResponse> sendCode(
            @Query("email_id") String email_id


    );
 @FormUrlEncoded()
    @POST("/VKC-API/vendorMessage")
    Call<VendorMessageResponse> vendorMessage   (
            @Field("data") String data


    );

 @FormUrlEncoded()
    @POST("/VKC-API/checkCodeOfficial")
    Call<OTPResponse> checkCodeOfficial   (
            @Field("send_code") String send_code,
            @Field("email_id") String email_id


    );@FormUrlEncoded()
    @POST("/VKC-API/resetOfficialPassowrd")
    Call<OTPResponse> resetOfficialPassowrd   (
            @Field("password") String send_code,
            @Field("email_id") String email_id


    );

 @FormUrlEncoded()
    @POST("/VKC-API/pendingPO")
    Call<PendingPOResponse> pendingPO   (
            @Field("vendor_id") String data


    );


 @FormUrlEncoded()
    @POST("/VKC-API/pendingArticle")
    Call<PendingPoGridResponse> pendingPoDetails   (
            @Field("pur_doc_num") String pur_doc_num,
            @Field("article") String article,
            @Field("item") String item


    );@FormUrlEncoded()
    @POST("/VKC-API/pendingArticle")
    Call<PendingPoGridResponse> pendingArticle   (
            @Field("pur_doc_num") String pur_doc_num,
            @Field("article") String article,
            @Field("item") String doc_date


    );


    @FormUrlEncoded()
    @POST("/VKC-API/pendingArticle")
    Call<PendingPoGridResponse> pendingArticle   (
            @Field("pur_doc_num") String pur_doc_num,
            @Field("article") String article


    );

@FormUrlEncoded()
    @POST("/VKC-API/pendingGrid")
    Call<POGridResponse> pendingGrid   (
            @Field("pur_doc_num") String pur_doc_num,
            @Field("article") String article,
            @Field("doc_date") String doc_date


    );@FormUrlEncoded()
    @POST("/VKC-API/pendingGrid")
    Call<POGridResponse> pendingGriditem   (
            @Field("pur_doc_num") String pur_doc_num,
            @Field("article") String article,
            @Field("item") String item


    );


    @FormUrlEncoded
    @POST("/VKC-API/allPoNumber")
    Call<AllPOResponse> allPoNumber   (
            @Field("vendor_id") String vendorid



    );


    @FormUrlEncoded
    @POST("/VKC-API/poAritcleList")
    Call<AllArticleResponse> poAritcleList   (

            @Field("pur_doc_num") String pur_doc_num

    );
@FormUrlEncoded
    @POST("/VKC-API/completePoList")
    Call<SearchPOResponse> completePoList   (

            @Field("pur_doc_num") String pur_doc_num,
            @Field("article") String article,
            @Field("date") String date,
            @Field("vendor_id") String vendor_id,
            @Field("item") String item

    );


@FormUrlEncoded
    @POST("/VKC-API/totalVendorDefect")
    Call<TotalVendorDefectResponse> totalVendorDefect   (

            @Field("article") String article,
            @Field("item") String item

    );
@FormUrlEncoded
    @POST("/VKC-API/articleWiseDefect")
    Call<ArticleWiseDefectResponse> articleWiseDefect   (

            @Field("article") String article,
            @Field("item") String item

    );

@FormUrlEncoded
    @POST("/VKC-API/report")
    Call<ReportResponse> report   (

            @Field("daterange") String daterange,
            @Field("vendor_id") String vendor_id,
            @Field("type") String type,
            @Field("no_of_working_days") String no_of_working_days

    );@FormUrlEncoded
    @POST("/VKC-API/serchVendorList")
    Call<SearchDataResponse> serchVendorList   (

            @Field("search") String search

    );

    @POST("/VKC-API/allVendorList")
    Call<AllVendorListResponse> allVendorList   (


    );


    @POST("/VKC-API/allArticleList")
    Call<AllArticleResponse> allArticleList   (


    );

    @POST("/VKC-API/completePoNotification")
    Call<CompletePoNotificationResponse> completePoNotification   (


    );
}
