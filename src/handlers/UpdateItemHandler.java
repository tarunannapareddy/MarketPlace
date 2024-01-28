package handlers;

import dao.ItemDAO;
import pojos.Item;

public class UpdateItemHandler implements RequestHandler{
    private ItemDAO itemDAO;

    public UpdateItemHandler(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public Object handle(Object request) {
        Item item = (Item) request;
        return itemDAO.updateItemPrice(item.getItemId(), item.getSalePrice());
    }
}
