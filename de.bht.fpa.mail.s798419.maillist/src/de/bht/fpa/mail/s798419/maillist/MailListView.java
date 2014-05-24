package de.bht.fpa.mail.s798419.maillist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
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
  private static final int HEIGHT_OF_TOOLBAR = 25;

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

  public static final char NULL_CHAR = 0x0;

  private static final int ICON_CONTAINING_CELL_WIDTH = 24;

  private static final int NUMBER_OF_MESSAGES = 50;
  private TableViewer tableViewer;
  private Text textInput;

  @Override
  public void createPartControl(Composite parent) {

    parent.setLayout(new GridLayout(1, false));

    Composite topLayer = new Composite(parent, SWT.NONE);
    Composite bottomLayer = new Composite(parent, SWT.NONE);

    topLayer.setLayout(new GridLayout(2, false));

    GridDataFactory.fillDefaults().grab(true, false).span(SWT.FILL, SWT.DEFAULT).hint(SWT.DEFAULT, HEIGHT_OF_TOOLBAR)
        .applyTo(topLayer);
    GridDataFactory.fillDefaults().grab(true, true).span(SWT.FILL, SWT.DEFAULT).hint(SWT.DEFAULT, SWT.DEFAULT)
        .applyTo(bottomLayer);

    TableViewerBuilder t = new TableViewerBuilder(bottomLayer);

    CLabel lblSearch = new CLabel(topLayer, SWT.LEFT);
    lblSearch.setText("Search");
    lblSearch.setMargins(0, 0, 0, 0);

    textInput = new Text(topLayer, SWT.FILL);
    textInput.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
    textInput.addKeyListener(new KeyListener() {

      @Override
      public void keyPressed(KeyEvent e) {

      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (e.character != MailListView.NULL_CHAR) {
          search(e.character);
        }
      }

    });

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

    ColumnBuilder sent = t.createColumn("Sent");
    sent.sortBy(MessageValues.SENT);
    sent.useAsDefaultSortColumn();
    sent.bindToValue(MessageValues.SENT);
    sent.format(new ICellFormatter() {
      @Override
      public void formatCell(ViewerCell cell, Object value) {
        Date nonFormattedDate = (Date) value;
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = formatter.format(nonFormattedDate);
        cell.setText(formattedDate);
      }
    });
    sent.build();

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

  private void search(char character) {

    final String searchString = this.textInput.getText().toLowerCase();

    this.tableViewer.resetFilters();

    if (searchString.length() > 0) {
      this.tableViewer.addFilter(new ViewerFilter() {

        @Override
        public boolean select(Viewer viewer, Object parentElement, Object element) {
          Message message = (Message) element;
          if (message.getSubject().toLowerCase().contains(searchString)) {
            return true;
          }
          if (message.getText().toLowerCase().contains(searchString)) {
            return true;
          }
          if (message.getSender().getEmail().toLowerCase().contains(searchString)) {
            return true;
          }
          if (message.getSender().getPersonal().toLowerCase().contains(searchString)) {
            return true;
          }
          for (Recipient msg : message.getRecipients()) {
            if (msg.getEmail().toLowerCase().contains(searchString)) {
              return true;
            }
            if (msg.getPersonal().toLowerCase().contains(searchString)) {
              return true;
            }
          }
          final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
          Date sent = message.getSent();
          Date received = message.getReceived();

          String formattedReceivedDate = formatter.format(received);
          String formattedSentDate = formatter.format(sent);

          if (formattedReceivedDate.contains(searchString) || formattedSentDate.contains(searchString)) {
            return true;
          }

          return false;
        }

      });
    }

    tableViewer.refresh();

  }

  @Override
  public void setFocus() {
    this.tableViewer.getTable().setFocus();
  }
}
