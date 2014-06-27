package de.bht.fpa.mail.s798419.imapnavigation;

import java.util.Arrays;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.builder.AccountBuilder;
import de.bht.fpa.mail.s000000.common.mail.model.builder.FolderBuilder;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;

public class ImapNavigationView extends ViewPart {

  public static final String ID = "de.bht.fpa.mail.s798419.imapnavigation.ImapNavigationView";
  public static final int[] NUMBER_OF_MESSAGES = { 20, 30, 5 };

  private TreeViewer viewer;
  private IMemento memento;

  public static final String GUI_STATE = "lastState";
  public static final String START_PATH = "startPath";
  public static final String ALL_PATHS = "allPaths";

  private String history = "";

  @Override
  public void init(final IViewSite site, final IMemento memento) throws PartInitException {
    init(site);
    this.memento = memento;
  }

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  @Override
  public void createPartControl(Composite parent) {
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    viewer.setContentProvider(new ImapContentProvider());
    viewer.setLabelProvider(new ImapLabelProvider());
    viewer.setInput(createModel());
    this.getSite().setSelectionProvider(viewer);
    this.restoreState();
  }

  public String[] getHistory() {
    return this.history.split(";");
  }

  public void addToHistory(String item) {
    if (!Arrays.asList(this.getHistory()).contains(item)) {
      this.history += ";" + item;
    }
  }

  private void restoreState() {
    if (this.memento == null) {
      return;
    }
    IMemento selectionsMomento = this.memento.getChild(ImapNavigationView.GUI_STATE);
    if (selectionsMomento != null) {
      this.history = selectionsMomento.getString(ImapNavigationView.ALL_PATHS);
      String startFolder = selectionsMomento.getString(ImapNavigationView.START_PATH);
      if (startFolder != null) {
        this.viewer.setInput(createModel());
      }
    }
  }

  public void changeModel(Object m) {
    viewer.setInput(m);
  }
  
  public Object getModel() {
    return viewer.getInput();
  }

  private Account getDummyAccount() {
    return AccountBuilder
        .newAccountBuilder()
        .host("de.somewhere.com")
        .username("alice")
        .password("secret")
        .name("Alice-IMAP")
        .folder(
            FolderBuilder
                .newFolderBuilder()
                .fullName("INBOX")
                .builtMessages(new RandomTestDataProvider(ImapNavigationView.NUMBER_OF_MESSAGES[0]).getMessages())
                .folder(
                    FolderBuilder
                        .newFolderBuilder()
                        .fullName("Customers")
                        .builtMessages(
                            new RandomTestDataProvider(ImapNavigationView.NUMBER_OF_MESSAGES[1]).getMessages())))
        .folder(
            FolderBuilder.newFolderBuilder().fullName("Sent")
                .builtMessages(new RandomTestDataProvider(ImapNavigationView.NUMBER_OF_MESSAGES[2]).getMessages()))
        .build();
  }

  private Object createModel() {
    AccountList accounts = new AccountList();
    accounts.addAll(getDummyAccount(), getDummyAccount(), getDummyAccount());
    return accounts;
  }

  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }

  @Override
  public void saveState(final IMemento memento) {
    String currentFolder = this.viewer.getInput().toString();
    IMemento remember = memento.createChild(ImapNavigationView.GUI_STATE);

    remember.putString(ImapNavigationView.ALL_PATHS, this.history);

    remember.putString(ImapNavigationView.START_PATH, currentFolder);
  }
}
