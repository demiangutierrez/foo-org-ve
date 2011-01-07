package org.cyrano.formats;

import java.io.File;
import java.util.List;

import org.cyrano.plugins.Paintable;

public interface FileFormat {

  public void load(File file, List<Paintable> paintableList) //
      throws Exception;

  public void save(File file, List<Paintable> paintableList) //
      throws Exception;
}
