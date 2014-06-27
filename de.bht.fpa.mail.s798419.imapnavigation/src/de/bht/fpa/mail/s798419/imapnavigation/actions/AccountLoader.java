package de.bht.fpa.mail.s798419.imapnavigation.actions;

import java.io.File;

import javax.xml.bind.JAXB;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s798419.imapnavigation.AccountList;
import de.bht.fpa.mail.s798419.imapnavigation.ImapNavigationView;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class AccountLoader implements IWorkbenchWindowActionDelegate {
  private IWorkbenchWindow window;

  /**
   * The constructor.
   */
  public AccountLoader() {
  }

  /**
   * The action has been activated. The argument of the method represents the
   * 'real' action sitting in the workbench UI.
   * 
   * @see IWorkbenchWindowActionDelegate#run
   */
  @Override
  public void run(IAction action) {
    FileDialog fileChooser = new FileDialog(window.getShell());
    String file = fileChooser.open();
    if (file != null) {
      File newAccount = new File(file);
      IWorkbenchPage page = window.getActivePage();
      ImapNavigationView view = (ImapNavigationView) page.findView(ImapNavigationView.ID);
      try {
        Account account = JAXB.unmarshal(newAccount, Account.class);
        if (account.getId() != null) {
          if (view.getModel() instanceof AccountList) {
            AccountList existingAccounts = (AccountList) view.getModel();
            existingAccounts.add(account);
            view.changeModel(existingAccounts);
          } else {
            view.changeModel(new AccountList(account));
          }
        }
      } catch (RuntimeException msg) {
        msg.printStackTrace();
      }
    }
  }

  /**
   * Selection in the workbench has been changed. We can change the state of the
   * 'real' action here if we want, but this can only happen after the delegate
   * has been created.
   * 
   * @see IWorkbenchWindowActionDelegate#selectionChanged
   */
  @Override
  public void selectionChanged(IAction action, ISelection selection) {
  }

  /**
   * We can use this method to dispose of any system resources we previously
   * allocated.
   * 
   * @see IWorkbenchWindowActionDelegate#dispose
   */
  @Override
  public void dispose() {
  }

  /**
   * We will cache window object in order to be able to provide parent shell for
   * the message dialog.
   * 
   * @see IWorkbenchWindowActionDelegate#init
   */
  @Override
  public void init(IWorkbenchWindow window) {
    this.window = window;
  }
}