package se.skl.tp.vp.extract;

import static se.skl.tp.vp.extract.Constants.FILE_COLON_PREFIX;

import java.io.File;
import se.skl.tp.vp.testutil.VPStringUtil;
import se.skl.tp.vp.wsdl.WsdlConfig;

class WsdlConfigHolder {

  private File file;

  private WsdlConfig config = new WsdlConfig();

  private StringBuilder problem = new StringBuilder();

  WsdlConfigHolder(File file) {
    this.file = file;
  }

  void setTjanstekontrakt(String tjanstekontrakt) {
    config.setTjanstekontrakt(tjanstekontrakt);
  }

  void setWsdlfilepath(String basepath, String wsdlfilepath) {
    if (basepath == null) {
      config.setWsdlfilepath("#replaceMe#" + wsdlfilepath);
    } else {
      config.setWsdlfilepath(basepath +"/" +wsdlfilepath);
    }
  }

  void setWsdlurl(String wsdlurl) {
    config.setWsdlurl(VPStringUtil.concat("vp/" + wsdlurl));
  }

  void addProblemLine(Object... problems) {
    for (Object o : problems) {
      problem.append(o);
    }
    problem.append(System.lineSeparator());
  }

  boolean hasAnyProblem() {
    return (problem.length() > 0) || anyWsdlConfigPropertyIsNotAssgined();
  }

  String getProblem() {
    return hasAnyProblem() ? getInternalProblem() : "";
  }

  private String getInternalProblem() {
    return (problem.length() > 0)
        ? problem.toString()
        : anyWsdlConfigPropertyIsNotAssginedPoblems();
  }

  private String anyWsdlConfigPropertyIsNotAssginedPoblems() {
    if (VPStringUtil.valueIsEmpty(config.getTjanstekontrakt())) {
      addProblemLine(FILE_COLON_PREFIX, file.getName(), " Tjanstekontrakt is Empty");
    }
    if (VPStringUtil.valueIsEmpty(config.getWsdlfilepath())) {
      addProblemLine(FILE_COLON_PREFIX, file.getName(), " Wsdlfilepath is Empty");
    }

    if (VPStringUtil.valueIsEmpty(config.getWsdlurl())) {
      addProblemLine(FILE_COLON_PREFIX, file.getName(), " WsdlUrl is Empty");
    }
    String result = problem.toString();
    problem.setLength(0);
    return result;
  }

  private boolean anyWsdlConfigPropertyIsNotAssgined() {
    return (VPStringUtil.valueIsEmpty(config.getTjanstekontrakt()))
        || (VPStringUtil.valueIsEmpty(config.getWsdlfilepath()))
        || (VPStringUtil.valueIsEmpty(config.getWsdlurl()));
  }

  WsdlConfig getWsdlConfig() {
    if (hasAnyProblem()) {
      throw new AttemptAtRetainBadWsdlConfigException("Please only retain valid WsdlConfigs");
    }
    return config;
  }

  public class AttemptAtRetainBadWsdlConfigException extends RuntimeException {

    public AttemptAtRetainBadWsdlConfigException(String message) {
      super(message);
    }
  }


}
