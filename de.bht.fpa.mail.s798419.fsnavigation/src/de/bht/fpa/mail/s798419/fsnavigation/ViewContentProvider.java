package de.bht.fpa.mail.s798419.fsnavigation;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ViewContentProvider implements ITreeContentProvider {

  @Override
  public Object[] getChildren(Object parentElement) {
    FolderTree newItem = new FolderTree(parentElement);
    Object[] listOfAllChildren = newItem.listAllSubElements();
    return listOfAllChildren;
  }

  @Override
  public boolean hasChildren(Object element) {
    FolderTree elements = new FolderTree(element);
    return elements.hasSubElements();
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
