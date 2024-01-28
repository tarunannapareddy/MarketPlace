package handlers;

import dao.ItemDAO;

public class GetItemsHandler implements RequestHandler{
    ItemDAO itemDAO;

    public GetItemsHandler(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }


    @Override
    public Object handle(Object request) {
        Integer sellerId = (Integer) request;
        return itemDAO.getItemsBySellerId(sellerId);
    }
}
