package org.kroko.clappy;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.kroko.clappy.R.id;
import org.kroko.clappy.R.layout;

public final class MainActivity extends AppCompatActivity {
    private static final String[] CLAPS = {
            "\uD83D\uDC4F", // Normal
            "\uD83D\uDC4F\uD83C\uDFFB", // Light
            "\uD83D\uDC4F\uD83C\uDFFC", // Medium-Light
            "\uD83D\uDC4F\uD83C\uDFFD", // Medium
            "\uD83D\uDC4F\uD83C\uDFFE", // Medium-Dark
            "\uD83D\uDC4F\uD83C\uDFFF", // Dark
    };

    private ClipboardManager mClipboard;
    private EditText mInputText;
    private Spinner mToneSelector;
    private TextView mResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Clappy" + CLAPS[0]);
        mClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        setContentView(layout.activity_main);

        mInputText = findViewById(id.main_input);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CLAPS);
        mToneSelector = findViewById(id.main_selector);
        mToneSelector.setAdapter(adapter);

        mResultView = findViewById(id.main_result);
    }

    public void onClapifyClick(@NonNull final View v) {
        final String text = mInputText.getText().toString();
        final String[] words = text.trim().split("\\s");
        final int maxWordIndex = words.length - 1;
        final StringBuilder builder = new StringBuilder(text.length() + maxWordIndex);

        for (int i = 0; i < maxWordIndex; i++) {
            builder.append(words[i]).append(mToneSelector.getSelectedItem());
        }

        builder.append(words[maxWordIndex]);
        mResultView.setText(builder);
    }

    public void onCopyClick(@NonNull final View v) {
        final ClipData data = ClipData.newPlainText(null, mResultView.getText());
        mClipboard.setPrimaryClip(data);
        Toast.makeText(MainActivity.this, "Text copied!", Toast.LENGTH_SHORT).show();
    }
}
