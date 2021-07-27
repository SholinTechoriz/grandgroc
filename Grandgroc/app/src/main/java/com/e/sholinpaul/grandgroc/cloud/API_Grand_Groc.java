package com.e.sholinpaul.grandgroc.cloud;

import com.e.sholinpaul.grandgroc.model.Model.LoginModel;
import com.e.sholinpaul.grandgroc.model.Responses.ChangePasswordResponse;
import com.e.sholinpaul.grandgroc.model.Responses.GetTypeListResponse;
import com.e.sholinpaul.grandgroc.model.Responses.LoginResponse;
import com.e.sholinpaul.grandgroc.model.Responses.NewOrderResponse;
import com.e.sholinpaul.grandgroc.model.Responses.OrderListResponse;
import com.e.sholinpaul.grandgroc.model.Responses.ProfileResponse;
import com.e.sholinpaul.grandgroc.model.Responses.UpdateUserProfileResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface API_Grand_Groc {

    @POST("login/delivery")
    Call<LoginResponse> signInWith(@Body LoginModel data);

    @GET("delivary/assignedorders")
    Call<NewOrderResponse> newOrderList(@Query("api_token") String access_token, @Query("device_id") String device_id, @Query("filter_Status") String filter_Status, @Query("page") int page);

    @GET("delivary/profile")
    Call<ProfileResponse> profile(@Query("api_token") String access_token, @Query("device_id") String device_id);

    @Multipart
    @POST("delivaryboys/profile/update")
    Call<UpdateUserProfileResponse> updateProfile(@Part MultipartBody.Part file,
                                                  @PartMap() Map<String, RequestBody> partMap);

    @POST("delivaryboys/change-password")
    Call<ChangePasswordResponse> changePassword(@Body LoginModel data);

    @GET("delivary/assignedordersdetails")
    Call<OrderListResponse> AllOrderList(@Query("device_id") String device_id, @Query("api_token") String api_token, @Query("id") int id, @Query("Order_id") int Order_id);

    @GET("delivary/delivarytypes/list")
    Call<GetTypeListResponse> AllOrderTypes(@Query("device_id") String device_id, @Query("api_token") String api_token);
}
