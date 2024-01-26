package pojos.Request;

import java.io.Serializable;

public class BuyerRequest implements  Serializable{
        private static final long serialVersionUID = 1L;
        public Integer buyerId;



        public BuyerRequest(Integer buyerId) {
            this.buyerId = buyerId;

        }
}
