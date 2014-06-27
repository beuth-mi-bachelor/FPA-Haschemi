package de.bht.fpa.mail.s798419.imapnavigation;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;

public class ImapContentProvider implements ITreeContentProvider {

  @Override
  public Object[] getChildren(Object parentElement) {
    Object[] folderList = new Object[] {};
    if (parentElement instanceof AccountList) {
      AccountList accountList = (AccountList) parentElement;
      folderList = accountList.getAccounts().toArray();
    } else if (parentElement instanceof Account) {
      Account account = (Account) parentElement;
      folderList = account.getFolders().toArray();
    } else if (parentElement instanceof Folder) {
      Folder folder = (Folder) parentElement;
      folderList = folder.getFolders().toArray();
    }
    return folderList;
  }

  @Override
  public boolean hasChildren(Object element) {
    if (element instanceof Account) {
      Account account = (Account) element;
      return (account.getFolders().size() > 0);
    } else if (element instanceof Folder) {
      Folder folder = (Folder) element;
      return (folder.getFolders().size() > 0);
    }
    return false;
  }

  @Override
  public Object[] getElements(Object parent) {
    return getChildren(parent);
  }

  @Override
  public void inputChanged(Viewer v, Object oldInput, Object newInput) {
  }

  @Override
  public void dispose() {
  }

  @Override
  public Object getParent(Object element) {
    return null;
  }
}
