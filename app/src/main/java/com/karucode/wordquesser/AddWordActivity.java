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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import static com.karucode.wordquesser.WordQuesserStartingScreenActivity.FILE_NAME;
import static com.karucode.wordquesser.WordQuesserStartingScreenActivity.TEST_FILE_NAME;

public class AddWordActivity extends AppCompatActivity {

    private EditText mEditText;
    private TextView definitionView;
    private WebView webView;
    private Word wordObject;
    private WordQuesserUtilities wordQuesserUtilities;
    HashMap<Integer, Word> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);


        wordQuesserUtilities = WordQuesserUtilities.getInstance();
        list = wordQuesserUtilities.getWordsAndDefinitions();


        mEditText = findViewById(R.id.add_word_insert_word);
        definitionView = findViewById(R.id.add_word_definition);
        definitionView.setMovementMethod(new ScrollingMovementMethod());
        webView = findViewById(R.id.add_word_web_view);
        webView.setWebViewClient(new WebViewClient());


//        Button buttonSearchWord = findViewById(R.id.add_word_button_search);
//        buttonSearchWord.setOnClickListener(V -> search());


        Button buttonSaveWordAndDef = findViewById(R.id.add_word_button_add_to_db);
        buttonSaveWordAndDef.setOnClickListener(V -> {
            if (wordObject != null) {

                SaveHashMapToDB(wordObject, list);
            } else {
                Toast.makeText(AddWordActivity.this, "word object is null", Toast.LENGTH_SHORT).show();
            }
            ;
        });


        // Not the correct solution but removes networkonmainthreadexception
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //------
    }

    public void search(View v) {
        String input = mEditText.getText().toString();
        closeKeyboard();
        loadWebview(input);
        searchFromEkiHtml();

    }

    public void loadWebview(String input) {
        webView.loadUrl("http://www.eki.ee/dict/ekss/index.cgi?Q=" + input + "&F=M");
    }


    void searchFromEkiHtml() {
        String word = mEditText.getText().toString();
        word = word.toLowerCase();
        URL url;
        try {
            String a = "http://www.eki.ee/dict/ekss/index.cgi?Q=" + word + "&F=M";
            url = new URL(a);
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            boolean found = false;
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                if (inputLine.contains("https://sõnaveeb.ee/search/est-est/detail/" + word)) {
                    //if (inputLine.contains(word)) {
                    int begin = inputLine.indexOf("width=16") + 32;
                    inputLine = inputLine.substring(begin);
                    inputLine = Jsoup.parse(inputLine).body().text();
                    Log.d("definition", word + " - " + inputLine);

                    definitionView.setText(word + " - " + inputLine);

                    wordObject = new Word(0, word, inputLine);

//                    Files.write(pathToWordsAndDefinitions, ("0" + " /// " + word + " /// " + inputLine + "\n").getBytes(), APPEND);
                    found = true;
                    // }else{ Files.write(pathToDoubleCheck,(word+"\n").getBytes(),APPEND); }

                }
            }
            br.close();
            if (found) {
//                Toast.makeText(AddWordActivity.this, wordObject.getAttempts() + " found, press Add to DB", Toast.LENGTH_LONG).show();
                Toast.makeText(AddWordActivity.this, list.size() + " size before", Toast.LENGTH_LONG).show();
                //System.out.println("Definition " + inputLine);
            } else {
//                wordObject = null;
                Toast.makeText(AddWordActivity.this, word + " not Found", Toast.LENGTH_SHORT).show();
//                Files.write(pathToDoubleCheck, (word + "\n").getBytes(), APPEND);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    //TODO add word and definition to db
    public void SaveHashMapToDB(Word word, HashMap<Integer, Word> list) {
        Integer number = list.size();
        list.put(number, word);
        Toast.makeText(AddWordActivity.this, list.size() + " size after", Toast.LENGTH_LONG).show();
//        Log.d("Word is", list.get(number). + " : " + millisInterval);

        //hashmapi sai lisatud

//        String line = wordQuesserUtilities.readWordsFromHashmapToString(list);
        String line = list.get(3).getWord() + list.get(number).getDefinition() + "\n";
//
        //TODO BUGGY


        //siitmaalt allapoole workib


        FileOutputStream fos = null;

        try {
            fos = openFileOutput(TEST_FILE_NAME, MODE_PRIVATE);

            for (int i = 0; i < list.size(); i++) {
                Word wd = list.get(i);
                line.concat((wd.getAttempts() + " /// " + wd.getWord() + " /// " + wd.getDefinition()));
                fos.write((line + "\n").getBytes());
            }



//            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + TEST_FILE_NAME,
//                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


//        Toast.makeText(AddWordActivity.this, list.get(number).getWord() + " added to db", Toast.LENGTH_LONG).show();
    }

    //TODO loop to write hashmap to txt file


    private void closeKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}


