package handlers;

import Exceptions.InvalidDataException;
import dao.CartDAO;
import dao.ItemDAO;
import pojos.Item;
import pojos.Request.UpdateCartRequest;
import pojos.Session;

public class UpdateCartHandler implements RequestHandler{
    private CartDAO cartDAO;

    private ItemDAO itemDAO;

    public UpdateCartHandler(CartDAO cartDAO, ItemDAO itemDAO) {
        this.cartDAO = cartDAO;
        this.itemDAO = itemDAO;
    }

    @Override
    public Object handle(Object request, Session session) throws InvalidDataException {
        UpdateCartRequest updateCartRequest = (UpdateCartRequest) request;
        if(!session.getSessionId().equals(updateCartRequest.getBuyerId())){
            throw  new InvalidDataException("User Not Authorised");
        }
        int cartId = cartDAO.getCart(updateCartRequest.getBuyerId());
        if(updateCartRequest.isSaveCart()){
            return true;
        } else if(updateCartRequest.isResetCart()){
            return cartDAO.deleteCart(cartId);
        }

        Item item = itemDAO.getItem(updateCartRequest.getItemId());
        if(item.getQuantity() < updateCartRequest.getQuantity()){
            return false;
        }
        return cartDAO.updateCartQuantity(cartId, updateCartRequest.getItemId(), updateCartRequest.getQuantity());
    }
}
