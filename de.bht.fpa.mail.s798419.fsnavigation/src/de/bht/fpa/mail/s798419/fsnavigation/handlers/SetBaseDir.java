package de.bht.fpa.mail.s798419.fsnavigation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class SetBaseDir extends AbstractHandler {

  public SetBaseDir() {
  }

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    DirectoryDialog setBaseDirDialog = new DirectoryDialog(window.getShell());
    setBaseDirDialog.setMessage("choose directory");
    setBaseDirDialog.setText("please choose your new favorite base directory for viewing");
    String newBaseDir = setBaseDirDialog.open();
    System.out.println(newBaseDir);
    return null;
  }
}
