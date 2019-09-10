package com.karucode.wordquesser;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AddWordActivity extends AppCompatActivity {

    private EditText mEditText;
    private TextView definitionView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();  //temoves top bar
        setContentView(R.layout.activity_add_word);

        mEditText = findViewById(R.id.add_word_insert_word);
        definitionView = findViewById(R.id.add_word_definition);
        webView = findViewById(R.id.add_word_web_view);
        webView.setWebViewClient(new WebViewClient());



//        Button buttonSearchWord = findViewById(R.id.add_word_button_search);
//        buttonSearchWord.setOnClickListener(V -> search());



//        Button buttonSaveWordAndDef = findViewById(R.id.add_word_button_search);
//        buttonSaveWordAndDef.setOnClickListener(V -> {
//            try {
//                add();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }
    public void search(View v) {
        String input = mEditText.getText().toString();

        closeKeyboard();

        webView.loadUrl("http://www.eki.ee/dict/ekss/index.cgi?Q=" + input + "&F=M");
//        searchFromEKI(input);

//        text = text + "\n";
//        FileOutputStream fos = null;
//        try {
//            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
//            fos.write(text.getBytes());
//
//            mEditText.getText().clear();
//            Toast.makeText(this,"Saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null){
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

    }

    //TODO add wrod and definition to db
    public void add(View v) throws IOException {
        String input = mEditText.getText().toString();

        URL oracle = new URL("http://www.eki.ee/dict/ekss/index.cgi?Q=" + input + "&F=M");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
            Log.d("Line", inputLine);
        }
        in.close();

    }

//    public void load(View v) {
//
//        FileInputStream fis = null;
//
//        try {
//            fis = openFileInput(FILE_NAME);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//            String text;
//
//            while ((text = br.readLine()) != null){
//                sb.append(text).append("\n");
//            }
//
//            mEditText.setText(sb.toString());
//
//        } catch (FileNotFoundException e){
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fis != null){
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }


    //TODO find the definition from eki html
//    void searchFromEKI(String word) {
//        word = word.toLowerCase();
//        URL url;
//        try {
//            String a = "http://www.eki.ee/dict/ekss/index.cgi?Q=" + word + "&F=M";
//            url = new URL(a);
//            URLConnection conn = url.openConnection();
//
//            // open the stream and put it into BufferedReader
//            BufferedReader br = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream()));
//            boolean added = false;
//            String inputLine;
//            while ((inputLine = br.readLine()) != null) {
//                if (inputLine.contains("https://sõnaveeb.ee/search/est-est/detail/" + word)) {
//                    //if (inputLine.contains(word)) {
//                    int begin = inputLine.indexOf("width=16") + 32;
//                    inputLine = inputLine.substring(begin);
//                    inputLine = Jsoup.parse(inputLine).body().text();
//                    Log.d("definition", word + " - " + inputLine);
//                    System.out.println(word + " - " + inputLine);
//
//                    definitionView.setText(inputLine);
////                    Files.write(pathToWordsAndDefinitions, ("0" + " /// " + word + " /// " + inputLine + "\n").getBytes(), APPEND);
//                    added = true;
//                    // }else{ Files.write(pathToDoubleCheck,(word+"\n").getBytes(),APPEND); }
//
//                }
//            }
//            br.close();
//            if (added) {
//                Toast.makeText(AddWordActivity.this, word + "Added", Toast.LENGTH_SHORT).show();
//                //System.out.println("Definition " + inputLine);
//            } else {
//                Toast.makeText(AddWordActivity.this, word + "Not added", Toast.LENGTH_SHORT).show();
////                Files.write(pathToDoubleCheck, (word + "\n").getBytes(), APPEND);
//            }
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void closeKeyboard(){

        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
