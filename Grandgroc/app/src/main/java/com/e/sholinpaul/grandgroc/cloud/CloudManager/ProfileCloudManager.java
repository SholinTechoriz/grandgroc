package com.e.sholinpaul.grandgroc.cloud.CloudManager;

import android.content.Context;

import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.ChangePasswordListener;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.GetProfileListener;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.UpdateProfileListener;
import com.e.sholinpaul.grandgroc.model.Model.LoginModel;
import com.e.sholinpaul.grandgroc.model.Responses.ChangePasswordResponse;
import com.e.sholinpaul.grandgroc.model.Responses.ProfileResponse;
import com.e.sholinpaul.grandgroc.model.Responses.UpdateUserProfileResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileCloudManager extends BaseCloudManger {

    public ProfileCloudManager(Context mContext) {
        super(mContext);
    }


    public void fetchProfile(String access_token, String device_id, final GetProfileListener listener) {
        if (!checkConnection(listener)) {
            return;
        }
        Call<ProfileResponse> call = api.profile(access_token, device_id);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                ProfileResponse profileResponse = response.body();

                if (profileResponse != null) {
                    if (profileResponse.success == "true") {
                        if (listener != null) {
                            listener.fetchProfileDetails(profileResponse.delivaryboy);
                        }
                    } else {
                        if (listener != null) {
                            listener.onCompleted();
                            listener.fetchProfileDetailsFailed(profileResponse.success);
                        }
                    }
                    if (listener != null) {
                        listener.onCompleted();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                if (listener != null) {
                    listener.onCompleted();
                    listener.fetchProfileDetailsFailed(t.getMessage());
                }
            }
        });

    }

    public void updateProfileMulti(MultipartBody.Part fileToUpload, Map<String, RequestBody> partMap, final UpdateProfileListener listener) {
        if (!checkConnection(listener)) {
            return;
        }
        Call<UpdateUserProfileResponse> call = api.updateProfile(fileToUpload, partMap);
        call.enqueue(new Callback<UpdateUserProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateUserProfileResponse> call, Response<UpdateUserProfileResponse> response) {

                UpdateUserProfileResponse userProfileResponse = response.body();


                if (userProfileResponse != null) {
                    if (userProfileResponse.success == "true") {
//                        LoginSPManager loyaltyMessagesDBManager = new LoginSPManager(mContext);
//                        loyaltyMessagesDBManager.saveLoginDetailsToSP(userProfileResponse.delivaryboy);
                        if (listener != null) {
                            listener.updateProfileDetails(userProfileResponse.delivaryboy, userProfileResponse.msg);
                        }
                    } else {
                        if (listener != null) {
                            listener.onCompleted();
                            listener.updateProfileDetailsFailed(userProfileResponse.success, userProfileResponse.msg);
                        }
                    }
                    if (listener != null) {
                        listener.onCompleted();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateUserProfileResponse> call, Throwable t) {

                if (listener != null) {
                    listener.onCompleted();
                    listener.updateProfileDetailsFailed("Failed", t.getMessage());
                }
            }
        });

//        if (listener != null) {
//            listener.onStarted();
//        }


    }

    public void changePassword(final LoginModel data, final ChangePasswordListener listener) {
        if (!checkConnection(listener)) {
            return;
        }
        Call<ChangePasswordResponse> call = api.changePassword(data);
        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {

                ChangePasswordResponse changePasswordResponse = response.body();


                if (changePasswordResponse != null) {
                    if (changePasswordResponse.success == "true") {

                        if (listener != null) {
                            listener.ChangePassword(changePasswordResponse.data, changePasswordResponse.msg);
                        }
                    } else {
                        if (listener != null) {
                            listener.onCompleted();
                            listener.ChangePasswordFailed(changePasswordResponse.success, changePasswordResponse.errorMsg);
                        }
                    }
                    if (listener != null) {
                        listener.onCompleted();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {

                if (listener != null) {
                    listener.onCompleted();
                    listener.ChangePasswordFailed("Failed", t.getMessage());
                }
            }
        });

//        if (listener != null) {
//            listener.onStarted();
//        }


    }

}
