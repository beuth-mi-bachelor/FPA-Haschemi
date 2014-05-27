package de.bht.fpa.mail.s798419.menu.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

public class MenuClearFilterHandler extends AbstractHandler {

  public MenuClearFilterHandler() {
  }

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    MessageDialog.openInformation(window.getShell(), "Menu", "a");
    return null;
  }
}
