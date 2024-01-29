package handlers;

import dao.CartDAO;
import dao.ItemDAO;
import pojos.Item;
import pojos.Request.UpdateCartRequest;

public class UpdateCartHandler implements RequestHandler{
    private CartDAO cartDAO;

    private ItemDAO itemDAO;

    public UpdateCartHandler(CartDAO cartDAO, ItemDAO itemDAO) {
        this.cartDAO = cartDAO;
        this.itemDAO = itemDAO;
    }

    @Override
    public Object handle(Object request) {
        UpdateCartRequest updateCartRequest = (UpdateCartRequest) request;
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
