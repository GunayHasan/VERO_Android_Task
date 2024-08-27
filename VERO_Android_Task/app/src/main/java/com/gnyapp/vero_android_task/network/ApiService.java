package com.gnyapp.vero_android_task.network;

import com.gnyapp.vero_android_task.models.Task;
import com.gnyapp.vero_android_task.request.LoginRequest;
import com.gnyapp.vero_android_task.response.LoginResponse;
import com.gnyapp.vero_android_task.response.TaskResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/index.php/login")
    Call<LoginResponse> login(
            @Header("Authorization") String authorization,
            @Body LoginRequest loginRequest
    );

    @GET("/dev/index.php/v1/tasks/select")
    Call<List<Task>> getTasks(@Header("Authorization") String authorization);
}
