package de.bht.fpa.mail.s798419.statusbar;

import java.io.File;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;
import de.bht.fpa.mail.s000000.common.rcp.statusbar.StatusBarHelper;

public class StatusbarView implements IStartup {

  public static final String ID = "de.bht.fpa.mail.s798419.statusbar.StatusbarView";

  @Override
  public void earlyStartup() {
    final IWorkbench workbench = PlatformUI.getWorkbench();
    workbench.getDisplay().asyncExec(new Runnable() {
      @Override
      public void run() {
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        if (window != null) {
          window.getActivePage().getActivePart().getSite().getPage().addSelectionListener(listener);
        }
      }
    });
  }

  private final ISelectionListener listener = new ISelectionListener() {

    @Override
    public void selectionChanged(IWorkbenchPart part, ISelection sel) {
      if (!(sel instanceof IStructuredSelection)) {
        return;
      }
      IStructuredSelection ss = (IStructuredSelection) sel;
      Object item = ss.getFirstElement();
      if (item instanceof File) {
        File folder = (File) item;
        StatusBarHelper.getStatusLineManager().setMessage("folder '" + folder + "' was selected");
      } else if (item instanceof Folder) {
        Folder folder = (Folder) item;
        StatusBarHelper.getStatusLineManager().setMessage(folder.getMessages().size() 
            + " messsages in " + folder.getFullName());
      } else if (item instanceof Account) {
        Account account = (Account) item;
        StatusBarHelper.getStatusLineManager().setMessage(account.getFolders().size() 
            + " folders in " + account.getName());
      }
    }
  };

}
