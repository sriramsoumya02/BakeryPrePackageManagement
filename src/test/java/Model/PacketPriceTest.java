package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PacketPriceTest {
    @Test
    public void validatePacketPrice() {
        PacketPrice packet = new PacketPrice(2, 5.99);
        assertAll(() -> assertEquals(2, packet.getNoItems()),
                () -> assertEquals(5.99, packet.getCost()),
                () -> packet.setNoItems(3),
                () -> assertEquals(3, packet.getNoItems()),
                () -> packet.setCost(6.99),
                () -> assertEquals(6.99, packet.getCost()));
    }
}
