package org.cyrano.common;

import org.cyrano.objects.Stack;

public interface AcceptRule {

  public boolean acceptCards(Stack tgt, Stack tmp);
}
