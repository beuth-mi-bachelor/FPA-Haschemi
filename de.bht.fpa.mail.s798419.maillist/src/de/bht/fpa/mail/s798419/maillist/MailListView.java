package de.bht.fpa.mail.s798419.maillist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s000000.common.mail.model.Sender;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s000000.common.table.MessageValues;
import de.ralfebert.rcputils.tables.ColumnBuilder;
import de.ralfebert.rcputils.tables.ICellFormatter;
import de.ralfebert.rcputils.tables.TableViewerBuilder;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.CLabel;

public class MailListView extends ViewPart {
  public MailListView() {
  }

  public static final String ID = "de.bht.fpa.mail.s798419.maillist.MailListView";

  private static final Image ICON_READ = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "img/icon_read.png")
      .createImage();
  private static final Image ICON_UNREAD = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
      "img/icon_unread.png").createImage();
  private static final Image ICON_NORMAL = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
      "img/icon_normal.png").createImage();
  private static final Image ICON_HIGH = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "img/icon_high.png")
      .createImage();
  private static final Image ICON_LOW = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "img/icon_low.png")
      .createImage();

  private static final int ICON_CONTAINING_CELL_WIDTH = 24;

  private static final int NUMBER_OF_MESSAGES = 50;
  private TableViewer tableViewer;
  private Text text_1;

  @Override
  public void createPartControl(Composite parent) {

    parent.setLayout(new GridLayout(1, true));

    CLabel lblSearch = new CLabel(parent, SWT.LEFT);
    lblSearch.setText("Search");

    text_1 = new Text(parent, SWT.FILL);

    Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new TableColumnLayout());

    TableViewerBuilder t = new TableViewerBuilder(composite, SWT.BORDER | SWT.FULL_SELECTION);
    composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
    t.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    ColumnBuilder importance = t.createColumn("Importance");
    importance.format(new ICellFormatter() {
      @Override
      public void formatCell(ViewerCell cell, Object value) {
        Importance prio = (Importance) value;
        Image icon;
        if (prio.value() == "low") {
          icon = ICON_LOW;
        } else if (prio.value() == "high") {
          icon = ICON_HIGH;
        } else {
          icon = ICON_NORMAL;
        }
        cell.setImage(icon);
        cell.setText("");
      }
    });
    importance.setPixelWidth(ICON_CONTAINING_CELL_WIDTH);
    importance.alignCenter();
    importance.bindToValue(MessageValues.IMPORTANCE).build();

    ColumnBuilder received = t.createColumn("Received");
    received.sortBy(MessageValues.RECEIVED);
    received.useAsDefaultSortColumn();
    received.bindToValue(MessageValues.RECEIVED);
    received.format(new ICellFormatter() {
      @Override
      public void formatCell(ViewerCell cell, Object value) {
        Date nonFormattedDate = (Date) value;
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = formatter.format(nonFormattedDate);
        cell.setText(formattedDate);
      }
    });
    received.build();

    ColumnBuilder read = t.createColumn("Read");
    read.format(new ICellFormatter() {
      @Override
      public void formatCell(ViewerCell cell, Object value) {
        Boolean alreadyRead = (Boolean) value;
        Image icon;
        if (alreadyRead) {
          icon = ICON_READ;
        } else {
          icon = ICON_UNREAD;
        }
        cell.setImage(icon);
        cell.setText("");
      }
    });
    read.setPixelWidth(ICON_CONTAINING_CELL_WIDTH);
    read.alignCenter();
    read.bindToValue(MessageValues.READ).build();

    ColumnBuilder sender = t.createColumn("Sender");
    sender.format(new ICellFormatter() {
      @Override
      public void formatCell(ViewerCell cell, Object value) {
        Sender sender = (Sender) value;
        cell.setText(sender.getPersonal() + " <" + sender.getEmail() + ">");
      }
    });
    sender.bindToValue(MessageValues.SENDER).build();

    ColumnBuilder recipients = t.createColumn("Recipients");
    recipients.format(new ICellFormatter() {
      @Override
      public void formatCell(ViewerCell cell, Object value) {
        @SuppressWarnings("unchecked")
        ArrayList<Recipient> recipients = (ArrayList<Recipient>) value;
        String recipientList = "";
        for (Recipient recipient : recipients) {
          recipientList += recipient.getPersonal() + " <" + recipient.getEmail() + ">";
        }

        cell.setText(recipientList);
      }
    });
    recipients.bindToValue(MessageValues.RECIPIENT).build();

    ColumnBuilder subject = t.createColumn("Subject");
    subject.bindToValue(MessageValues.SUBJECT).build();

    this.tableViewer = t.getTableViewer();
    t.setInput(createDummyMessages());
  }

  private Collection<Message> createDummyMessages() {
    return new RandomTestDataProvider(NUMBER_OF_MESSAGES).getMessages();
  }

  @Override
  public void setFocus() {
    this.tableViewer.getTable().setFocus();
  }
}
