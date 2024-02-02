package handlers;

import Exceptions.InvalidDataException;
import dao.ItemDAO;
import pojos.Item;
import pojos.Session;

public class UpdateItemHandler implements RequestHandler{
    private ItemDAO itemDAO;

    public UpdateItemHandler(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public Object handle(Object request, Session session) throws InvalidDataException {
        Item item = (Item) request;
        if(!session.getSessionId().equals(item.getSellerId())){
            throw  new InvalidDataException("User Not Authorised");
        }
        return itemDAO.updateItemPrice(item.getItemId(), item.getSalePrice());
    }
}
