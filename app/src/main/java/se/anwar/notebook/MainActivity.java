package se.anwar.notebook;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by anwar_se on 1/3/2020.
 * Email: anwar.dev.96@gmail.com
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout container = findViewById(R.id.container);
        NotebookView nbView = new NotebookView(this);
        nbView.setStartColor(Color.RED);
        nbView.setEndColor(Color.BLUE);
        nbView.setAngle(NotebookView.Angle.ANGLE_270);
        container.addView(nbView);




    }
}
