package net.atos.dwAngularStartup.api;

import static com.google.common.base.Preconditions.checkNotNull;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Todo {

  public static TodoBuilder newBuilder() {
    return new TodoBuilder();
  }

  @JsonProperty
  private final String id;

  @JsonProperty
  private final String title;

  @JsonProperty
  private final Boolean completed;


  @JsonCreator
  public Todo(
      @JsonProperty("id") String id,
      @JsonProperty("title") String title,
      @JsonProperty("completed") Boolean completed
  ) {
    this.id = checkNotNull(id, "id is null");
    this.title = title;
    this.completed = completed;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public Boolean getCompleted() {
    return completed;
  }


  public TodoBuilder toBuilder() {
    return new TodoBuilder(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Todo)) return false;

    Todo todo = (Todo) o;

    if (completed != null ? !completed.equals(todo.completed) : todo.completed != null) return false;
    if (title != null ? !title.equals(todo.title) : todo.title != null) return false;
    if (id != null ? !id.equals(todo.id) : todo.id != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (completed != null ? completed.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Todo{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", completed=" + completed +
        '}';
  }
}
