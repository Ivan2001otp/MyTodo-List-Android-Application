package com.example.mytodolist;
import com.example.mytodolist.Data.Priority;
import com.example.mytodolist.Model.Task;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytodolist.Model.SharedViewModel;
import com.example.mytodolist.Model.TaskViewModel;
import com.example.mytodolist.Util.Utilis;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener{
    private EditText todo_et;
    private Group Calendar_group;
    private ImageView Calendar_button;
    private ImageView Priority_button;
    private ImageView Save_button;

    private RadioButton selectedRadioBtn;
    private int selectRadioId;
    private Priority settingPriority;

    private RadioGroup priorityRadioGroup;
    private CalendarView calendarView;
    private Chip today_chip,tomorrow_chip,next_week_chip;
    private SharedViewModel sharedViewModel;
    private boolean isEdit;
    private TaskViewModel taskViewModel;
    private Date dueDate;


     private final Calendar calendar  = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        // inflating all the views of bottomLayer sheet.

        View view = inflater.inflate(R.layout.bottom_sheet,container,false);
            //extracting the views and setting the findview by id.
        todo_et = view.findViewById(R.id.todo_et);
        Calendar_group = view.findViewById(R.id.calendar_group);
        Calendar_button = view.findViewById(R.id.today_calendar_button);
        Priority_button = view.findViewById(R.id.priority_todo_button);
        Save_button = view.findViewById(R.id.upload_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup);
        calendarView = view.findViewById(R.id.calendarView);

        today_chip = view.findViewById(R.id.today_chip_button);
        tomorrow_chip = view.findViewById(R.id.tomorrow_chip_button);
        next_week_chip = view.findViewById(R.id.next_week_chip_button);

        today_chip.setOnClickListener(this);
        tomorrow_chip.setOnClickListener(this);
        next_week_chip.setOnClickListener(this);

        return view;

    }


    //this refreshes the update activity ,and reflects the changes.
    @Override
    public void onResume() {
        super.onResume();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        if(sharedViewModel.getSelectedItem().getValue()!=null){
            isEdit = sharedViewModel.getIsEdit();
            Task task = sharedViewModel.getSelectedItem().getValue();
            todo_et.setText(task.getTask());
            Log.d("onViewCreate", "onResume task : "+task.getTask());
        }
    }

    //this method helps to set the pertained operations of inflated view.Set the listeners
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

        Priority_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priorityRadioGroup.setVisibility(
                        priorityRadioGroup.getVisibility()==View.GONE ?
                                View.VISIBLE : View.GONE
                );

                priorityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int selectedId) {


                            if(priorityRadioGroup.getVisibility() == View.VISIBLE){
                                if(selectedId == R.id.radio_btn_high_priority){
                                    settingPriority = Priority.HIGH;
                                }else if(selectedId == R.id.medium_priority){
                                    settingPriority = Priority.MEDIUM;
                                }else if(selectedId == R.id.low_priority){
                                    settingPriority = Priority.LOW;
                                }
                            }
                            else{
                                //default
                                settingPriority = Priority.LOW;
                            }

                    }
                });
            }
        });



        //functionality of calendar button
            Calendar_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilis.hideSoftKeyboardUtil(view);

                    Calendar_group.setVisibility(
                            Calendar_group.getVisibility()==View.GONE ? View.VISIBLE : View.GONE
                    );
                }
            });

            //functionality of calendar view.
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                @Override
                public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth){
                        Utilis.hideSoftKeyboardUtil(calendarView);

                        calendar.clear();
                        calendar.set(year,month,dayOfMonth);
                        dueDate = calendar.getTime();
                    Log.d("onCreate", "onSelectedDayChange: "+dueDate.toString());
                }
            });

            //functionality of save button.
            Save_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(!todo_et.getText().toString().isEmpty() && dueDate!=null && settingPriority !=null){
                        Task myTask = new Task(todo_et.getText().toString().trim(),dueDate,Calendar.getInstance().getTime()
                                ,settingPriority,false);


                        if(isEdit){
                            Task updatedTask = sharedViewModel.getSelectedItem().getValue();
                            isEdit = false;

                            if(todo_et.getText().toString().isEmpty()){
                                updatedTask.setTask(updatedTask.getTask());
                            }else {
                                updatedTask.setTask(todo_et.getText().toString().trim());
                            }


                            updatedTask.setCreated_date(Calendar.getInstance().getTime());
                            updatedTask.setDueDate(dueDate);
                            updatedTask.setPriority(settingPriority);
                            updatedTask.setDone(false);

                            TaskViewModel.updateViewModel(updatedTask);
                            sharedViewModel.setIsEdit(false);

                            Log.d("onCreate", "updated : "+updatedTask.getTask());
                        }else{
                            TaskViewModel.insertViewModel(myTask);
                            Log.d("onCreate", "saved : "+myTask.getTask());
                        }

                        if(BottomSheetFragment.this.isVisible()){
                            BottomSheetFragment.this.dismiss();
                        }

                    }else{
                        Toast.makeText(getContext(),"please provide necessary data !",Toast.LENGTH_SHORT)
                                .show();
                    }
                    //removes the keyboard ui as soon as save function is triggered.
                    Utilis.hideSoftKeyboardUtil(view);
                }
            });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.today_chip_button){
            calendar.add(Calendar.DAY_OF_YEAR,0);
            dueDate = calendar.getTime();
            Log.d("onCreate", "today : "+dueDate.toString());
        }else if(id == R.id.tomorrow_chip_button){
            calendar.add(Calendar.DAY_OF_YEAR,1);
            dueDate = calendar.getTime();
            Log.d("onCreate", "tomorrow : "+dueDate.toString());
        }else if(id == R.id.next_week_chip_button){
            calendar.add(Calendar.DAY_OF_YEAR,7);
            dueDate = calendar.getTime();
            Log.d("onCreate", "week : "+dueDate.toString());
        }
    }
}
