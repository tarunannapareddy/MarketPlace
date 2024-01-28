package handlers;

import dao.ItemDAO;
import pojos.Item;
import pojos.Request.RemoveItemRequest;

public class RemoveItemHandler implements RequestHandler{
    private ItemDAO itemDAO;

    public RemoveItemHandler(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public Object handle(Object request) {
        RemoveItemRequest item = (RemoveItemRequest) request;
        return itemDAO.updateItemQuantity(item.itemId,item.quantity);
    }
}
