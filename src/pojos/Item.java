package pojos;

import java.io.Serializable;
import java.util.List;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    private String itemId;
    private String name;
    private Integer category;
    private String condition;
    private Integer quantity;
    private Double salePrice;
    private Integer sellerId;
    private String[] keyWords;

    public Item(Integer id, String itemId, String name, Integer category, String condition, Integer quantity, Double salePrice, Integer sellerId, String[] keyWords) {
        this.id = id;
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.condition = condition;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.sellerId = sellerId;
        this.keyWords = keyWords;
    }

    public Item(String name, Integer category, String condition, Integer quantity, Double salePrice, Integer sellerId, String[] keyWords) {
        this.name = name;
        this.category = category;
        this.condition = condition;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.sellerId = sellerId;
        this.keyWords = keyWords;
    }

    public Item(String itemId, Double salePrice) {
        this.itemId = itemId;
        this.salePrice = salePrice;
    }


    public Integer getId() {
        return id;
    }

    public String getItemId() {
        return itemId;
    }


    public String getName() {
        return name;
    }

    public Integer getCategory() {
        return category;
    }

    public String getCondition() {
        return condition;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public String[] getKeyWords() {
        return keyWords;
    }
}
