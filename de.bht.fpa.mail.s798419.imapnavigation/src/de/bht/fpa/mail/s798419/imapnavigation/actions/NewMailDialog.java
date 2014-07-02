package de.bht.fpa.mail.s798419.imapnavigation.actions;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public final class NewMailDialog extends Dialog {

  private static final String DIALOG_TITLE = "Filter Configuration";
  private static final int HEIGHT = 300;
  private static final int WIDTH = 600;
  private static final int NR_OF_COLUMNS = 3;
  private Composite container;
  private Text text;
  private Text text_1;
  private Text text_2;

  public NewMailDialog(Shell parentShell) {
    super(parentShell);
    setShellStyle(SWT.MAX | SWT.RESIZE | SWT.TITLE);
  }

  /**
   * Create contents of the dialog.
   * 
   * @param parent
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    container = (Composite) super.createDialogArea(parent);
    container.setLayout(new GridLayout(2, false));
    
    Label lblRecipient = new Label(container, SWT.NONE);
    lblRecipient.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblRecipient.setText("Recipient");
    
    text = new Text(container, SWT.BORDER);
    text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    
    Label lblSubject = new Label(container, SWT.NONE);
    lblSubject.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblSubject.setText("Subject");
    
    text_2 = new Text(container, SWT.BORDER);
    text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    
    Label lblText = new Label(container, SWT.NONE);
    lblText.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblText.setText("Text");
    
    text_1 = new Text(container, SWT.BORDER);
    GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
    gd_text_1.heightHint = 168;
    text_1.setLayoutData(gd_text_1);
    getShell().setText(DIALOG_TITLE);
    return container;
  }

  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    String ok = JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY);
    String cancel = JFaceResources.getString(IDialogLabelKeys.CANCEL_LABEL_KEY);

    createButton(parent, IDialogConstants.OK_ID, "Send", true);
    createButton(parent, IDialogConstants.CANCEL_ID, cancel, false);
  }

  /**
   * Return the initial size of the dialog.
   */
  @Override
  protected Point getInitialSize() {
    return new Point(WIDTH, HEIGHT);
  }

  @Override
  protected void okPressed() {
    super.okPressed();
  }

  @Override
  protected void cancelPressed() {
    super.cancelPressed();
  }

}
