package se.skl.tp.vp;

import java.io.File;
import java.util.List;
import se.skl.tp.vp.extract.ExtractWsdlDataFromOldVPJars;

public class VirtualServiceConverter {

  public static void main(String[] args) {
    try {
      if (args.length < 1) {
        showUsage();
        return;
      }
      String sourceFolder = args[0];
      String destinationFolder = sourceFolder + "/generated";
      String serverFolder =  args.length > 1 ? args[1] :null;

      ExtractWsdlDataFromOldVPJars dataExtractor = new ExtractWsdlDataFromOldVPJars();
      dataExtractor.mountExtractedWsdlAndCreateSettingFiles(sourceFolder, destinationFolder, serverFolder);

      System.out.printf("Done!\n"
              + "Check destination folder %s for the result.\n"
              + "The file problematicJars.txt will show all failed jar extractions.\n",
          destinationFolder);

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static void showUsage() {
    System.out.println(
        "This tool will extract old VP virtual services jars and "
            + "create a config file for VP-camel\n\n"
            + "Usage:\n"
            + "VirtualServiceConverter source [serverpath]\n\n"
            + "source:      source folder with VP virtual service jars\n"
            + "serverpath: (optional) The path on the server where the files should be located");
  }
}