package pojos.Request;

import java.io.Serializable;

public class RateItemRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    public String itemId;
    public boolean feedback;

    public RateItemRequest(String itemId, boolean feedback) {
        this.itemId = itemId;
        this.feedback = feedback;
    }
}
