package com.techyourchance.multithreading.exercises.exercise3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techyourchance.multithreading.R;
import com.techyourchance.multithreading.common.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Exercise3Fragment extends BaseFragment {

    private static final int SECONDS_TO_COUNT = 3;

    public static Fragment newInstance() {
        return new Exercise3Fragment();
    }

    private Button mBtnCountSeconds;
    private TextView mTxtCount;


    private final Handler mUiHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_3, container, false);

        mBtnCountSeconds = view.findViewById(R.id.btn_count_seconds);
        mTxtCount = view.findViewById(R.id.txt_count);

        mBtnCountSeconds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countIterations();
            }
        });

        return view;
    }

    @Override
    protected String getScreenTitle() {
        return "Exercise 3";
    }

    private void countIterations() {
        /*
        1. Disable button to prevent multiple clicks
        2. Start counting on background thread using loop and Thread.sleep()
        3. Show count in TextView
        4. When count completes, show "done" in TextView and enable the button
         */

        //1. Disable button to prevent multiple clicks

        mBtnCountSeconds.setEnabled(false);
        //2. Start counting on background thread using loop and Thread.sleep()

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= SECONDS_TO_COUNT; i++) {
                    final int count = i;
                    //mUiHandler is doing task at main thread
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mTxtCount.setText(String.valueOf(count));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
//                3. Show count in TextView
//                4. When count completes, show "done" in TextView and enable the button

                //After done for loop, is time to bring back to UiThread
                //mUiHandler is doing task at main thread
                mUiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTxtCount.setText("Done!");
                        mBtnCountSeconds.setEnabled(true);
                    }
                });
            }
        }).start();



    }
}
