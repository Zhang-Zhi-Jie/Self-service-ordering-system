package com.example.a13051_000.buffetmealsystem.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.MainActivity;
import com.example.a13051_000.buffetmealsystem.R;

/**
 * Created by 13051_000 on 2016/7/23.
 */
public class Setting_suggestions extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editText;
    private Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_suggestions);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        toolbar.setTitle("意见反馈");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = (EditText) findViewById(R.id.editText_suggestions);

        button = (Button) findViewById(R.id.suggestions_ensure);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"您的意见已提交，我们会不断地改进！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Setting_suggestions.this, MainActivity.class);
                Setting_suggestions.this.startActivity(intent);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
