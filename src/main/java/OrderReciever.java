import Bakery.OrderFullfillment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderReciever {

    private static void processOrder(List<String> userinput) {
        OrderFullfillment order = new OrderFullfillment(userinput);
        String bill = order.processOrderByProduct();
        System.out.println(bill);
    }

    public static void main(String[] args) {
        System.out.println("Please Place your Order below like quantiry,productcode eg:10,VS5 :");
        Scanner consoleInput = new Scanner(System.in);
        List<String> userinput = new ArrayList<String>();
        String line;

        while (consoleInput.hasNextLine()) {
            line = consoleInput.nextLine().replaceAll("\\s", "");
            if (line.isEmpty()) {
                break;
            }
            userinput.add(line);
        }
        processOrder(userinput);
    }
}
