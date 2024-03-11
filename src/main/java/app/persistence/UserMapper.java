package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;
import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper
{
    public static User login(Context ctx) throws DatabaseException
    {
        User user;

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        String sql = "select * from public.\"user\" where user_name = ? and password = ?";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, ctx.formParam("user_name"));
                ps.setString(2, ctx.formParam("password"));
                ResultSet rs = ps.executeQuery();

                if (rs.next())
                {
                    // INITIALIZE user object
                    int id = rs.getInt("id");
                    String username = rs.getString("user_name");
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    user = new User(id, username, password, role);
                }
                else
                {
                    throw new DatabaseException("Fejl i input");
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return user;
    }
}
