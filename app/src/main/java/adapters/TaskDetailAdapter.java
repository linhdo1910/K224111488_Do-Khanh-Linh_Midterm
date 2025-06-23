package adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.k224111488_dokhanhlinh_k22411ca.R;

import java.util.ArrayList;

import models.Customer;

public class TaskDetailAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Customer> customers;

    public TaskDetailAdapter(Context context, ArrayList<Customer> customers) {
        this.context = context;
        this.customers = customers;
    }

    @Override
    public int getCount() {
        return customers.size();
    }

    @Override
    public Object getItem(int position) {
        return customers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return customers.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Tối ưu View
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.task_detail_item, parent, false);
        }

        // Ánh xạ
        TextView txtName = convertView.findViewById(R.id.txtCustomerName);
        TextView txtPhone = convertView.findViewById(R.id.txtPhoneNumber);

        Customer customer = customers.get(position);
        txtName.setText(customer.getName());
        txtPhone.setText(customer.getPhone());

        // Đổi màu nền: Vàng nếu chưa gọi, Trắng nếu đã gọi
        if (customer.getIsCalled() == 0) {
            convertView.setBackgroundColor(Color.YELLOW);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }
}
