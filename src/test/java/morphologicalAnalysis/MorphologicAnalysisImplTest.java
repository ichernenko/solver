package morphologicalAnalysis;

import org.junit.Test;
import static org.junit.Assert.*;

public class MorphologicAnalysisImplTest {
    @Test
    public void getSentences() throws Exception {
        int n = 50;
        assertEquals(n, 50);
        assertNotEquals(n, 51);
    }

}