package com.kalazi.countdown;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class OldMainActivity extends AppCompatActivity {
    private CalendarAccountManager calendar;

//    private void accessCalendar() {
//        TextView text = findViewById(R.id.textView);
//        text.setText(calendar.getFirstItem(this));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_oldmain);

//        NavController navController = Navigation.findNavController(this, R.id.textView);
//        AppBarConfiguration appBarConfiguration =
//                new AppBarConfiguration.Builder(navController.getGraph()).build();
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        NavigationUI.setupWithNavController(
//                toolbar, navController, appBarConfiguration);

        calendar = new CalendarAccountManager(this);

//        Button button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                accessCalendar();
//            }
//        });

    }
}
