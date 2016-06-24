package oms;

import dbc.DBConnection;
import org.apache.activemq.transport.stomp.StompConnection;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controller of the web-service
 */
@RestController
public class Controller {

    /**
     * Creates a new order and inserts it into the table.
     * Order details are getting from JSON-file, which was sent via POST-method
     * @param order - Order instance
     */
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public void CreateOrder(@RequestBody Order order) {
        if (order.getStatus().equals("payed")) {
            String sql = "insert into orders (id, description, status, empid) values (" + order.getOrder_id() +
                    ", 'order_" + order.getOrder_id() + "', '" + order.getStatus() + "', 1)";
            try {
                Connection connect = DBConnection.connect();
                connect.createStatement().execute(sql);
                System.out.println("New order has been inserted into DB");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the order status and/or it's worker ID
     * Order details are getting from JSON-file, which was sent via POST-method
     * @param order - Order instance
     */
    @RequestMapping(value = "/changedorder", method = RequestMethod.POST)
    public void ChangeOrder(@RequestBody Order order) throws Exception {
        Connection connect = DBConnection.connect();
        if (order.getStatus().equals("payed")) {
            String sql = "insert into orders (id, description, status, empid) values (" + order.getOrder_id() +
                    ", 'order_" + order.getOrder_id() + "', '" + order.getStatus() + "', 1)";
            try {
                //Connection connect = DBConnection.connect();
                connect.createStatement().execute(sql);
                System.out.println("New order has been inserted into DB: " +
                        order.getOrder_id() + " - " + order.getStatus());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (order.getStatus().equals("assigned")) {

            String sql = "select status from orders where id = " + order.getOrder_id();
            String oStatus = "";

            try {
                ResultSet rs = connect.createStatement().executeQuery(sql);
                while (rs.next())
                    oStatus = rs.getString(1);
            } catch (Exception ex) {
            }

            if (!oStatus.equals(order.getStatus())) {
                sql = "update orders set empid = '" + order.getWorker_id() + "', status = '" + order.getStatus() + "' " +
                        "where id = " + order.getOrder_id() + " and status = 'payed'";
                try {
                    //Connection connect = DBConnection.connect();
                    connect.createStatement().execute(sql);
                    System.out.println("The order has been updated in DB");

                    JSONObject json = new JSONObject();
                    json.put("order_id", Integer.toString(order.getOrder_id()));
                    json.put("status", order.getStatus());
                    json.put("worker_id", order.getWorker_id());
                    Send(json.toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        connect.close();
    }

    /**
     * Sends JSON to the queue on amq
     * @param json - JSON
     * @throws Exception
     */
    private void Send(String json) throws Exception {
        StompConnection connection = new StompConnection();
        connection.open("192.168.1.2", 61613);
        connection.connect("admin", "admin");
        connection.send("/queue/OrderStatus", json);
        connection.disconnect();
    }
}
