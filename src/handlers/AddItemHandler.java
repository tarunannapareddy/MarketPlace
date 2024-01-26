package handlers;

import dao.ItemDAO;
import pojos.Item;

public class AddItemHandler implements RequestHandler{

    ItemDAO itemDAO;
    @Override
    public Object handle(Object request) {
        Item item = (Item) request;

    }
}
