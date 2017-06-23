package c4e.aiu.test.securestore.test;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;

@WebServlet("/trySpringConnector")
public class TrySpringConnector extends HttpServlet {
  private static final long serialVersionUID = -5462851083156674211L;

  @Override
  public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
    CloudFactory cloudFactory = new CloudFactory();
    Cloud cloud = cloudFactory.getCloud();
    String serviceId = "sample-securestore";
    DataSource dataSource = cloud.getServiceConnector(serviceId, DataSource.class, null /* default config */);
    try (Connection connection = dataSource.getConnection()) {

      CallableStatement callableStatement = connection.prepareCall("{call SYS.USER_SECURESTORE_RETRIEVE(?,?,?,?)}");
      callableStatement.setString(1, "store");
      callableStatement.setBoolean(2, false);
      callableStatement.setString(3, "key");
      callableStatement.registerOutParameter(4, Types.VARBINARY);

      callableStatement.executeUpdate();

      byte[] bytes = callableStatement.getBytes(4);

      if (null == bytes) {
        response.getWriter().println("null");
      } else {
        String returnValue = new String(bytes);
        response.getWriter().println(returnValue);
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
