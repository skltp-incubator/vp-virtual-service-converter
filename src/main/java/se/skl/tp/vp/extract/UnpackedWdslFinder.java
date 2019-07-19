package se.skl.tp.vp.extract;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.XPath;
import se.skl.tp.vp.wsdl.PathHelper;
import se.skl.tp.vp.xmlutil.XmlHelper;

class UnpackedWdslFinder {

  public enum WsdlFoundAndDataExtracted {
    OK, WRONG_NUMBER_OF_WSDL, WRONG_NUMBER_OF_CONTRACTS
  }

  private WsdlFoundAndDataExtracted lasExcecutionResult;

  WsdlFoundAndDataExtracted lastExcecutionResult() {
    return lasExcecutionResult;
  }

  private String serviceContract;

  String serviceContract() {
    return serviceContract;
  }

  String wsdlPath() {
    return wsdlPath;
  }

  private String wsdlPath;

  private XPath getConTract =
      XmlHelper.createXPath(
          "wsdl:definitions/wsdl:types/xs:schema/xs:import/@namespace[contains(.,'Responder')]",
          "wsdl=http://schemas.xmlsoap.org/wsdl/",
          "xs=http://www.w3.org/2001/XMLSchema"
      );

  boolean extractTjansteKontraktAndPath(Path destination, String mountIn)
      throws IOException, DocumentException, URISyntaxException {
    lasExcecutionResult = WsdlFoundAndDataExtracted.OK;
    List<File> wsdlFiles = PathHelper.findFilesInDirectory(destination.toFile().getPath(), ".*\\.wsdl");
    if (wsdlFiles.size() == 1) {
      wsdlPath = PathHelper.subtractDirectoryFromPath(mountIn, wsdlFiles.get(0));
      List<Node> contracts = getConTract.selectNodes(XmlHelper.openDocument(wsdlFiles.get(0).getPath()));
      List<String> contractsNoDuplicates = new ArrayList<>(
          contracts.stream().map(Node::getStringValue).collect(Collectors.toSet()));
      if (contractsNoDuplicates.size() == 1) {
        serviceContract = contractsNoDuplicates.get(0);
      } else {
        lasExcecutionResult = WsdlFoundAndDataExtracted.WRONG_NUMBER_OF_CONTRACTS;
      }
    } else {
      lasExcecutionResult = WsdlFoundAndDataExtracted.WRONG_NUMBER_OF_WSDL;
    }
    return lasExcecutionResult == WsdlFoundAndDataExtracted.OK;
  }
}
