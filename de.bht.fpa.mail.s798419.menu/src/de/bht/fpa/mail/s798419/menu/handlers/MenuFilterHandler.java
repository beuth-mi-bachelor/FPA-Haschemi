package de.bht.fpa.mail.s798419.menu.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;
import de.bht.fpa.mail.s000000.common.filter.FilterDialog;
import de.bht.fpa.mail.s798419.menu.FilterDialogExecutionListener;

public class MenuFilterHandler extends AbstractHandler {

  private FilterDialog dialog;

  public MenuFilterHandler() {
  }

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

    ICommandService commandService = (ICommandService) window.getWorkbench().getService(ICommandService.class);
    commandService.addExecutionListener(new FilterDialogExecutionListener());

    this.dialog = new FilterDialog(window.getShell());
    this.dialog.open();

    return this.dialog;
  }

}
