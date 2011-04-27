/*
 * Created on 04/11/2006
 */
package com.minotauro.state.generator;

import java.io.FileWriter;

import org.apache.commons.lang.SystemUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import stateMachine.StateMachine;
import stateMachine.StateMachinePackage;

import com.minotauro.cleda.util.FreeMarkerUtil;

/**
 * @author Demián Gutierrez
 */
public class CodeGenerator {

  private String basePath;
  private String basePack;
  private String baseName;
  private String template;

  // --------------------------------------------------------------------------------

  public CodeGenerator(String basePath, String basePack, String baseName, String template) {
    this.basePath = basePath;
    this.basePack = basePack;
    this.baseName = baseName;
    this.template = template;
  }

  // --------------------------------------------------------------------------------

  public void run() throws Exception {

    ResourceSet resourceSet = new ResourceSetImpl();

    // --------------------------------------------------------------------------------
    // Register the appropriate resource factory to handle all file extensions
    // --------------------------------------------------------------------------------

    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( //
        Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

    // --------------------------------------------------------------------------------
    // Register the package to ensure it is available during loading
    // --------------------------------------------------------------------------------

    resourceSet.getPackageRegistry().put( //
        StateMachinePackage.eNS_URI, StateMachinePackage.eINSTANCE);

    // --------------------------------------------------------------------------------
    // Load the resource
    // --------------------------------------------------------------------------------

    String loadFileName = basePath + //
        basePack.replaceAll("\\.", SystemUtils.FILE_SEPARATOR) + //
        SystemUtils.FILE_SEPARATOR + baseName + ".statemachine";

    URI fileURI = URI.createFileURI(loadFileName);
    Resource resource = resourceSet.getResource(fileURI, true);
    resource.load(null);

    EList<?> list = resource.getContents();

    if (list.isEmpty()) {
      throw new IllegalArgumentException( //
          "list.isEmpty() : " + loadFileName);
    }

    if (!(list.get(0) instanceof StateMachine)) {
      throw new IllegalArgumentException( //
          "!(list.get(0) instanceof StateMachine) : " + loadFileName);
    }

    StateMachine stateMachine = (StateMachine) list.get(0);

    // --------------------------------------------------------------------------------
    // Write the template
    // --------------------------------------------------------------------------------

    String saveFileName = basePath + //
        stateMachine.getPackage().replaceAll("\\.", SystemUtils.FILE_SEPARATOR) + //
        SystemUtils.FILE_SEPARATOR + stateMachine.getName() + ".java";

    FileWriter fileWriter = new FileWriter(saveFileName);

    FreeMarkerUtil.getInstance().process("/templates/" + template, stateMachine, fileWriter);
    fileWriter.close();
  }
}
