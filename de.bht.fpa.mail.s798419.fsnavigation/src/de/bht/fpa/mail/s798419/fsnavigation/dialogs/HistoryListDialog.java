package de.bht.fpa.mail.s798419.fsnavigation.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.ui.IWorkbenchPage;

import de.bht.fpa.mail.s798419.fsnavigation.FolderTree;
import de.bht.fpa.mail.s798419.fsnavigation.NavigationView;

public class HistoryListDialog extends Dialog {

  private final IWorkbenchPage page;
  private List historyList;
  private final NavigationView view;

  public HistoryListDialog(Shell parentShell, IWorkbenchPage page) {
    super(parentShell);
    this.page = page;
    this.view = (NavigationView) this.page.findView(NavigationView.ID);
    this.setShellStyle(SWT.SHELL_TRIM | SWT.RESIZE);
  }

  @Override
  protected Control createDialogArea(Composite parent) {

    Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new GridLayout(1, true));
    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    this.historyList = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    this.historyList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    String[] history = this.view.getHistory();

    if (history.length == 1 && history[0].equals("")) {
      this.historyList.setItems(new String[] { "no base directories in history" });
      this.historyList.setEnabled(false);
    } else {
      this.historyList.setItems(history);
    }

    return composite;
  }

  @Override
  protected void okPressed() {
    String[] selectedItem = this.historyList.getSelection();
    if (selectedItem.length > 0) {
      this.view.changeModel(new FolderTree(selectedItem[0]));
    }
    close();
  }
}
