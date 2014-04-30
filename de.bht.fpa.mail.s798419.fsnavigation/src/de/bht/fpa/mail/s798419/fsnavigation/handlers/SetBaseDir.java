package de.bht.fpa.mail.s798419.fsnavigation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import de.bht.fpa.mail.s798419.fsnavigation.FolderTree;
import de.bht.fpa.mail.s798419.fsnavigation.NavigationView;

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

    if (newBaseDir != null) {
      IWorkbenchPage page = window.getActivePage();
      NavigationView view = (NavigationView) page.findView(NavigationView.ID);
      view.changeModel(new FolderTree(newBaseDir));
    }

    return newBaseDir;

  }
}
