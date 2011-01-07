package org.cyrano.formats.txt1;

import org.cyrano.formats.FileFormat;
import org.cyrano.formats.FileFormatFactory;

public class Txt1FileFormatFactory implements FileFormatFactory {

  @Override
  public FileFormat create() {
    return new Txt1FileFormat();
  }

  @Override
  public String getExtensionName() {
    return "tx1";
  }
}
