package open.hashmap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class OpenAddressingMapTest {
    private OpenAddressingMap map;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void initDefaultMap() {
        map = new OpenAddressingHashMap();
    }

    @Test
    public void mapSizeShouldReturn4() {
        map.put(2, 12L);
        map.put(4, 12L);
        map.put(8, 12L);
        map.put(16, 12L);

        assertThat(map.size(), equalTo(4));
    }

    @Test
    public void getShouldReturnActualValue() {
        map.put(2, 12L);
        map.put(4, 7L);

        assertThat(map.get(2), equalTo(12L));
        assertThat(map.get(4), equalTo(7L));
    }

    @Test
    public void getShouldThrowNoSuchElementException() {
        map.put(4, 6L);

        exception.expect(NoSuchElementException.class);
        exception.expectMessage("Key 8 not found in hash table.");
        map.get(8);
    }

    @Test
    public void putShouldRewriteOlderValue(){
        map.put(4, 12L);
        map.put(4, 8L);

        assertThat(map.size(),equalTo(1));
        assertThat(map.get(4),equalTo(8L));
    }
}
