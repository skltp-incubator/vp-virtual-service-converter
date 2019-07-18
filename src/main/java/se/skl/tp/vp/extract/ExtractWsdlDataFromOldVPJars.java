package se.skl.tp.vp.extract;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import se.skl.tp.vp.extract.WsdlUrlExtractor.WsdlurlExtraction;
import se.skl.tp.vp.wsdl.PathHelper;

public class ExtractWsdlDataFromOldVPJars {

  private JarFilesUnZipper unzipper = new JarFilesUnZipper(new KeepWsdlAndXsdFilter());

  private WsdlUrlExtractor wsdlurlExtractor = new WsdlUrlExtractor();

  private UnpackedWdslFinder findUnpackedWsdl = new UnpackedWdslFinder();

  private JSonWsdlConfigFileFileGenerator fileGenerator = new JSonWsdlConfigFileFileGenerator();

  private FileGenerationData fileGenData = new FileGenerationData();

  private List<WsdlurlExtraction> extractResult;

  public List<File> mountExtractedWsdlAndCreateSettingFiles(String sourceDir, String mountIn, String serverFolder)
      throws IOException {

    extractResult = new ArrayList();
    List<File> jarFiles = PathHelper.findFilesInDirectory(sourceDir, ".*\\.jar");
    jarFiles.forEach(
        file -> {
          doExtract(mountIn, file, serverFolder);
        }
    );
    return fileGenerator.make(fileGenData, mountIn);
  }

  private void doExtract(String mountIn, File file, String basePath) {
    try (JarFile jarFile = new JarFile(file)) {

      if (wsdlurlExtractor.excecutedOk(jarFile)) {

        fileGenData.setWsdlurl(file, wsdlurlExtractor.wsdlUrl());
        unzipper.unzipXsdAndWsdlFilesTo(jarFile, wsdlurlExtractor.subDir(mountIn));
        if (findUnpackedWsdl.extractTjansteKontraktAndPath(
            wsdlurlExtractor.subDir(mountIn), mountIn)) {
          fileGenData.setTjanstekontrakt(file, findUnpackedWsdl.serviceContract());
          fileGenData.setWsdlfilepath(file, basePath, findUnpackedWsdl.wsdlPath());
        } else {
          fileGenData.AddProblemWithExtractTjansteKontraktAndPath(
              file, findUnpackedWsdl.lastExcecutionResult());
        }
      } else {
        fileGenData.addProblemWithWsdlurl(wsdlurlExtractor.excecutionResult(), file);
      }
    } catch (Exception e) {
      fileGenData.addProblemUnexpectedException(file, e);
    }
  }
}
