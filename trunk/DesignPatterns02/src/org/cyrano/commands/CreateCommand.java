package org.cyrano.commands;

import java.util.List;

import org.cyrano.common.Command;
import org.cyrano.common.Paintable;
import org.cyrano.common.PaintableFactory;

// Command
public class CreateCommand implements Command {

  private PaintableFactory paintableFactory;

  private Paintable paintable;

  private List<Paintable> paintableList;

  private int x;
  private int y;

  // --------------------------------------------------------------------------------

  public CreateCommand(PaintableFactory paintableFactory, //
      int x, int y, List<Paintable> paintableList) {

    this.paintableFactory = paintableFactory;

    this.x = x;
    this.y = y;

    this.paintableList = paintableList;
  }

  // --------------------------------------------------------------------------------

  @Override
  public void redoCommand() {
    if (paintable == null) {
      paintable = paintableFactory.create( //
          x - 50, y - 50, x + 50, y + 50);
    }

    paintableList.add(paintable);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void undoCommand() {
    paintableList.remove(paintable);
  }
}
