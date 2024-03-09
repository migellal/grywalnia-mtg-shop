package pl.michalgellert.model;

public class MtgSource
{
    private String rawName;
    private String name;
    private boolean foil;
    private String comment;
    private Double price;
    private String contact;

    public MtgSource(String rawName, String name, boolean foil, String comment, Double price, String contact)
    {
        this.rawName = rawName;
        this.name = name;
        this.foil = foil;
        this.comment = comment;
        this.price = price;
        this.contact = contact;
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isFoil()
    {
        return foil;
    }

    public void setFoil(boolean foil)
    {
        this.foil = foil;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String toString()
    {
        return "MtgSource [name=" + name + ", foil=" + foil + ", comment=" + comment + ", price=" + price + "]";
    }
}
