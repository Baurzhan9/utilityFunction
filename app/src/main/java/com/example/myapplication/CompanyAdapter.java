package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.Viewholder> {

    private Context context;
    private ArrayList<CompanyModel> companyModelArrayList;
    private static ClickListener clickListener;

    public CompanyAdapter(Context context, ArrayList<CompanyModel> companyModelArrayList) {
        this.context = context;
        this.companyModelArrayList = companyModelArrayList;
    }

    @NonNull
    @Override
    public CompanyAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.Viewholder holder, int position) {
        CompanyModel model = companyModelArrayList.get(position);
        holder.courseNameTV.setText(model.getCompany_name());

    }

    @Override
    public int getItemCount() {
        return companyModelArrayList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
        private TextView courseNameTV;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            courseNameTV = itemView.findViewById(R.id.CompanyName);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        CompanyAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}

