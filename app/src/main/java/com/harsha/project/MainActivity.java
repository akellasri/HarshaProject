package com.harsha.project;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context ctx;
    PieChart mChart;
    double pointerAngle = 0, currentAngle = 0;
    public static Handler mHandler = new Handler();
    TextView message, selectedTv;
    ImageView ic_Back, ic_Space, ic_Fullstop, ic_Close;
    int quadrant = 0;
    private String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        mChart = findViewById(R.id.mChart);
        message = findViewById(R.id.message);
        selectedTv = findViewById(R.id.selectedTv);
        ic_Back = findViewById(R.id.ic_Back);
        ic_Space = findViewById(R.id.ic_Space);
        ic_Fullstop = findViewById(R.id.ic_Fullstop);
        ic_Close = findViewById(R.id.ic_Close);
        ic_Space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.setText(message.getText().toString().concat(" "));
            }
        });
        ic_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.getText().toString().length() > 0)
                    message.setText(message.getText().toString().substring(0, message.getText().toString().length() - 1));
                else
                    Toast.makeText(ctx, "Message already empty", Toast.LENGTH_SHORT).show();
            }
        });
        ic_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.setText("");
            }
        });
        ic_Fullstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.setText(message.getText().toString().concat(". "));
            }
        });
        chartInit();
    }

    void chartInit() {
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setDragDecelerationFrictionCoef(0f);
        mChart.setCenterTextColor(Color.BLACK);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.BLACK);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(40f);
        mChart.setTransparentCircleRadius(10f);
        mChart.setDrawCenterText(false);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(false);
        setData();
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTextSize(13.5f);
        l.setTextColor(Color.WHITE);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setEnabled(false);
        Description desc2 = new Description();
        desc2.setText("");
        mChart.setDescription(desc2);
        mChart.setEntryLabelColor(Color.TRANSPARENT);
        mChart.setEntryLabelTextSize(2f);
        mChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pointerAngle = Math.floor((double) mChart.getRotationAngle());
                currentAngle = Math.floor((double) mChart.getRotationAngle());
                if (pointerAngle > 90 && pointerAngle <= 180)
                    quadrant = 3;
                else if (pointerAngle > 180 && pointerAngle < 270)
                    quadrant = 1;
                else if (pointerAngle > 270 && pointerAngle < 360)
                    quadrant = 2;
                else if (pointerAngle >= 0 && pointerAngle < 90)
                    quadrant = 4;
                else quadrant = 0;
                if (quadrant == 2)
                    pointerAngle -= 270;
                else
                    pointerAngle += 90;
                int pos = (int) Math.floor(pointerAngle / 13.8461538462);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    try {
                        message.setText(message.getText().toString().concat("" + chars[pos]));
                    } catch (Exception e) {
                        if (pos > chars.length / 2)
                            message.setText(message.getText().toString().concat("" + chars[chars.length - 1]));
                        else
                            message.setText(message.getText().toString().concat("" + chars[0]));
                    }
                    if (quadrant == 1 || quadrant == 3)
                        mHandler.postDelayed(runTimerLeft, 1);
                    else if (quadrant == 2 || quadrant == 4)
                        mHandler.postDelayed(runTimerRight, 1);
                } else {
                    try {
                        selectedTv.setText(chars[pos]);
                    } catch (Exception e) {
                        if (pos > chars.length / 2)
                            selectedTv.setText(chars[chars.length - 1]);
                        else
                            selectedTv.setText(chars[0]);
                    }
                }
                return false;
            }
        });
    }

    void setData() {
        List<PieEntry> allPieEntries2 = new ArrayList<PieEntry>();
        allPieEntries2.add(new PieEntry((float) 99, 1));
        allPieEntries2.add(new PieEntry((float) 1, 1));

        PieDataSet dataSet = new PieDataSet(allPieEntries2, "");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(0f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#7DEB58"));
        colors.add(Color.parseColor("#FF6C5A"));
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.TRANSPARENT);
        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    Runnable runTimerLeft = new Runnable() {
        public void run() {
            if (currentAngle != 270.0 && (quadrant == 1 || quadrant == 3)) {
                mChart.setRotationAngle((float) ++currentAngle);
                mChart.invalidate();
                mHandler.postDelayed(runTimerLeft, 1);
                mHandler.postDelayed(runTimerLeft, 1);
            } else quadrant = 0;
        }
    };


    Runnable runTimerRight = new Runnable() {
        public void run() {
            if (currentAngle != 270 && (quadrant == 2 || quadrant == 4)) {
                if (currentAngle == 0)
                    currentAngle = 360f;
                mChart.setRotationAngle((float) --currentAngle);
                mChart.invalidate();
                mHandler.postDelayed(runTimerRight, 1);
                mHandler.postDelayed(runTimerRight, 1);
            } else quadrant = 0;
        }
    };
}
