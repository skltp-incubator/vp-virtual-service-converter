package se.skl.tp.vp.json;

import static se.skl.tp.vp.wsdl.PathHelper.PATH_PREFIX;

import se.skl.tp.vp.testutil.VPStringUtil;

public class UtilConfigConstants {

  private UtilConfigConstants(){}

  public static final String TEST_WSDL_CONFIG_FILE =
      VPStringUtil.concat(PATH_PREFIX, "testWsdlConfigFile.json");
}
