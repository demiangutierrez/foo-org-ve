package org.cyrano.formats.txt2;

import org.cyrano.formats.FileFormat;
import org.cyrano.formats.FileFormatFactory;

public class Txt2FileFormatFactory implements FileFormatFactory {

  @Override
  public FileFormat create() {
    return new Txt2FileFormat();
  }

  @Override
  public String getExtensionName() {
    return "tx2";
  }
}
