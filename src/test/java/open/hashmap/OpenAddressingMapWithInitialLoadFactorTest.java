package open.hashmap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class OpenAddressingMapWithInitialLoadFactorTest {
    private OpenAddressingMap map;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void initDefaultMap() {
        map = new OpenAddressingHashMap(4, 0.5);
    }

    @Test
    public void mapSizeShouldReturn4AndCapacity8() {
        map.put(2, 12L);
        map.put(4, 12L);
        map.put(8, 12L);
        map.put(16, 12L);

        assertThat(map.size(), equalTo(4));
        assertThat(map.capacity(), equalTo(8));
    }

    @Test
    public void mapCapacityShouldIncreaseAfterLoadFactorOverloaded() {
        map.put(2, 12L);
        assertThat(map.capacity(), equalTo(4));
        map.put(4, 12L);
        assertThat(map.capacity(), equalTo(4));
        map.put(2, 12L);
        map.put(16, 12L);
        assertThat(map.capacity(), equalTo(8));
    }

    @Test
    public void initMapShouldThrowIllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Load Factor should be greater then 0.25");
        new OpenAddressingHashMap(0, 0);
    }

    @Test
    public void initMapShouldThrowIllegalArgumentExceptionCase2() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Capacity should be greater then 1");
        new OpenAddressingHashMap(0, 0.3);
    }
}
