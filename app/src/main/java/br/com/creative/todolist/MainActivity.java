package br.com.creative.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static String EXTRA_TODO = "EXTRA_TODO";
    private RecyclerView todoRecyclerView;
    private ArrayList<Todo> todos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        todoRecyclerView = findViewById(R.id.todoRecyclerView);
        update();



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddTodoActivity.class);

                startActivityForResult(intent,1);
            }
        });
    }

    public static Intent getIntent(Context context, final Todo todo){
        return new Intent(context,MainActivity.class){{
            putExtra(EXTRA_TODO, todo);
        }};
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TodoResult result = TodoResult.fromResult(resultCode);

        if(result == null) return;

        switch (result){
            case CREATED:
                Todo newTodo = (Todo) data.getSerializableExtra(EXTRA_TODO);
                todos.add(newTodo);
                break;
            case EDITED:
                Todo todo = (Todo) data.getSerializableExtra(EXTRA_TODO);
                todos.set(todos.indexOf(todo),todo);
                break;
        }
        update();
    }

    private RecyclerView.Adapter<?> getAdapter(){
        return new TodoAdapter(this,todos,getEditItemClick(),getDeleteItemClick(),getCheckedItemClick());
    }

    private OnItemClick<Todo> getEditItemClick(){
        return new OnItemClick<Todo>() {
            @Override
            public void onClick(Todo item) {
                startActivityForResult(AddTodoActivity.getStartIntent(MainActivity.this,item),1);
            }
        };
    }

    private OnItemClick<Todo> getDeleteItemClick(){
        return new OnItemClick<Todo>() {
            @Override
            public void onClick(Todo item) {
                removeTodo(item);
            }
        };
    }

    private OnItemClick<Todo> getCheckedItemClick(){
        return new OnItemClick<Todo>() {
            @Override
            public void onClick(Todo item) {
                item.setChecked(! item.getChecked());
                update();
            }
        };
    }

    private void removeTodo(Todo item) {
        todos.remove(item);
        update();
    }

    private void update (){
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        todoRecyclerView.setHasFixedSize(true);
        todoRecyclerView.setAdapter(getAdapter());
    }
}