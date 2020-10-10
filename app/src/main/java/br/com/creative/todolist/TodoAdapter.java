package br.com.creative.todolist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private final Context context;
    private final List<Todo> todos;
    private final OnItemClick<Todo> onEditClick;
    private final OnItemClick<Todo> onDeleteClick;
    private final OnItemClick<Todo> onCheckedClick;

    public TodoAdapter(Context context,List<Todo> todos, OnItemClick<Todo> onEditClick, OnItemClick<Todo> onDeleteClick, OnItemClick<Todo> onCheckedClick) {
        this.todos = todos;
        this.context = context;
        this.onEditClick = onEditClick;
        this.onDeleteClick = onDeleteClick;
        this.onCheckedClick = onCheckedClick;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item,parent,false);
        return new TodoViewHolder(context,view,onEditClick,onDeleteClick,onCheckedClick);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.bindView(todos.get(position));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;
        private final Context context;
        private final TextView itemTitle;
        private final Button btnDelete;
        private final Button btnCheckbox;

        private final OnItemClick<Todo> onEditClick;
        private final OnItemClick<Todo> onDeleteClick;
        private final OnItemClick<Todo> onCheckedClick;

        public TodoViewHolder(Context context,View itemView, OnItemClick<Todo> onEditClick, OnItemClick<Todo> onDeleteClick, OnItemClick<Todo> onCheckedClick) {
            super(itemView);
            this.context = context;
            this.itemView = itemView;
            this.itemTitle =  itemView.findViewById(R.id.itemTitle);
            this.btnDelete =  itemView.findViewById(R.id.itemDelete);
            this.btnCheckbox =  itemView.findViewById(R.id.itemCheckbox);

            this.onEditClick = onEditClick;
            this.onDeleteClick = onDeleteClick;
            this.onCheckedClick = onCheckedClick;
        }

        void bindView(final Todo todo){
            itemTitle.setText(todo.getTitle());
            itemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClick.onClick(todo);
                }
            });

            btnCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { onCheckedClick.onClick(todo);
                }
            });

            if(todo.getChecked()){
                btnCheckbox.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_baseline_check_box_24));
            }

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { onDeleteClick.onClick(todo);
                }
            });

        }

    }
}
