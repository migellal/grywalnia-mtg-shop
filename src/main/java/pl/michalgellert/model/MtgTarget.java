package pl.michalgellert.model;

public class MtgTarget extends MtgSource
{
    private String description;
    public MtgTarget(String name, String description, boolean foil, String comment, Double price)
    {
        super(name, foil, comment, price);
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
