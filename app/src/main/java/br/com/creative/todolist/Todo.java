package br.com.creative.todolist;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.UUID;

public class Todo implements Serializable {
    private final String id;
    private String title;
    private String description;
    private Boolean checked;


    public Todo(String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.checked = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == this) return true;
        if(obj == null) return false;
        if(!(obj instanceof Todo)) return false;

        return this.id.equals(((Todo) obj).id);
    }
}
