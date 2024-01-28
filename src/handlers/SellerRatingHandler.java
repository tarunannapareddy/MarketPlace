package handlers;

import dao.SellerDAO;
import pojos.Seller;

public class SellerRatingHandler implements RequestHandler{
    private SellerDAO sellerDAO;

    public SellerRatingHandler(SellerDAO sellerDAO) {
        this.sellerDAO = sellerDAO;
    }

    @Override
    public Object handle(Object request) {
        Integer id = (Integer) request;
        Seller seller = sellerDAO.getSellerInfo(id);
        double total = seller.getNegativeReviewCount()+seller.getNegativeReviewCount();
        if(total ==0){
            return -1;
        }
        return seller.getPositiveReviewCount()/total;
    }
}
