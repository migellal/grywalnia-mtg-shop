package pl.michalgellert.model;

import io.magicthegathering.javasdk.resource.Card;

public class ExtendedCard extends Card
{
    private String rawName;

    private Double price;

    private Double priceInEuro;

    private boolean foil;

    private String comment;

    private String contact;

    public ExtendedCard()
    {
        // GSON needs this
    }

    public ExtendedCard(Card card)
    {
        setId(card.getId());
        setLayout(card.getLayout());
        setName(card.getName());
        setNames(card.getNames());
        setManaCost(card.getManaCost());
        setCmc(card.getCmc());
        setColors(card.getColors());
        setColorIdentity(card.getColorIdentity());
        setType(card.getType());
        setSupertypes(card.getSupertypes());
        setTypes(card.getTypes());
        setSubtypes(card.getSubtypes());
        setRarity(card.getRarity());
        setText(card.getText());
        setOriginalText(card.getOriginalText());
        setFlavor(card.getFlavor());
        setArtist(card.getArtist());
        setNumber(card.getNumber());
        setPower(card.getPower());
        setToughness(card.getToughness());
        setLoyalty(card.getLoyalty());
        setMultiverseid(card.getMultiverseid());
        setVariations(card.getVariations());
        setImageName(card.getImageName());
        setWatermark(card.getWatermark());
        setBorder(card.getBorder());
        setTimeshifted(card.isTimeshifted());
        setHand(card.getHand());
        setLife(card.getLife());
        setReserved(card.isReserved());
        setReleaseDate(card.getReleaseDate());
        setStarter(card.isStarter());
        setSet(card.getSet());
        setSetName(card.getSetName());
        setPrintings(card.getPrintings());
        setImageUrl(card.getImageUrl());
        setLegalities(card.getLegalities());
        setPriceHigh(card.getPriceHigh());
        setPriceMid(card.getPriceMid());
        setPriceLow(card.getPriceLow());
        setOnlinePriceHigh(card.getOnlinePriceHigh());
        setOnlinePriceMid(card.getOnlinePriceMid());
        setOnlinePriceLow(card.getOnlinePriceLow());
        setRulings(card.getRulings());
        setForeignNames(card.getForeignNames());
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Double getPriceInEuro()
    {
        return priceInEuro;
    }

    public void setPriceInEuro(Double priceInEuro)
    {
        this.priceInEuro = priceInEuro;
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

    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }
}
