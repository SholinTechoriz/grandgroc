package com.e.sholinpaul.grandgroc.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.e.sholinpaul.grandgroc.R;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.ChangePasswordListener;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.GetProfileListener;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.UpdateProfileListener;
import com.e.sholinpaul.grandgroc.cloud.CloudManager.ProfileCloudManager;
import com.e.sholinpaul.grandgroc.cloud.SPManager.LoginSPManager;
import com.e.sholinpaul.grandgroc.databinding.CustomChangepasswordLayoutBinding;
import com.e.sholinpaul.grandgroc.databinding.FragmentUserProfileBinding;
import com.e.sholinpaul.grandgroc.model.Model.LoginModel;
import com.e.sholinpaul.grandgroc.utils.BusinessDetailsGenerator;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class UserProfileFragment extends Fragment implements GetProfileListener, UpdateProfileListener, ChangePasswordListener {

    View view;
    private FragmentUserProfileBinding binding;
    String enOldPassword, enNewPassword, enCPassword, deviceId, accessToken, SPhone, SName, SEmail;
    LoginModel model;
    private String sImagePath = "";
    private static final int ACT_FINISH_CODE = 302;
    private static final int SELECT_PROFILE_PIC_REQUEST_CODE = 303;
    float BUTTON_ALPHA_VALUE_ENABLE = 1f;
    float BUTTON_ALPHA_VALUE_DISABLE = 0.6f;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        init();
        return view;
    }

    private void init() {


        fetchProfile();
        binding.fabProfile.setOnClickListener(view -> {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        SELECT_PROFILE_PIC_REQUEST_CODE);
            } else {
                showImageAlert();
            }


        });

        binding.btnUpdate.setOnClickListener(v -> {
            if (binding.btnUpdate.getText().equals("Edit")) {
                setDataToEdit(model);

            } else {
                validateAndUpdateDataToServer();
            }
        });

        binding.tvChangePassword.setOnClickListener(v -> {
            accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
            Log.d("ACCESS12", "access token is.." + accessToken);
            deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();
            showChangePassword(accessToken, deviceId);
        });


    }

    private void validateAndUpdateDataToServer() {
        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();

        SEmail = binding.edEmail.getText().toString();
        SName = binding.edUsername.getText().toString();
        SPhone = binding.edPhone.getText().toString();
        if (SName.isEmpty()) {
            binding.edUsername.setError("Name is empty");
            binding.edUsername.requestFocus();
            return;
        }
        if (SEmail.isEmpty()) {
            binding.edEmail.setError("Email is empty");
            binding.edEmail.requestFocus();
            return;
        }

        if (SPhone.isEmpty()) {
            binding.edPhone.setError("Phone number is empty");
            binding.edPhone.requestFocus();
            return;

        } else if (SPhone.length() < 10) {
            binding.edPhone.setError("Please enter valid Phone number");
            binding.edPhone.requestFocus();
            return;
        }


        //ProfilePic Body Creation
        File file;
        MultipartBody.Part profilePicUpload;

        if (sImagePath.equals("")) {
            RequestBody image = RequestBody.create(MediaType.parse("image/*"), "");

            profilePicUpload = MultipartBody.Part.createFormData("profile_pic", "", image);
        } else {
            file = new File(sImagePath);
            Log.d("qwerty", "file path is.1 :" + file);
            if (isValidUrl(sImagePath)) {
                RequestBody image = RequestBody.create(MediaType.parse("image/*"), sImagePath);
                profilePicUpload = MultipartBody.Part.createFormData("profile_pic", sImagePath, image);
                Log.d("qwerty", "file prof pic is.2 :" + sImagePath);
            } else {
                profilePicUpload = prepareFilePart("profile_pic", sImagePath);
                Log.d("qwerty", "file prof pic is.3 :" + sImagePath);

            }
        }


        RequestBody accessToken1 = RequestBody.create(MediaType.parse("text/plain"), accessToken);

        Log.d("qwerty", "api+token iss.  :" + accessToken);
        RequestBody deviceId1 = RequestBody.create(MediaType.parse("text/plain"), deviceId);
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), SName);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), SEmail);
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), SPhone);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("api_token", accessToken1);
        map.put("device_id", deviceId1);
        map.put("name", username);
        map.put("email", email);
        map.put("phone", phone);
        ProfileCloudManager profileCloudManager = new ProfileCloudManager(getActivity());
        profileCloudManager.updateProfileMulti(profilePicUpload, map, this);

        binding.btnUpdate.setAlpha(BUTTON_ALPHA_VALUE_DISABLE);

        for (int i = 0; i < binding.rlMain.getChildCount(); i++) {
            View view = binding.rlMain.getChildAt(i);
            enableDisableView(view, false);
        }

        binding.lLoading.setVisibility(View.VISIBLE);

    }

    private void setDataToTextview(LoginModel data) {
        binding.btnUpdate.setText("Edit");
        binding.fabProfile.setVisibility(View.GONE);
        binding.tvChangePassword.setVisibility(View.GONE);


        String realpath = ("http://projects.techoriz.in/grandgroc/public/assets/uploads/delivaryboys/" + data.getProfile_pic());
        Log.d("asdfgh", "file prof pic is.2 :" + realpath);
        Glide.with(this)
                .load(realpath)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_loading).error(R.drawable.ic_profile)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(binding.ivEmpImg);

        binding.mainUName.setText(data.getName());
        binding.mainULocation.setText(data.getLocation());

        binding.edUsername.setEnabled(false);
        binding.edUsername.setClickable(false);
        binding.edLocation.setEnabled(false);
        binding.edLocation.setClickable(false);
        binding.edEmail.setClickable(false);
        binding.edEmail.setEnabled(false);
        binding.edPhone.setClickable(false);
        binding.edPhone.setEnabled(false);
        binding.edDate.setClickable(false);
        binding.edDate.setEnabled(false);
        binding.edEmpCode.setClickable(false);
        binding.edEmpCode.setEnabled(false);


        binding.edUsername.setText(data.getName());
        binding.edLocation.setText(data.getLocation());
        binding.edEmail.setText(data.getEmail());
        binding.edPhone.setText(data.getPhone());
        binding.edDate.setText(data.getCreated_at());
        binding.edEmpCode.setText(data.getEmployee_code());
    }

    private void setDataToEdit(LoginModel model) {
        binding.btnUpdate.setText("Save");
        binding.fabProfile.setVisibility(View.VISIBLE);
        binding.tvChangePassword.setVisibility(View.VISIBLE);

        String realpath = ("http://projects.techoriz.in/grandgroc/public/assets/uploads/delivaryboys/" + model.getProfile_pic());
        Log.d("asdfgh", "file prof pic is.2 :" + realpath);
        Glide.with(this)
                .load(realpath)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_loading).error(R.drawable.ic_profile)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(binding.ivEmpImg);

        binding.mainUName.setText(model.getName());
        binding.mainULocation.setText(model.getLocation());

        binding.edUsername.setEnabled(true);
        binding.edUsername.setClickable(true);
        binding.edEmail.setClickable(true);
        binding.edEmail.setEnabled(true);
        binding.edPhone.setClickable(true);
        binding.edPhone.setEnabled(true);
        binding.edLocation.setEnabled(false);
        binding.edLocation.setClickable(false);
        binding.edDate.setClickable(false);
        binding.edDate.setEnabled(false);
        binding.edEmpCode.setClickable(false);
        binding.edEmpCode.setEnabled(false);


        binding.edUsername.setText(model.getName());
        binding.edLocation.setText(model.getLocation());
        binding.edEmail.setText(model.getEmail());
        binding.edPhone.setText(model.getPhone());
        binding.edDate.setText(model.getCreated_at());
        binding.edEmpCode.setText(model.getEmployee_code());
    }


    private void fetchProfile() {
        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        Log.d("ACCESS", "access token is.." + accessToken);
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();
        ProfileCloudManager profileCloudManager = new ProfileCloudManager(getActivity());
        profileCloudManager.fetchProfile(accessToken, deviceId, this);

        binding.lLoading.setVisibility(View.VISIBLE);
    }

    public void showChangePassword(String Access_token1, String device_id1) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final CustomChangepasswordLayoutBinding layoutBinding = CustomChangepasswordLayoutBinding.inflate(getLayoutInflater());
        View view = layoutBinding.getRoot();
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        assert dialog.getWindow() != null;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 20);
        dialog.getWindow().setBackgroundDrawable(inset);
        dialog.show();


        layoutBinding.edCPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                enOldPassword = layoutBinding.edOldpassword.getText().toString();
                enNewPassword = layoutBinding.edNewpassword.getText().toString();
                enCPassword = layoutBinding.edCPassword.getText().toString();
                if (enNewPassword.equals(enCPassword)) {
                    layoutBinding.tvError.setTextColor(Color.GREEN);
                    layoutBinding.tvError.setText("Password Matching");

                } else {
                    layoutBinding.tvError.setTextColor(Color.RED);
                    layoutBinding.tvError.setText("Password Not Matching");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        layoutBinding.btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChangePassword(Access_token1, device_id1);
                dialog.dismiss();
            }
        });

    }

    private void saveChangePassword(String Access_token1, String device_id1) {

        LoginModel loginModel = new LoginModel();
        loginModel.setApi_token(Access_token1);

        loginModel.setDevice_id(device_id1);
        loginModel.setCurrent_password(enOldPassword);
        loginModel.setNew_password(enNewPassword);
        loginModel.setNew_confirm_password(enCPassword);

        ProfileCloudManager profileCloudManager = new ProfileCloudManager(getActivity());
        profileCloudManager.changePassword(loginModel, this);
        binding.lLoading.setVisibility(View.VISIBLE);
    }


    private void showImageAlert() {

        ImagePicker.Companion.with(this)
                .crop()
                .compress(800)        //in KB
                .maxResultSize(280, 280)
                .start(SELECT_PROFILE_PIC_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PROFILE_PIC_REQUEST_CODE) {//Profile Pic


            if (resultCode == Activity.RESULT_OK) {

//                sImagePath = ImagePicker.Companion.getFilePath(data);
                sImagePath = data.getData().getPath();
                Log.d("qwertyyy", "image path iss..:" + sImagePath);


                Glide.with(this)
                        .load(sImagePath)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.ic_loading).error(R.drawable.ic_profile)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true))
                        .into(binding.ivEmpImg);

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Log.d("qwertyyy", "rslt code 1 is..:" + resultCode);

                Toast.makeText(getActivity(), ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
        if (resultCode == ACT_FINISH_CODE) {
            getActivity().finish();
        }
    }


    private MultipartBody.Part prepareFilePart(String partName, String fileUri) {

        File file = new File(fileUri);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);

        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }


    private boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void fetchProfileDetails(LoginModel delivaryboy) {
        model = delivaryboy;
        setDataToEdit(model);

        setDataToTextview(model);

        binding.lLoading.setVisibility(View.GONE);

    }

    @Override
    public void fetchProfileDetailsFailed(String errorMessage) {

        binding.lLoading.setVisibility(View.GONE);

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onConnectionFailure(int errorCode) {

    }

    @Override
    public void updateProfileDetails(LoginModel delivaryboy, String message) {
        fetchProfile();
//        LoginSPManager loyaltyMessagesDBManager = new LoginSPManager(getActivity());
//        loyaltyMessagesDBManager.saveLoginDetailsToSP(delivaryboy);
        Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
        binding.btnUpdate.setAlpha(BUTTON_ALPHA_VALUE_ENABLE);

        for (int i = 0; i < binding.rlMain.getChildCount(); i++) {
            View view = binding.rlMain.getChildAt(i);
            enableDisableView(view, true);
        }
        binding.lLoading.setVisibility(View.GONE);


    }

    @Override
    public void updateProfileDetailsFailed(String errorMsg, String message) {
        binding.btnUpdate.setAlpha(BUTTON_ALPHA_VALUE_ENABLE);

        for (int i = 0; i < binding.rlMain.getChildCount(); i++) {
            View view = binding.rlMain.getChildAt(i);
            enableDisableView(view, true);
        }
        binding.lLoading.setVisibility(View.GONE);

    }

    @Override
    public void ChangePassword(LoginModel data, String message) {

        Toast.makeText(getActivity(), "Password changed Successfully", Toast.LENGTH_SHORT).show();

        fetchProfile();
        binding.lLoading.setVisibility(View.GONE);
    }

    @Override
    public void ChangePasswordFailed(String errorMsg, String errorMessage) {
        binding.lLoading.setVisibility(View.GONE);
    }

    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }
}

