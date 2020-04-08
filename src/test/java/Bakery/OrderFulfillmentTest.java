package Bakery;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderFulfillmentTest {
    @Test
    public void validateSingleProductOrder() {
        List<String> input = new ArrayList<>(Arrays.asList("37,GB"));
        OrderFulfillment order = new OrderFulfillment(input);
        StringBuffer expected = new StringBuffer().append("\n37 GB $128.95").append("\n\t2 x 11 $35.99").append("\n\t1 x 9 $30.99").append("\n\t2 x 3 $12.99");
        assertEquals(expected.toString(), order.processOrderByProduct());
    }

    @Test
    public void validateMultipleProductsOrders() {
        List<String> input = new ArrayList<>(Arrays.asList("10,VS5", "14,MB11", "13,CF"));
        OrderFulfillment order = new OrderFulfillment(input);
        StringBuffer expected = new StringBuffer().append("\n10 VS5 $17.98").append("\n\t2 x 5 $8.99").append("\n14 MB11 $54.8\n\t1 x 8 $24.95\n\t3 x 2 $9.95\n13 CF $25.85\n\t2 x 5 $9.95\n\t1 x 3 $5.95");
        assertEquals(expected.toString(), order.processOrderByProduct());
    }

    @Test
    public void validateInvalidOrder() {
        List<String> input = new ArrayList<>(Arrays.asList("15,RT"));
        OrderFulfillment order = new OrderFulfillment(input);
        String expected = "\nRT : Unable to process this quantity with available packet sizes";
        assertEquals(expected, order.processOrderByProduct());
    }

    @Test
    public void validateNoAvailablePacketsForProduct() {
        List<String> input = new ArrayList<>(Arrays.asList("15,SB"));
        OrderFulfillment order = new OrderFulfillment(input);
        String expected = "\nSB : This product is not available";
        assertEquals(expected, order.processOrderByProduct());
    }

    @Test
    public void validateInvalidInput() {
        List<String> input = new ArrayList<>(Arrays.asList("SB"));
        OrderFulfillment order = new OrderFulfillment(input);
        String expected = "\nSB : Please enter valid Productcode and Quantity";
        assertEquals(expected, order.processOrderByProduct());
    }
}
