package com.example.mytodolist.Adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mytodolist.Model.Task;
import com.example.mytodolist.R;
import com.example.mytodolist.Util.Utilis;
import com.google.android.material.chip.Chip;

import java.util.Date;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Task> taskList;
    private final OnToDoClickListener toDoClickListener;

     public RecyclerViewAdapter(List<Task> taskList, OnToDoClickListener toDoClickListener){
        this.toDoClickListener = toDoClickListener;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row
                 ,parent,false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task currTask = taskList.get(position);
        String formattedDate = Utilis.formatDate(currTask.getDueDate().getTime());

        //colorStatelist
        ColorStateList colorStateList = new ColorStateList(new int[][]{
            new int[]{-android.R.attr.state_enabled}
            ,new int[]{android.R.attr.state_enabled}
        },new int[]{
                Color.LTGRAY,
                Utilis.priorityColor(currTask)
        }
        );


        holder.row_text_view.setText(currTask.getTask().toString().trim());
        holder.row_chip_btn.setText(formattedDate.trim());
        holder.row_chip_btn.setChipIconTint(colorStateList);
        holder.row_radio_btn.setButtonTintList(colorStateList);
        holder.row_chip_btn.setTextColor(Utilis.priorityColor(currTask));


    }



    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatTextView row_text_view;
        private AppCompatRadioButton row_radio_btn;
        private Chip row_chip_btn;
        private final OnToDoClickListener onToDoClickListener;

         public ViewHolder(@NonNull View itemView) {
            super(itemView);
            row_text_view = itemView.findViewById(R.id.rowxml_todo_textview);
            row_radio_btn = itemView.findViewById(R.id.rowxml_radio_button);
            row_chip_btn = itemView.findViewById(R.id.rowxml_todo_chip);
            this.onToDoClickListener = toDoClickListener;

            //setting the specific clicks in the recycler view.
            itemView.setOnClickListener(this);
            row_radio_btn.setOnClickListener(this);
            row_chip_btn.setOnClickListener(this);
         }

        @Override
        public void onClick(View view) {
            int id = view.getId();

            Task currTask = taskList.get(getAdapterPosition());

            if(id == R.id.todo_row_layout){
                onToDoClickListener.toDoOnclick(getAdapterPosition(),currTask);
            }else if(id == R.id.rowxml_radio_button){
                onToDoClickListener.radioOnClick(currTask);
            }else if(id == R.id.rowxml_todo_chip){
                onToDoClickListener.chipOnClick(taskList.get(getAdapterPosition()));
            }

        }
    }
}
