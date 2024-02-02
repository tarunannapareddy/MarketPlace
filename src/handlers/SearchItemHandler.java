package handlers;

import dao.ItemDAO;
import pojos.Request.SearchItemRequest;
import pojos.Session;

public class SearchItemHandler implements RequestHandler{
    private ItemDAO itemDAO;

    public SearchItemHandler(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public Object handle(Object request, Session sessionId) {
        SearchItemRequest searchItemRequest = (SearchItemRequest) request;
        return itemDAO.getItemsByCategoryAndKeywords(searchItemRequest.category, searchItemRequest.keyWords);
    }
}
