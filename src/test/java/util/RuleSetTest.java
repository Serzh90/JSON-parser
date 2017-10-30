package util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RuleSetTest {

    @Test
    public void ruleSet() {
        RuleSet ruleSet = new RuleSet("$price > 20 AND $description ",
                Arrays.asList("apple", "banana"),
                "red");
        assertEquals(ruleSet.getResult(), "red");
        assertTrue(ruleSet.getContains() != null && !ruleSet.getContains().isEmpty());
    }

    @Test
    public void testWithPriceOnly() {
        RuleSet ruleSet = new RuleSet("$price = 0", null, "red");
        Record record = new Record(1, 0.0F, "");
        assertTrue(ruleSet.match(record));

        record = new Record(1, -0.1F, "");
        assertFalse(ruleSet.match(record));

        record = new Record(1, 0.0F, "description with bmw and banana");
        assertTrue(ruleSet.match(record));

        ruleSet = new RuleSet("$price >= 10000", Arrays.asList("bmw"), "yellow");
        record = new Record(1, 1000000, "the new BMW m3 gran coupe Competition");
        assertTrue(ruleSet.match(record));
    }

    @Test
    public void testWithPriceAndDescription() {
        RuleSet ruleSet = new RuleSet("$price > 20 AND $description ",
                Arrays.asList("apple", "banana"),
                "red");

        Record record = new Record(0, 10, "invalid desc");
        assertFalse(ruleSet.match(record));

        record = new Record(0, 10, "valid aPpLe desc with bAnaNa");
        assertFalse(ruleSet.match(record));

        record = new Record(0, 21, "valid apple desc with banana");
        assertTrue(ruleSet.match(record));

        record = new Record(0, 20.1F, "valid apple desc with banana");
        assertTrue(ruleSet.match(record));
    }

    @Test
    public void testWithDescriptionOnly() {
        RuleSet ruleSet = new RuleSet("$description",
                Arrays.asList("apple", "banana", "strawberry"),
                "red");

        Record record = new Record(0, 10, "invalid desc");
        assertFalse(ruleSet.match(record));

        record = new Record(0, 10, "valid apple desc with banana and some strawberry as well");
        assertTrue(ruleSet.match(record));

        record = new Record(0, 21, "invalid apple desc with banana");
        assertFalse(ruleSet.match(record));
    }
}
