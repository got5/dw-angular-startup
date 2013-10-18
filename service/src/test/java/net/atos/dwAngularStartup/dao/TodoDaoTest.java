package net.atos.dwAngularStartup.dao;

import java.util.Date;
import java.util.UUID;
import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import net.atos.dwAngularStartup.api.Todo;

public class TodoDaoTest {

  private TodoDao dao;

  @Before
  public void setUp() {
    DBI dbi = new DBI("jdbc:h2:mem:test");
    dao = dbi.open(TodoDao.class);
  }

  @After
  public void tearDown() {
    dao.close();
  }

  @Test
  public void testCreateAnEmptyTable() {
    dao.createTableIfNotExists();
    assertThat(dao.findAll()).isEmpty();
  }

  @Test
  public void testCRUD() {
    final Todo todo = Todo.newBuilder()
        .id(UUID.randomUUID().toString())
        .title("john@example.com")
        .build();

    dao.createTableIfNotExists();
    dao.insert(todo);

    assertThat(dao.exists(todo.getId())).isTrue();
    assertThat(dao.exists("dummy-missing-id")).isFalse();

    assertThat(dao.findAll()).contains(todo);
    assertThat(dao.findById(todo.getId())).isEqualTo(todo);



    Todo updated = todo.toBuilder().build();
    dao.update(updated);

    assertThat(dao.findById(updated.getId())).isEqualTo(updated);

    dao.delete(updated.getId());
    assertThat(dao.findAll()).isEmpty();
  }

  @Test
  public void testTryToRetrieveMissingTodo() {
    dao.createTableIfNotExists();

    assertThat(dao.findById("dummy")).isNull();
  }


}
