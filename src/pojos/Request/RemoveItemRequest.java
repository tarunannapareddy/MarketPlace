package pojos.Request;

import java.io.Serializable;

public class RemoveItemRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    public String itemId;

    public Integer quantity;

    public RemoveItemRequest(String itemId, Integer quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
