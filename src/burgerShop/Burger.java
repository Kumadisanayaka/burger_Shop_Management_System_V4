package burgerShop;
public class Burger {
    public static final int UNIT_PRICE = 500;
    
    public static final int PREPARING = 0;
    public static final int DELIVERED = 1;
    public static final int CANCELED = 2;
    
    private String orderId;
    private String customerId;
    private String customarName;
    private int burgerQty;
    private int status;

    public Burger() {
        orderId = null;
        customerId = null;
        customarName = null;
        burgerQty = 0;
        status = 0;
    }

    public Burger(String orderId, String customerId, String customarName, int burgerQty) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customarName = customarName;
        this.burgerQty = burgerQty;
        this.status = 0;
    }
    
    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the customarName
     */
    public String getCustomarName() {
        return customarName;
    }

    /**
     * @param customarName the customarName to set
     */
    public void setCustomarName(String customarName) {
        this.customarName = customarName;
    }

    /**
     * @return the burgerQty
     */
    public int getBurgerQty() {
        return burgerQty;
    }

    /**
     * @param burgerQty the burgerQty to set
     */
    public void setBurgerQty(int burgerQty) {
        this.burgerQty = burgerQty;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
