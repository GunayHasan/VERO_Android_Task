package com.gnyapp.vero_android_task.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gnyapp.vero_android_task.R;
import com.gnyapp.vero_android_task.adapters.TaskAdapter;
import com.gnyapp.vero_android_task.databinding.ActivityMainBinding;
import com.gnyapp.vero_android_task.models.Task;
import com.gnyapp.vero_android_task.network.ApiClient;
import com.gnyapp.vero_android_task.network.ApiService;
import com.gnyapp.vero_android_task.request.LoginRequest;
import com.gnyapp.vero_android_task.response.LoginResponse;
import com.gnyapp.vero_android_task.response.TaskResponse;
import com.gnyapp.vero_android_task.viewmodels.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TaskViewModel taskViewModel;
    private ActivityMainBinding binding;
    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private RecyclerView tasksRecyclerView;
    LoginRequest loginRequest;
    String authHeader = "Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz";
    String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("access_token", accessToken);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_qr) {
            Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
            intent.putExtra("access_token", accessToken);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        binding.tasksRecyclerView.setHasFixedSize(true);
        taskAdapter = new TaskAdapter(taskList);
        binding.tasksRecyclerView.setAdapter(taskAdapter);
        loginRequest = new LoginRequest("365", "1");
        authenticateAndFetchTasks();

        binding.swipeRefresh.setOnRefreshListener(() -> {
            taskList.clear();
            authenticateAndFetchTasks();
        });

    }


    private void authenticateAndFetchTasks() {
        binding.setIsLoading(true);
        taskViewModel.login(authHeader, loginRequest).observe(this, loginResponse ->
        {
            if(loginResponse != null) {
                binding.setIsLoading(false);
                accessToken = loginResponse.getOauth().getAccessToken();
                Log.e(TAG, "Access Token->: " + accessToken);
                fetchTasks(accessToken);
            }
            else {
                binding.setIsLoading(false);
                Toast.makeText(this, "Can't find access token!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchTasks(String accessToken) {
        binding.setIsLoading(true);
        taskViewModel.getAllTasks(accessToken).observe(this, getTasks ->
        {
            if(getTasks != null) {
                binding.setIsLoading(false);
                taskList.addAll(getTasks);
                taskAdapter.notifyDataSetChanged();
                binding.swipeRefresh.setRefreshing(false);
            }
            else {
                binding.setIsLoading(false);
                Toast.makeText(this, "Something gone wrong!!", Toast.LENGTH_SHORT).show();
                binding.swipeRefresh.setRefreshing(false);
            }
        });
    }

}