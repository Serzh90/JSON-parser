import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class JsonParserApplicationTest {
    private String resultFileName = "resources/result.out";
    private String validationConfigFileName = "resources/validation_config";
    private String testFileName = "resources/test.in";

    @Before
    public void clean() {
        File resultFile = new File(resultFileName);
        if(resultFile.exists()){
            resultFile.delete();
        }
    }

    @Test
    public void main() {
        JsonParserApplication app = new JsonParserApplication();
        app.main(new String[]{validationConfigFileName, testFileName, resultFileName});
        assertTrue(new File(resultFileName).exists());
    }
}