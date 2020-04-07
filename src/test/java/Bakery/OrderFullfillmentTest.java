package Bakery;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderFullfillmentTest {
    @Test
    public void validateSingleOrder() {
        //GB-(11,9,5,3) 37
        List<String> input = new ArrayList<>(Arrays.asList("37,GB"));
        OrderFullfillment order = new OrderFullfillment(input);
        StringBuffer expected = new StringBuffer().append("37GB $128.95").append("\n\t2 x11$35.99").append("\n\t1 x9$30.99").append("\n\t2 x3$12.99\n");
        assertEquals(expected.toString(), order.processOrderByProduct());
    }

    @Test
    public void validateMultipleOrders() {
        List<String> input = new ArrayList<>(Arrays.asList("10,VS5", "14,MB11", "13,CF"));
        OrderFullfillment order = new OrderFullfillment(input);
        StringBuffer expected = new StringBuffer().append("10VS5 $17.98").append("\n\t2 x5$8.99").append("\n14MB11 $54.8\n\t1 x8$24.95\n\t3 x2$9.95\n13CF $25.85\n\t2 x5$9.95\n\t1 x3$5.95\n");
        assertEquals(expected.toString(), order.processOrderByProduct());
    }

    @Test
    public void invalidOrder() {
        //(6,4,2)15-RT
        List<String> input = new ArrayList<>(Arrays.asList("15,RT"));
        OrderFullfillment order = new OrderFullfillment(input);
        String expected = "\nRT : Unable to Process this quantity with available packets";
        assertEquals(expected, order.processOrderByProduct());
    }

    @Test
    public void noAvailablePacketsForProduct() {
        List<String> input = new ArrayList<>(Arrays.asList("15,SB"));
        OrderFullfillment order = new OrderFullfillment(input);
        String expected = "\nSB : This Products is not available";
        assertEquals(expected, order.processOrderByProduct());
    }

    @Test
    public void invalidInput() {
        List<String> input = new ArrayList<>(Arrays.asList("SB"));
        OrderFullfillment order = new OrderFullfillment(input);
        String expected = "\nSB : Please enter valid productcode and quantity";
        assertEquals(expected, order.processOrderByProduct());
    }
}
