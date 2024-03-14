package app.controllers;

import app.persistence.ItemMapper;
import io.javalin.http.Context;

public class ItemCreateController
{

    public static void createItem(Context ctx)
    {
        ItemMapper.createNewItem(ctx);
        ctx.redirect("/welcome");
    }
}
