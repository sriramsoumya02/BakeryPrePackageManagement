package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    Product product;

    @BeforeEach
    public void init() {
        product = new Product("VegemiteScroll", "VS5");
    }

    @Test
    public void validateProductWhenNoPacketsAvailable() {
        List<Integer> packets = product.sortedPacketSizelist();
        assertAll(() -> assertEquals("VegemiteScroll", product.getProductName()),
                () -> assertEquals("VS5", product.getProductCode()),
                () -> assertTrue(packets.isEmpty()),
                () -> assertEquals(0.0, product.getPriceofPacket(10)),
                () -> assertFalse(product.removePacket(10))
        );

    }

    @Test
    public void validateProductAfterSettingIt() {
        product.setProductName("BlueberryMuffin");
        product.setProductCode("MB11");
        assertAll(() -> assertEquals("BlueberryMuffin", product.getProductName()),
                () -> assertEquals("MB11", product.getProductCode()));

    }

    @Test
    public void validateProductAfterAddingAndRemovingPackets() {
        product.addPacket(new PacketPrice(5, 16.95));
        product.addPacket(new PacketPrice(2, 9.95));
        product.addPacket(new PacketPrice(8, 24.95));
        List<Integer> expectedPacketlist = new ArrayList<>(Arrays.asList(8, 5, 2));
        List<Integer> expectedPacketlist1 = new ArrayList<>(Arrays.asList(8, 2));
        assertAll(() -> assertEquals(9.95, product.getPriceofPacket(2)),
                () -> assertEquals(expectedPacketlist, product.sortedPacketSizelist()),
                () -> assertTrue(product.removePacket(5)),
                () -> assertEquals(expectedPacketlist1, product.sortedPacketSizelist()),
                () -> product.addPacket(new PacketPrice(5, 16.95)),
                () -> assertEquals(expectedPacketlist, product.sortedPacketSizelist())
        );

    }

}
