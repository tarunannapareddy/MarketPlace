package handlers;

import Exceptions.InvalidDataException;
import dao.FeedBackDAO;
import dao.ItemDAO;
import dao.SellerDAO;
import pojos.Item;
import pojos.Request.RateItemRequest;
import pojos.Session;

public class RateItemHandler implements RequestHandler{

    private ItemDAO itemDAO;
    private SellerDAO sellerDAO;

    private FeedBackDAO feedBackDAO;

    public RateItemHandler(ItemDAO itemDAO, SellerDAO sellerDAO, FeedBackDAO feedBackDAO) {
        this.itemDAO = itemDAO;
        this.sellerDAO = sellerDAO;
        this.feedBackDAO = feedBackDAO;
    }

    @Override
    public Object handle(Object request, Session session) throws InvalidDataException {
        RateItemRequest rateItemRequest = (RateItemRequest)  request;
        if(!session.getSessionId().equals(rateItemRequest.getBuyerId())){
            throw new InvalidDataException("User not Authorised");
        }
        Item item = itemDAO.getItem(rateItemRequest.itemId);
        if(feedBackDAO.doesFeedbackExist(rateItemRequest.buyerId, rateItemRequest.itemId)) {
            return false;
        }
        feedBackDAO.saveFeedback(rateItemRequest.itemId, rateItemRequest.buyerId, rateItemRequest.feedback);
        return sellerDAO.updateReviewCounts(item.getSellerId(), rateItemRequest.feedback);
    }
}
