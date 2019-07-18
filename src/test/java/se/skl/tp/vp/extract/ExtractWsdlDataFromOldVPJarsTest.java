package se.skl.tp.vp.extract;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;
import se.skl.tp.vp.testutil.VPStringUtil;
import se.skl.tp.vp.wsdl.PathHelper;

public class ExtractWsdlDataFromOldVPJarsTest {



  @Test
  public void extract() throws FileNotFoundException, URISyntaxException {
    String sourceFolder = PathHelper.getPath("classpath:testfiles/jarTestFiles").toString();
    String destinationFolder = Paths.get(sourceFolder,"VP").toString();

    String exceptionMessage = null;
    ExtractWsdlDataFromOldVPJars dataExtractor = new ExtractWsdlDataFromOldVPJars();
    assertTrue(dataExtractor != null);

    try {

      List<File> result =
          dataExtractor.mountExtractedWsdlAndCreateSettingFiles(sourceFolder, destinationFolder, null);

      File destination = new File(destinationFolder);

      PathHelper.deleteDirectory(Paths.get(destinationFolder));

      assertTrue((result.size()>1));

      assertFalse(destination.exists());
    } catch (Exception e) {
      exceptionMessage = e.getMessage();
    }
    assertTrue(exceptionMessage, VPStringUtil.valueIsEmpty(exceptionMessage));
  }
}
