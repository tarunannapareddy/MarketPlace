package handlers;

import dao.ItemDAO;
import pojos.Item;

import java.util.Random;

public class AddItemHandler implements RequestHandler{

    ItemDAO itemDAO;

    public AddItemHandler(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public Object handle(Object request) {
        Item item = (Item) request;
        item.setItemId(item.getName()+" "+item.getCategory()+" "+Math.random());
        return itemDAO.addItem(item);
    }
}
