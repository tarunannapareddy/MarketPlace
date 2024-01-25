package pojos;

public class Buyer {
    Integer buyerId;
    String name;
    Integer itemsPurchased;

    public void setBuyerId(Integer buyerId){this.buyerId=buyerId;}

    public void setName(String name){this.name=name;}

    public String getName(){return name;}

    public void setItemsPurchased(Integer itemsPurchased){this.itemsPurchased=itemsPurchased;}

    public Integer getItemsPurchased(){return itemsPurchased;}

}
