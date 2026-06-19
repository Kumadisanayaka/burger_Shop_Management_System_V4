package model;
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

    public Burger(String orderId, String customerId, String customarName, int burgerQty, int status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customarName = customarName;
        this.burgerQty = burgerQty;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomarName() {
        return customarName;
    }

    public void setCustomarName(String customarName) {
        this.customarName = customarName;
    }

    public int getBurgerQty() {
        return burgerQty;
    }

    public void setBurgerQty(int burgerQty) {
        this.burgerQty = burgerQty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}

