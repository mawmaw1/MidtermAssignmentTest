package net.sf.javaanpr.test;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class RecognitionAllIT {

    private Intelligence intelligence;



    @Parameterized.Parameters(name = "{index}: Correct plate property({1})")
    public static Collection<Object[]> setParameters() throws IOException {
        Collection<Object[]> params = new ArrayList<>();
        String snapshotDirPath = "src/test/resources/snapshots";
        String resultsPath = "src/test/resources/results.properties";
        InputStream resultsStream = new FileInputStream(new File(resultsPath));
        Properties properties = new Properties();
        properties.load(resultsStream);
        resultsStream.close();
        assertTrue(properties.size() > 0);
        File snapshotDir = new File(snapshotDirPath);
        File[] snapshots = snapshotDir.listFiles();
        assertNotNull(snapshots);
        assertTrue(snapshots.length > 0);

        for (File snap : snapshots) {
            String correctPlateProperty = properties.getProperty(snap.getName());
            params.add(new Object[]{snap, correctPlateProperty});
        }

        return params;
    }

    @Parameterized.Parameter(value = 1)
    public String correctPlateProperty;

    @Parameterized.Parameter
    public File snap;


    @Before
    public void createIntelligence() throws IOException, SAXException, ParserConfigurationException {
        intelligence = new Intelligence();
    }

    @Test
    public void intelligenceSingleTest() throws IOException {
        CarSnapshot carSnap = new CarSnapshot(new FileInputStream(snap));
        assertNotNull("carSnap is null", carSnap);
        assertNotNull("carSnap.image is null", carSnap.getImage());
        assertNotNull("correctPlateProperty is null", correctPlateProperty);

        String resPlate = intelligence.recognize(carSnap, false);
        assertNotNull("resPlate is null",resPlate);
        assertThat(correctPlateProperty,equalTo(resPlate));
        carSnap.close();
    }
}
