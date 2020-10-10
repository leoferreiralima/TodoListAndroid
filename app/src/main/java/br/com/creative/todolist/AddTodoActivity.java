package br.com.creative.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddTodoActivity extends AppCompatActivity {
    private static String EXTRA_TODO = "EXTRA_TODO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Todo todo = (Todo) getIntent().getSerializableExtra(EXTRA_TODO);

        final TextView txtTitle = findViewById(R.id.txtTitle);
        final TextView textDescription = findViewById(R.id.textDescription);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);

        if(todo != null){
            txtTitle.setText(todo.getTitle());
            textDescription.setText(todo.getDescription());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence title = txtTitle.getText();
                CharSequence description = textDescription.getText();
                if(title == null || title.toString().length() == 0){
                    String message = getString(R.string.null_value,getString(R.string.title_input_lable));
                    Toast.makeText(AddTodoActivity.this,message,Toast.LENGTH_SHORT)
                        .show();
                    return;
                }

                if(todo != null){
                    todo.setTitle(title.toString());
                    todo.setDescription(description != null ? description.toString() : null);
                    setResult(TodoResult.EDITED.getResult(),MainActivity.getIntent(AddTodoActivity.this,todo));
                }else{
                    Todo newTodo = new Todo(title.toString(),description != null ? description.toString() : null);
                    setResult(TodoResult.CREATED.getResult(),MainActivity.getIntent(AddTodoActivity.this,newTodo));
                }

                finish();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(TodoResult.CANCELED.getResult());
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(TodoResult.CANCELED.getResult());
        finish();
    }

    public static Intent getStartIntent(Context context){
        return getStartIntent(context,null);
    }

    public static Intent getStartIntent(Context context, final Todo todo){
        return new Intent(context,AddTodoActivity.class){{
           putExtra(EXTRA_TODO, todo);
        }};
    }
}