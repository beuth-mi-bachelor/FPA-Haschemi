package de.bht.fpa.mail.s798419.fsnavigation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bht.fpa.mail.s798419.fsnavigation.dialogs.HistoryListDialog;

public class HistoryViewer extends AbstractHandler {

  public HistoryViewer() {
    
  }

  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    HistoryListDialog listDialog = new HistoryListDialog(window.getShell());
    listDialog.open();
    return null;
  }
}
