package com.gnyapp.vero_android_task.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.gnyapp.vero_android_task.R;
import com.gnyapp.vero_android_task.adapters.TaskAdapter;
import com.gnyapp.vero_android_task.databinding.ActivitySearchBinding;
import com.gnyapp.vero_android_task.models.Task;
import com.gnyapp.vero_android_task.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;
    private List<Task> tasks = new ArrayList<>();
    private List<Task> filteredList = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private Timer timer;
    String scannedText = "";
    String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        doInitialization();
    }

    private void doInitialization() {
        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        binding.tasksRecylerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        taskAdapter = new TaskAdapter(filteredList);
        binding.tasksRecylerView.setAdapter(taskAdapter);
        Intent intent = getIntent();
        accessToken = intent.getStringExtra("access_token");
        scannedText = intent.getStringExtra("scanned_text");
        if(scannedText != null) {
            if(!scannedText.equalsIgnoreCase("")) {
                binding.inputSearch.setText(scannedText);
                searchTask(scannedText, accessToken);
            }
        }

        binding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().trim().isEmpty()) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                        searchTask(editable.toString(), accessToken);
                                }
                            });
                        }
                    }, 800);
                }else {
                    tasks.clear();
                    taskAdapter.notifyDataSetChanged();
                }
            }
        });
        binding.inputSearch.requestFocus();
    }

    private void searchTask(String query, String accessToken) {
        binding.setIsLoading(true);
        viewModel.searchTask(accessToken).observe(this, taskResponse -> {
            binding.setIsLoading(false);
            if(taskResponse != null) {
                tasks.addAll(taskResponse);
                filter(query, tasks);
            }
        });
    }

    private void filter(String query, List<Task> itemList) {
        String lowerCaseQuery = query.toLowerCase();
        filteredList.clear();
        if (lowerCaseQuery.isEmpty()) {
            filteredList.addAll(itemList);
        } else {
            for (Task item : itemList) {
                if (item.getTask().toLowerCase().contains(lowerCaseQuery) ||
                        item.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                        item.getDescription().toLowerCase().contains(lowerCaseQuery) ||
                        item.getColorCode().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(item);
                }
            }
        }
        taskAdapter.notifyDataSetChanged();
    }

}