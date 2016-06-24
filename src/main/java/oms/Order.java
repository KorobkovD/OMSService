package oms;

/**
 * Class which represents the order
 */
public class Order {
    private int order_id;
    private String status;
    private int worker_id;

    /**
     * Dummy constructor
     */
    public Order() { }

    /**
     * Order ID setter
     * @param order_id - Order ID
     */
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    /**
     * Order status setter
     * @param status - Order status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Order's worker setter
     * @param worker_id - Worker ID
     */
    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

    /**
     * Order ID getter
     */
    public int getOrder_id() {
        return order_id;
    }

    /**
     * Order status getter
     */
    public String getStatus() {
        return status;
    }

    /**
     * Order's worker getter
     */
    public int getWorker_id() {
        return worker_id;
    }
}