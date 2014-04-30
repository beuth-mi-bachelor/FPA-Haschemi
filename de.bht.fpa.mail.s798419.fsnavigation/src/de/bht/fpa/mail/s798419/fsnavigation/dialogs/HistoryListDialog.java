package de.bht.fpa.mail.s798419.fsnavigation.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class HistoryListDialog extends Dialog {

  public HistoryListDialog(Shell parentShell) {
    super(parentShell);
    this.setShellStyle(SWT.SHELL_TRIM | SWT.RESIZE);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    
    Composite composite = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout();
    composite.setLayout(layout);
    
    List list = new List(composite, SWT.BORDER);
    list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
    new Label(composite, SWT.NONE);
    return composite;
  }

  @Override
  protected void okPressed() {
    super.okPressed();
  }
  
  

}
