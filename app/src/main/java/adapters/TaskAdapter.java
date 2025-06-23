package adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.k224111488_dokhanhlinh_k22411ca.R;

import java.util.List;

import models.TaskForTeleSales;

public class TaskAdapter extends BaseAdapter {
    Context context;
    List<TaskForTeleSales> list;

    public TaskAdapter(Context context, List<TaskForTeleSales> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }
    @Override
    public Object getItem(int i) { return list.get(i); }
    @Override
    public long getItemId(int i) { return i; }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtDate = view.findViewById(R.id.txtDate);

        TaskForTeleSales task = list.get(i);
        txtTitle.setText(task.getTaskTitle());
        txtDate.setText("Date: " + task.getDateAssigned());

        if (task.getIsCompleted() == 1)
            view.setBackgroundColor(0xFFA5D6A7); // Green

        return view;
    }
}