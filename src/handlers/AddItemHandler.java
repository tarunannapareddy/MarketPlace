package handlers;

import Exceptions.InvalidDataException;
import dao.ItemDAO;
import pojos.Item;
import pojos.Session;

import java.util.Random;

public class AddItemHandler implements RequestHandler{

    ItemDAO itemDAO;

    public AddItemHandler(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public Object handle(Object request, Session session) throws InvalidDataException {
        Item item = (Item) request;
        if(!session.getSessionId().equals(item.getSellerId())){
            throw new InvalidDataException("User not Authorised");
        }
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000000);
        item.setItemId(item.getName()+"_"+item.getCategory()+"_"+rand_int1);
        return itemDAO.addItem(item);
    }
}
