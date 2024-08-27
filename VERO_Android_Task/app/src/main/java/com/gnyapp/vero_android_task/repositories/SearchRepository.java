package com.gnyapp.vero_android_task.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gnyapp.vero_android_task.models.Task;
import com.gnyapp.vero_android_task.network.ApiClient;
import com.gnyapp.vero_android_task.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {

    private ApiService apiService;

    public SearchRepository() {
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
    }

    public LiveData<List<Task>> searchTask(String authorization) {
        MutableLiveData<List<Task>> data = new MutableLiveData<>();
        apiService.getTasks("Bearer " + authorization).enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                data.setValue(response.body());
            }
            @Override
            public void onFailure(Call<List<Task>> call,Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
