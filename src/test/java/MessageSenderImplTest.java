import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MessageSenderImplTest {

    MessageSenderImpl sut;


    @Test
    void send_msg_if_IP_starts_with_172() {
        GeoServiceImpl gs = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(gs.byIp("172.1.23.45"))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        LocalizationServiceImpl ls = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(ls.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        sut = new MessageSenderImpl(gs, ls);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.1.23.45");

        String result = sut.send(headers);
        String expected = "Добро пожаловать";

        assertEquals(expected, result);
    }

    @Test
    void send_msg_if_IP_starts_with_96() {
        GeoServiceImpl gs = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(gs.byIp("96.1.23.45"))
                .thenReturn(new Location("New York", Country.USA, null,  0));

        LocalizationServiceImpl ls = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(ls.locale(Country.USA))
                .thenReturn("Welcome");

        sut = new MessageSenderImpl(gs, ls);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.1.23.45");

        String result = sut.send(headers);
        String expected = "Welcome";

        assertEquals(expected, result);
    }

    @Test
    void send_msg_if_IP_equals_17203211() {
        GeoServiceImpl gs = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(gs.byIp("172.0.32.11"))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        LocalizationServiceImpl ls = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(ls.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        sut = new MessageSenderImpl(gs, ls);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.MOSCOW_IP);

        String result = sut.send(headers);
        String expected = "Добро пожаловать";

        assertEquals(expected, result);
    }

    @Test
    void send_msg_if_IP_equals_127001() {
        GeoServiceImpl gs = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(gs.byIp("96.44.183.149"))
                .thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));

        LocalizationServiceImpl ls = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(ls.locale(Country.USA))
                .thenReturn("Welcome");

        sut = new MessageSenderImpl(gs, ls);
        final Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.NEW_YORK_IP);

        String result = sut.send(headers);
        String expected = "Welcome";

        assertEquals(expected, result);
    }
}