package net.atos.dwAngularStartup.api;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;

public class TodoBuilder {

  private String id;
  private String title;
  private Boolean completed;

  public TodoBuilder() {
  }

  public TodoBuilder(Todo todo) {
    this.id = todo.getId();
    this.title = todo.getTitle();
    this.completed = todo.getCompleted();
  }

  public TodoBuilder id(String id) {
    this.id = checkNotNull(id, "id is null");
    return this;
  }

  public TodoBuilder title(String title) {
    this.title = checkNotNull(title, "title is null");
    return this;
  }

  public TodoBuilder created(Boolean completed) {
    this.completed = checkNotNull(completed, "completed is null");
    return this;
  }


  public Todo build() {
    return new Todo(id, title, completed);
  }
}