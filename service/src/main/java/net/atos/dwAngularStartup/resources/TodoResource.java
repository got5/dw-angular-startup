package net.atos.dwAngularStartup.resources;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.atos.dwAngularStartup.dao.TodoDao;
import net.atos.dwAngularStartup.api.Todo;
import net.atos.dwAngularStartup.api.TodoBuilder;

@Path("/todo")
public class TodoResource {


  public static final Todo TODO = Todo.newBuilder()
      .id(UUID.randomUUID().toString())
      .completed(false)
      .title("procrastinate")
      .build();

  private final TodoDao dao;

  public TodoResource(TodoDao dao) {
    this.dao = checkNotNull(dao, "dao is null");
  }

  @GET
  public List<Todo> list() {
    return dao.findAll();
  }

  @GET
  @Path("{id}")
  public Todo get(@PathParam("id") String id) {
    Todo todo = dao.findById(id);
    if (todo == null) {
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
    return todo;
  }

  @POST
  public Todo create(Todo todo) {
    String id = UUID.randomUUID().toString();
    while (dao.exists(id)) {
      id = UUID.randomUUID().toString();
    }

    final TodoBuilder builder = Todo.newBuilder().id(id).completed(false);
    if (todo.getTitle() != null) {
      builder.title(todo.getTitle());
    }
      Todo newTodo = builder.build();
    dao.insert(newTodo);

    return newTodo;
  }

  @PUT
  @Path("{id}")
  public Todo update(@PathParam("id") String id, @Valid Todo todo) {
    checkArgument(id.equals(todo.getId()));
    dao.update(todo);
    return todo;
  }

  @DELETE
  @Path("{id}")
  public String delete(@PathParam("id") String id){
    dao.delete(id);
    return id;
  }

  @DELETE
  public void deleteAll() {
    dao.deleteAll();
  }
}
