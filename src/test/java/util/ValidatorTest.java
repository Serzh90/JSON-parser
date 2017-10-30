package util;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ValidatorTest {

    /**
     * Requires ...\src\test to be specified as current working directory for the unit tests
     */
    @Test
    public void init() {
        Validator validator = null;
        try {
            validator = new Validator(new File("resources/validation_config")).init();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(validator);
        assertNotNull(validator.getRuleSet());
        assertEquals(validator.getRuleSet().size(), 4);
    }

    @Test
    public void validate() {
        Validator validator = null;
        try {
            validator = new Validator(new File("resources/validation_config")).init();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        Record record = new Record(15, 0.0F, "");
        RecordResult result = validator.validate(record);
        assertNotNull(result);
        assertEquals(result.getFlag(), "red");
        assertEquals(result.getRecordId(), 15);

        record = new Record(12, 111, "nothing special here");
        result = validator.validate(record);
        assertEquals(result.getFlag(), "green"); //default
        assertEquals(result.getRecordId(), 12);
    }

}
