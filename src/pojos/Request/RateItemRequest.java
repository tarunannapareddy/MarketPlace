package pojos.Request;

import java.io.Serializable;

public class RateItemRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    public int buyerId;

    public String itemId;

    public boolean feedback;

    public RateItemRequest(int buyerId, String itemId, boolean feedback) {
        this.buyerId = buyerId;
        this.itemId = itemId;
        this.feedback = feedback;
    }
}
