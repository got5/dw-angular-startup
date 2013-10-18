package net.atos.dwAngularStartup;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.bundles.DBIExceptionsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseFactory;
import com.yammer.dropwizard.views.ViewBundle;
import net.atos.dwAngularStartup.dao.TodoDao;
import net.atos.dwAngularStartup.resources.HtmlPageResource;
import net.atos.dwAngularStartup.resources.TodoResource;

public class TodoService extends Service<TodoServiceConfiguration> {

  public static void main(String[] arguments) throws Exception {
    new TodoService().run(arguments);
  }

  public TodoService() {
    addBundle(new ViewBundle());
    addBundle(new DBIExceptionsBundle());
    addBundle(new AssetsBundle("/app/","/"));
  }

  @Override
  protected void initialize(TodoServiceConfiguration configuration,
                            Environment environment) throws Exception {

    final DatabaseFactory factory = new DatabaseFactory(environment);
    Database database = factory.build(configuration.getDatabase(), "todo");

    final TodoDao dao = database.onDemand(TodoDao.class);
    dao.createTableIfNotExists();

      environment.addResource(new HtmlPageResource());
      environment.addResource(new TodoResource(dao));
  }
}
