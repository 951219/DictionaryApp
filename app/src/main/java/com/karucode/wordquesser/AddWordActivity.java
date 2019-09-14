package com.karucode.wordquesser;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class AddWordActivity extends AppCompatActivity {

    private EditText mEditText;
    private TextView definitionView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        mEditText = findViewById(R.id.add_word_insert_word);
        definitionView = findViewById(R.id.add_word_definition);
        definitionView.setMovementMethod(new ScrollingMovementMethod());
        webView = findViewById(R.id.add_word_web_view);
        webView.setWebViewClient(new WebViewClient());


//        Button buttonSearchWord = findViewById(R.id.add_word_button_search);
//        buttonSearchWord.setOnClickListener(V -> search());


        Button buttonSaveWordAndDef = findViewById(R.id.add_word_button_load);
        buttonSaveWordAndDef.setOnClickListener(V -> {
            searchFromEKI();
        });


        // Not the correct solution but removes networkonmainthreadexception
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //------
    }

    public void search(View v) {
        String input = mEditText.getText().toString();
        closeKeyboard();
        webView.loadUrl("http://www.eki.ee/dict/ekss/index.cgi?Q=" + input + "&F=M");
        searchFromEKI();

    }



    //TODO find the definition from eki html
    void searchFromEKI() {
        String word =mEditText.getText().toString();
        word = word.toLowerCase();
        URL url;
        try {
            String a = "http://www.eki.ee/dict/ekss/index.cgi?Q=" + word + "&F=M";
            url = new URL(a);
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            boolean added = false;
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                if (inputLine.contains("https://sõnaveeb.ee/search/est-est/detail/" + word)) {
                    //if (inputLine.contains(word)) {
                    int begin = inputLine.indexOf("width=16") + 32;
                    inputLine = inputLine.substring(begin);
                    inputLine = Jsoup.parse(inputLine).body().text();
                    Log.d("definition", word + " - " + inputLine);
                    System.out.println(word + " - " + inputLine);

                    definitionView.setText(word + " - " +inputLine);
//                    Files.write(pathToWordsAndDefinitions, ("0" + " /// " + word + " /// " + inputLine + "\n").getBytes(), APPEND);
                    added = true;
                    // }else{ Files.write(pathToDoubleCheck,(word+"\n").getBytes(),APPEND); }

                }
            }
            br.close();
            if (added) {
                Toast.makeText(AddWordActivity.this, word + " Found", Toast.LENGTH_SHORT).show();
                //System.out.println("Definition " + inputLine);
            } else {
                Toast.makeText(AddWordActivity.this, word + " Not Found", Toast.LENGTH_SHORT).show();
//                Files.write(pathToDoubleCheck, (word + "\n").getBytes(), APPEND);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    //TODO add word and definition to db
    public void addWordToDB(String word, String definition){







    }


//    void searchFromEKI(String word) { }

    private void closeKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
