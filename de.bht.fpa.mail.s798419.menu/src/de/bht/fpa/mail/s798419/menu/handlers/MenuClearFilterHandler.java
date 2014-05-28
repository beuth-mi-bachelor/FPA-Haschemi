package de.bht.fpa.mail.s798419.menu.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;
import de.bht.fpa.mail.s798419.maillist.MailListView;

public class MenuClearFilterHandler extends AbstractHandler {

  public MenuClearFilterHandler() {
  }

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    MailListView view = (MailListView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
        .findView(MailListView.ID);
    view.restoreView();
    return null;
  }
}
