package net.atos.dwAngularStartup.api;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static com.yammer.dropwizard.testing.JsonHelpers.fromJson;
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import java.io.IOException;
import java.util.Date;
import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;

public class TodoSerializationTest {

  public static final String FIXTURE = "fixtures/todo.json";



  private final Todo TODO = Todo.newBuilder().id("Test")
      .title("john@example.com").completed(false).build();

  @Test
  public void testSerializeToJson() throws IOException {
    assertThat(asJson(TODO)).isEqualTo(jsonFixture(FIXTURE));
  }

  @Test
  public void testLoadFromJson() throws IOException {
    Todo todo = fromJson(jsonFixture(FIXTURE), Todo.class);
    assertThat(todo).isEqualTo(TODO);
  }
}
