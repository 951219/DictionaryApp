package com.karucode.wordquesser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;import java.io.InputStream;
import java.io.InputStreamReader;


public class LookDbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_db);


//        try {
//            InputStream is = getAssets().open(WordQuesserStartingScreenActivity.FILE_NAME);
//
//            // We guarantee that the available method returns the total
//            // size of the asset...  of course, this does mean that a single
//            // asset can't be more than 2 gigs.
//            int size = is.available();
//
//            // Read the entire asset into a local byte buffer.
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//
//            // Convert the buffer into a string.
//            String text = new String(buffer);
//
//            // Finally stick the string into the text view.
//            // Replace with whatever you need to have the text into.
//
//        TextView tv = findViewById(R.id.db_look_text);
//        tv.setMovementMethod(new ScrollingMovementMethod());

//            tv.setText(text);
//
//        } catch (IOException e) {
//            // Should never happen!
//            throw new RuntimeException(e);
//        }

        TextView tv = findViewById(R.id.db_look_text);
        tv.setMovementMethod(new ScrollingMovementMethod());
        Toast.makeText(this, "Read from TEST_FILE_NAME, size: " + WordQuesserUtilities.getInstance().getWordsAndDefinitions().size(), Toast.LENGTH_LONG).show();
        FileInputStream fis = null;

        try {
            fis = openFileInput(WordQuesserStartingScreenActivity.TEST_FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            tv.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
