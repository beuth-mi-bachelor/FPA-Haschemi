package de.bht.fpa.mail.s798419.imapnavigation.actions;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;


public class NewMail extends AbstractHandler {

  private Dialog dialog;

  public NewMail() {
  }

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

    this.dialog = new NewMailDialog(window.getShell());
    this.dialog.open();

    return this.dialog;
  }

}
