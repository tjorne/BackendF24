package app.entities;

public class Item
{
    private int id;
    private String title;
    private String author;
    private String body;
    private String timestamp;
    private int price;

    public Item(int id, String title, String author, String body, String timestamp, int price)
    {
        this.id = id;
        this.title = title;
        this.author = author;
        this.body = body;
        this.timestamp = timestamp;
        this.price = price;
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getBody()
    {
        return body;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public int getPrice()
    {
        return price;
    }
}
