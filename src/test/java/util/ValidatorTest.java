package util;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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
        assertEquals(validator.getRuleSet().size(),4);
    }

    @Test
    public void validate(){
        Validator validator = null;
        try {
            validator = new Validator(new File("resources/validation_config")).init();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

    }

}
