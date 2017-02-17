package com.xtelsolution.xmec.xmec.views.activity;

import android.content.Intent;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.entity.BodyMapPosition;
import com.xtelsolution.xmec.R;

public class DiseaseDiagnosiActivity extends AppCompatActivity {
    private TextView tvTitleToobar;
    private Toolbar mToolbar;
    private ImageView imgBodyMap;
    private BodyMapPosition bodyMapPosition;
    private RadioGroup quarterGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_diagnosi);
        init();
        tvTitleToobar.setText(getResources().getText(R.string.disease_diagnosi));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        imgBodyMap = (ImageView) findViewById(R.id.img_body_map);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final int screenDensity = metrics.densityDpi;
        bodyMapPosition.setNameBodyPart(Constant.NAME_BODY_PARTS);
        bodyMapPosition.setPositionBody(Constant.POSITION_BODY_MAP);
        Log.e("POSITIONBODY", "onTouch: ");
        imgBodyMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = getPointerCoords(imgBodyMap, motionEvent)[0] / screenDensity;
                bodyMapPosition.setX(x);
                float y = getPointerCoords(imgBodyMap, motionEvent)[1] / screenDensity;
                bodyMapPosition.setY(y);
                Log.e("POSITIONBODY", "onTouch: "+x+"    "+y );
                String namePart = bodyMapPosition.getNamePosition();
                if (namePart != null) {
                    Intent i = new Intent(DiseaseDiagnosiActivity.this, DetailBodyPart.class);
                    i.putExtra("part", namePart);
                    startActivity(i);
                }
                return false;
            }
        });
        quarterGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.quarter_front) {
                    imgBodyMap.setImageResource(R.drawable.nu_truoc);
                    bodyMapPosition.setNameBodyPart(Constant.NAME_BODY_PARTS);
                    bodyMapPosition.setPositionBody(Constant.POSITION_BODY_MAP);
                } else {
                    imgBodyMap.setImageResource(R.drawable.nu_sau);
                    bodyMapPosition.setNameBodyPart(Constant.NAME_BODY_PARTS_BEHIND);
                    bodyMapPosition.setPositionBody(Constant.POSITION_BODY_MAP_BEHIND);
                }
            }
        });


    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitleToobar = (TextView) findViewById(R.id.toolbar_title);
        bodyMapPosition = new BodyMapPosition();
        quarterGroup = (RadioGroup) findViewById(R.id.toggle_quarter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private final float[] getPointerCoords(ImageView view, MotionEvent e) {
        final int index = e.getActionIndex();
        final float[] coords = new float[]{e.getX(index), e.getY(index)};
        Matrix matrix = new Matrix();
        view.getImageMatrix().invert(matrix);
        matrix.postTranslate(view.getScrollX(), view.getScrollY());
        matrix.mapPoints(coords);
        return coords;
    }

}
