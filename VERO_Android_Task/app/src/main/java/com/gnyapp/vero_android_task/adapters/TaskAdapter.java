package com.gnyapp.vero_android_task.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gnyapp.vero_android_task.R;
import com.gnyapp.vero_android_task.databinding.ItemContainerTaskBinding;
import com.gnyapp.vero_android_task.models.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TasksViewHolder> {

    private List<Task> tasks;
    private LayoutInflater layoutInflater;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerTaskBinding taskBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_task, parent, false);
        return new TasksViewHolder(taskBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        holder.bindTasks(tasks.get(position));
        if(tasks.get(position).getColorCode() != null && !tasks.get(position).getColorCode().equalsIgnoreCase("")){
            holder.itemContainerTaskBinding.frameLayout.setBackgroundColor(Color.parseColor(tasks.get(position).getColorCode()));
        }
        else {
            holder.itemContainerTaskBinding.frameLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }




    class TasksViewHolder extends RecyclerView.ViewHolder{

        private ItemContainerTaskBinding itemContainerTaskBinding;

        public TasksViewHolder(ItemContainerTaskBinding itemContainerTaskBinding){
            super(itemContainerTaskBinding.getRoot());
            this.itemContainerTaskBinding = itemContainerTaskBinding;
        }

        public void bindTasks(Task task){
            itemContainerTaskBinding.setTask(task);
            itemContainerTaskBinding.executePendingBindings();
        }
    }
}
