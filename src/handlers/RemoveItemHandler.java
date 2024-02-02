package handlers;

import Exceptions.InvalidDataException;
import dao.ItemDAO;
import pojos.Item;
import pojos.Request.RemoveItemRequest;
import pojos.Session;

public class RemoveItemHandler implements RequestHandler{
    private ItemDAO itemDAO;

    public RemoveItemHandler(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public Object handle(Object request, Session session) throws InvalidDataException {
        RemoveItemRequest item = (RemoveItemRequest) request;
        Item itemInfo = itemDAO.getItem(item.itemId);
        if(!session.getSessionId().equals(itemInfo.getSellerId())){
            throw new InvalidDataException("User Not authorised");
        }
        return itemDAO.updateItemQuantity(item.itemId,item.quantity);
    }
}
