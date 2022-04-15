import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {

    static Location mskLen = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
    static Location nyAve10 = new Location("New York", Country.USA, " 10th Avenue", 32);
    static Location nyNull = new Location("New York", Country.USA, null,  0);
    static Location mskNull = new Location("Moscow", Country.RUSSIA,null, 0);

    @ParameterizedTest
    @MethodSource("source")
    void byIpTest(String arg, Location expected) {
        GeoServiceImpl gs = new GeoServiceImpl();
        Location[] locationsArr = new Location[4];
        locationsArr[0] = mskLen;
        locationsArr[1] = nyAve10;
        locationsArr[2] = nyNull;
        locationsArr[3] = mskNull;


        Location location = gs.byIp(arg);

        for (int i = 0; i < locationsArr.length; i++) {
            if (location.equals(locationsArr[i])) {
                location = locationsArr[i];
                assertEquals(expected, location);
            }
        }
    }

    private static Stream<Arguments> source() {
return Stream.of(Arguments.of("172.0.32.11", mskLen),
                 Arguments.of("96.44.183.149", nyAve10),
                 Arguments.of("96.47.390.251", nyNull),
                 Arguments.of("172.0.54.39", mskNull));
    }
}