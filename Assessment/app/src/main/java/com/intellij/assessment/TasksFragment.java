package com.intellij.assessment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intellij.assessment.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment implements DetailFragment.OnSaveListener, MyItemRecyclerViewAdapter.Listener {

    private MainActivity mainActivity;
    private List<Task> tasks;
    private MyItemRecyclerViewAdapter adapter;
    public TasksFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        tasks = new ArrayList<>();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new MyItemRecyclerViewAdapter(tasks, this);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        tasks.addAll(mainActivity.getTasks());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void doneSave() {
        Log.i("done save", "test");
    }

    @Override
    public void onClick(Task task) {
        mainActivity.initTask(task);
    }

}
