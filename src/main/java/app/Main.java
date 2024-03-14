package app;

import app.config.ThymeleafConfig;
import app.controllers.ItemCreateController;
import app.controllers.UserController;
import app.controllers.ItemOverviewController;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.Map;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{
    public static void main(String[] args)
    {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        //  Routing


        app.get("/", ctx ->  ctx.render("index.html"));

        app.post("/login", ctx ->
        {
            UserController.login(ctx);
        });

        app.get("/welcome", ctx ->
        {
            var items = ItemOverviewController.loadItems(ctx);
            ctx.render("/welcome.html", Map.of("items", items));
        });

        // Create item redirect
        app.get("/createitem", ctx -> {
           ctx.render("createitem.html");
        });

        app.post("/submitItem", ctx ->
        {
           ItemCreateController.createItem(ctx);
        });
    }
}