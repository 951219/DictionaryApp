package com.karucode.wordquesser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class LookDbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_db);


        try {
            InputStream is = getAssets().open("WordsAndDefinitions.txt");

            // We guarantee that the available method returns the total
            // size of the asset...  of course, this does mean that a single
            // asset can't be more than 2 gigs.
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            String text = new String(buffer);

            // Finally stick the string into the text view.
            // Replace with whatever you need to have the text into.

            TextView tv = findViewById(R.id.db_look_text);

            tv.setMovementMethod(new ScrollingMovementMethod());
            tv.setText(text);

        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }
}
