package app.persistence;

import app.entities.Item;
import app.entities.User;
import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemMapper
{
    public static List<Item> getItems(Context ctx)
    {
        List<Item> items = new ArrayList<Item>();

        int lowerBound = 0;
        int upperBound = 24;

        String orderBy = getOrder(ctx);

        String sql = "select * from public.\"messages\" where id >= ? and id < ?"+orderBy;

        try (Connection connection = ConnectionPool.getInstance().getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, lowerBound);
                ps.setInt(2, upperBound);
                ResultSet rs = ps.executeQuery();

                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String body = rs.getString("body");
                    String timeCreated = rs.getTimestamp("time_created").toString();
                    int score = rs.getInt("score");

                    Item item = new Item(id, title, author, body, timeCreated, score);
                    items.add(item);
                }
            }

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return items;
    }

    // ordering should probably be done clientside
    private static String getOrder(Context ctx)
    {
        String result = " order by ";
        String orderBy = ctx.formParam("orderBy") == null ? "" : ctx.formParam("orderBy");

        switch (orderBy)
        {
            default:
            case "NEWEST":
                result+="time_created DESC";
                break;
            case "OLDEST":
                result+="time_created ASC";
                break;
            case "BEST":
                result+="score DESC";
                break;
            case "WORST":
                result+="score ASC";
        }

        return result;
    }


    public static void createNewItem(Context ctx)
    {
        String sql = "INSERT INTO public.\"messages\" (title, author, body, score) VALUES (?,?,?,?)";

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                String title    = ctx.formParam("title");

                User user = ctx.sessionAttribute("currentUser");

                String author   = user.getUsername();
                String body     = ctx.formParam("body");
                int price       = Integer.parseInt(ctx.formParam("price"));

                ps.setString(1, title);
                ps.setString(2, author);
                ps.setString(3, body);
                ps.setInt(4, price);

                ps.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
