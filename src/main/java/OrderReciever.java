import Bakery.OrderFulfillment;
import Util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderReciever {

    private static void processOrder(List<String> userinput) {
        OrderFulfillment order = new OrderFulfillment(userinput);
        String bill = order.processOrderByProduct();
        System.out.println(bill);
    }

    public static void main(String[] args) {
        System.out.println(Constants.USER_INPUT_LABEL);
        Scanner consoleInput = new Scanner(System.in);
        List<String> userInput = new ArrayList<>();
        String line;

        while (consoleInput.hasNextLine()) {
            line = consoleInput.nextLine().replaceAll("\\s", "");
            if (line.isEmpty()) {
                break;
            }
            userInput.add(line);
        }
        processOrder(userInput);
    }
}
