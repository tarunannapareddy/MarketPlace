package handlers;

import dao.ItemDAO;
import pojos.Session;

public class GetItemsHandler implements RequestHandler{
    ItemDAO itemDAO;

    public GetItemsHandler(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }


    @Override
    public Object handle(Object request, Session sessionId) {
        Integer sellerId = (Integer) request;
        return itemDAO.getItemsBySellerId(sellerId);
    }
}
