package com.gnyapp.vero_android_task.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.gnyapp.vero_android_task.models.Task;
import com.gnyapp.vero_android_task.repositories.SearchRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private SearchRepository searchRepository;

    public SearchViewModel() {
        searchRepository = new SearchRepository();
    }

    public LiveData<List<Task>> searchTask(String authorization) {
        return searchRepository.searchTask(authorization);
    }

}
