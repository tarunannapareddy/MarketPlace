package pojos;

public class Seller {
    Integer sellerId;
    String sellerName;
    Integer positiveReviewCount;
    Integer negativeReviewCount;
    Integer totalSold;

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Integer getPositiveReviewCount() {
        return positiveReviewCount;
    }

    public void setPositiveReviewCount(Integer positiveReviewCount) {
        this.positiveReviewCount = positiveReviewCount;
    }

    public Integer getNegativeReviewCount() {
        return negativeReviewCount;
    }

    public void setNegativeReviewCount(Integer negativeReviewCount) {
        this.negativeReviewCount = negativeReviewCount;
    }

    public Integer getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(Integer totalSold) {
        this.totalSold = totalSold;
    }
}
