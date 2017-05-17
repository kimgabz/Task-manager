package com.intellij.assessment;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.intellij.assessment.database.DataSource;
import com.intellij.assessment.model.Task;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected DataSource dataSource;
    private TasksFragment tasksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        dataSource = new DataSource(this);
        initTaskListing();
    }

    @Override
    public void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        dataSource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_task, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action0:
                initTask(null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void switchFromMainContent(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }

    public void switchToChildContent(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }

    private void initTaskListing() {
        tasksFragment = new TasksFragment();
        switchFromMainContent(tasksFragment);
    }

    public void initTask(Task task) {
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setListener(tasksFragment);
        if (task != null) {
            detailFragment.setTask(task);
        }
        switchToChildContent(detailFragment);
    }

    public List<Task> getTasks() {
         return dataSource.findAllTasks();
    }

    public void saveTask(Task task) {
        dataSource.createTaskData(task);
    }

    public void deleteTask(int id) {
        dataSource.removeSingleContact(id);
    }

    public void editTask(Task task) {
        dataSource.editItem(task);
    }
}
