package app.controllers;

import app.entities.Item;
import app.persistence.ItemMapper;
import io.javalin.http.Context;

import java.util.List;

public class ItemOverviewController
{
    public static List<Item> loadItems(Context ctx)
    {
        var items = ItemMapper.getItems(ctx);
        return items;
    }
}
