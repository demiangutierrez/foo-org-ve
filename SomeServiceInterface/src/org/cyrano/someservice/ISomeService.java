package org.cyrano.someservice;

import java.util.ServiceLoader;

public interface ISomeService {

  public static final ServiceLoader<ISomeService> someServiceLoader = //
  ServiceLoader.load(ISomeService.class);

  // --------------------------------------------------------------------------------

  public String sayHelloTo(String someone);

  // --------------------------------------------------------------------------------
  // other methods...
  // --------------------------------------------------------------------------------
}
