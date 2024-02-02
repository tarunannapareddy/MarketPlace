package handlers;

import dao.SellerDAO;
import pojos.Seller;
import pojos.Session;

public class SellerRatingHandler implements RequestHandler{
    private SellerDAO sellerDAO;

    public SellerRatingHandler(SellerDAO sellerDAO) {
        this.sellerDAO = sellerDAO;
    }

    @Override
    public Object handle(Object request, Session sessionId) {
        Integer id = (Integer) request;
        Seller seller = sellerDAO.getSellerInfo(id);
        double total = seller.getNegativeReviewCount()+seller.getPositiveReviewCount();
        if(total ==0){
            return -1;
        }
        return seller.getPositiveReviewCount()/total;
    }
}
