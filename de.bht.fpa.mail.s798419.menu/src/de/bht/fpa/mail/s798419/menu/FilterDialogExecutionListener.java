package de.bht.fpa.mail.s798419.menu;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.ui.PlatformUI;

import de.bht.fpa.mail.s000000.common.filter.FilterCombination;
import de.bht.fpa.mail.s000000.common.filter.FilterDialog;
import de.bht.fpa.mail.s000000.common.filter.FilterGroupType;
import de.bht.fpa.mail.s000000.common.filter.FilterType;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s798419.filter.Filter;
import de.bht.fpa.mail.s798419.filter.ImportanceFilter;
import de.bht.fpa.mail.s798419.filter.IntersectionFilter;
import de.bht.fpa.mail.s798419.filter.ReadFilter;
import de.bht.fpa.mail.s798419.filter.RecipientFilter;
import de.bht.fpa.mail.s798419.filter.SenderFilter;
import de.bht.fpa.mail.s798419.filter.SubjectFilter;
import de.bht.fpa.mail.s798419.filter.TextFilter;
import de.bht.fpa.mail.s798419.filter.UnionFilter;
import de.bht.fpa.mail.s798419.maillist.MailListView;

public class FilterDialogExecutionListener implements IExecutionListener {

  @Override
  public void notHandled(String commandId, NotHandledException exception) {

  }

  @Override
  public void postExecuteFailure(String commandId, ExecutionException exception) {

  }

  @Override
  public void postExecuteSuccess(String commandId, Object returnValue) {
    if (commandId.equals("de.bht.fpa.mail.s798419.menu.commands.menuFilter")) {
      if (returnValue != null) {
        if (returnValue instanceof FilterDialog) {
          FilterDialog filterDialog = (FilterDialog) returnValue;
          if (filterDialog.getReturnCode() == FilterDialog.OK) {
            Collection<FilterCombination> filterCombo = filterDialog.getFilterCombinations();
            FilterGroupType filterTypes = filterDialog.getFilterGroupType();
            Collection<Filter> filterList = new ArrayList<Filter>();
            for (FilterCombination filterCombination : filterCombo) {
              Filter currentFilter = null;
              if (filterCombination.getFilterType().equals(FilterType.SENDER)) {
                currentFilter = new SenderFilter((String) filterCombination.getFilterValue(),
                    filterCombination.getFilterOperator());
              } else if (filterCombination.getFilterType().equals(FilterType.RECIPIENTS)) {
                currentFilter = new RecipientFilter((String) filterCombination.getFilterValue(),
                    filterCombination.getFilterOperator());
              } else if (filterCombination.getFilterType().equals(FilterType.TEXT)) {
                currentFilter = new TextFilter((String) filterCombination.getFilterValue(),
                    filterCombination.getFilterOperator());
              } else if (filterCombination.getFilterType().equals(FilterType.SUBJECT)) {
                currentFilter = new SubjectFilter((String) filterCombination.getFilterValue(),
                    filterCombination.getFilterOperator());
              } else if (filterCombination.getFilterType().equals(FilterType.IMPORTANCE)) {
                currentFilter = new ImportanceFilter((Importance) filterCombination.getFilterValue());
              } else if (filterCombination.getFilterType().equals(FilterType.READ)) {
                currentFilter = new ReadFilter((Boolean) filterCombination.getFilterValue());
              }
              filterList.add(currentFilter);
            }

            if (filterList.size() > 0) {
              Filter filterGroup = null;
              if (filterTypes.equals(FilterGroupType.UNION)) {
                filterGroup = new UnionFilter(filterList);
              } else if (filterTypes.equals(FilterGroupType.INTERSECTION)) {
                filterGroup = new IntersectionFilter(filterList);
              }
              if (filterGroup != null) {
                MailListView view = (MailListView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                    .findView(MailListView.ID);
                view.filter(filterGroup);
              }
            }
          }
        }
      }
    }
  }

  @Override
  public void preExecute(String commandId, ExecutionEvent event) {

  }

}
