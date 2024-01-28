package handlers;

import dao.ItemDAO;
import dao.SellerDAO;
import pojos.Item;
import pojos.Request.RateItemRequest;

public class RateItemHandler implements RequestHandler{

    private ItemDAO itemDAO;
    private SellerDAO sellerDAO;

    public RateItemHandler(ItemDAO itemDAO, SellerDAO sellerDAO) {
        this.itemDAO = itemDAO;
        this.sellerDAO = sellerDAO;
    }

    @Override
    public Object handle(Object request) {
        RateItemRequest rateItemRequest = (RateItemRequest)  request;
        Item item = itemDAO.getItem(rateItemRequest.itemId);
        return sellerDAO.updateReviewCounts(item.getSellerId(), rateItemRequest.feedback);
    }
}
