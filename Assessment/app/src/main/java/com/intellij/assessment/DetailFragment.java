package com.intellij.assessment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.intellij.assessment.model.Task;


public class DetailFragment extends Fragment {

    private MainActivity mainActivity;
    private OnSaveListener onSaveListener;
    private Button save;
    private Button cancel;

    private EditText title;
    private EditText details;

    private Task task;
    private View bottomView;
    public DetailFragment() {
        // Required empty public constructor
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        bottomView = view.findViewById(R.id.bottom_view);
        title = (EditText) view.findViewById(R.id.title);
        details = (EditText) view.findViewById(R.id.details);
        save = (Button) view.findViewById(R.id.save);
        cancel = (Button) view.findViewById(R.id.cancel);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title_ = title.getText().toString();
                String details_ = details.getText().toString();
                if (task == null && !title_.isEmpty()) {
                    Task task = new Task();
                    task.setTitle(title_);
                    task.setDetails(details_);
                    mainActivity.saveTask(task);
                }
                else {
                    task.setTitle(title_);
                    task.setDetails(details_);
                    mainActivity.editTask(task);
                }
                if (onSaveListener != null) {
                    onSaveListener.doneSave();
                }
                getFragmentManager().popBackStack();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        if (task != null) {
            title.setText(task.getTitle());
            details.setText(task.getDetails());
            title.setFocusable(false);
            details.setFocusable(false);
            bottomView.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action0).setVisible(false);
        inflater.inflate(R.menu.menu_new, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.edit){
            title.setFocusableInTouchMode(true);
            details.setFocusableInTouchMode(true);
            bottomView.setVisibility(View.VISIBLE);
            return false;
        }
        else if(id == R.id.delete){
            mainActivity.deleteTask(task.getId());
            getFragmentManager().popBackStack();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setListener(OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
    }

    interface OnSaveListener {
        void doneSave();
    }
}
