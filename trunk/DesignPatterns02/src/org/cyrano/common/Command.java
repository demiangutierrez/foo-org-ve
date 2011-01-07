package org.cyrano.common;

// Command
public interface Command {

  public void redoCommand();

  public void undoCommand();
}
