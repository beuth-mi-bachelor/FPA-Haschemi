package de.bht.fpa.mail.s798419.filter.parser;

import java.util.ArrayList;
import java.util.Collection;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
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

public class FilterParser {
  private static final String IMPORTANCE = "Importance";
  private static final String READ = "Read";
  private static final String RECIPIENT = "Recipient";
  private static final String SENDER = "Sender";
  private static final String SUBJECT = "Subject";
  private static final String TEXT = "Text";
  private static final String UNION = "Union";
  private static final String INTERSECTION = "Intersection";

  private static final Object CONTAINS = "contains";
  private static final Object CONTAINS_NOT = "containsNot";
  private static final Object STARTS_WITH = "startsWith";
  private static final Object ENDS_WITH = "endsWith";
  private static final Object IS = "is";

  private static ArrayList<String> mainFilterArray;
  private static ArrayList<String> filterArray;

  public FilterParser() {

    mainFilterArray = new ArrayList<String>();
    mainFilterArray.add(UNION);
    mainFilterArray.add(INTERSECTION);

    filterArray = new ArrayList<String>();
    filterArray.add(IMPORTANCE);
    filterArray.add(READ);
    filterArray.add(RECIPIENT);
    filterArray.add(SENDER);
    filterArray.add(SUBJECT);
    filterArray.add(TEXT);
  }

  public Filter parseCommand(String stringToParse) {

    stringToParse = stringToParse.trim().replace(", ", ",");
    int lastIndexOfFirstFilter = stringToParse.indexOf("(");

    String firstFilterToTranslate = stringToParse.substring(0, lastIndexOfFirstFilter);

    if (filterArray.contains(firstFilterToTranslate)) {
      String filterName = stringToParse.substring(lastIndexOfFirstFilter + 1, stringToParse.length() - 1);
      String[] arguments = filterName.split(",");
      if (arguments.length > 2) {
        throw new IllegalArgumentException("The argument weight is invalid. Use 2 arguments only.");
      }
      Filter msgFilter = translateFilter(firstFilterToTranslate, arguments);
      return msgFilter;
    } else if (mainFilterArray.contains(firstFilterToTranslate)) {
      String head = stringToParse.substring(lastIndexOfFirstFilter + 1, stringToParse.length() - 1);

      int firstClosingChain = head.indexOf("),");

      String[] arguments = { head.substring(0, firstClosingChain + 1),
          head.substring(firstClosingChain + 2, head.length()) };

      if (arguments.length > 2) {
        throw new IllegalArgumentException("too many arguments");
      }
      String firstArg = arguments[0];
      String secondArg = arguments[1];

      Filter firstFilter = parseCommand(firstArg);
      Filter secondFilter = parseCommand(secondArg);
      Filter msgFilter = translateRecursiveCommand(firstFilterToTranslate, firstFilter, secondFilter);
      return msgFilter;
    } else {
      throw new IllegalArgumentException("undefined filter: " + firstFilterToTranslate);
    }
  }

  private Filter translateRecursiveCommand(String firstCom, Filter firstFilter, Filter secondFilter) {
    Collection<Filter> filterList = new ArrayList<Filter>();
    filterList.add(firstFilter);
    filterList.add(secondFilter);
    if (firstCom.equals(UNION)) {
      return new UnionFilter(filterList);
    } else if (firstCom.equals(INTERSECTION)) {
      return new IntersectionFilter(filterList);
    } else {
      throw new IllegalArgumentException("undefined filter");
    }
  }

  private Filter translateFilter(String firstCom, String[] arguments) {
    Filter filter = null;
    String filterText = arguments[0].replace("\"", "");

    FilterOperator filterOperator = FilterOperator.IS;
    if (arguments.length > 1) {
      if (arguments[1].equals(CONTAINS)) {
        filterOperator = FilterOperator.CONTAINS;
      }
      if (arguments[1].equals(CONTAINS_NOT)) {
        filterOperator = FilterOperator.CONTAINS_NOT;
      }
      if (arguments[1].equals(STARTS_WITH)) {
        filterOperator = FilterOperator.STARTS_WITH;
      }
      if (arguments[1].equals(ENDS_WITH)) {
        filterOperator = FilterOperator.ENDS_WITH;
      }
      if (arguments[1].equals(IS)) {
        filterOperator = FilterOperator.IS;
      }
    }

    if (firstCom.equals(SENDER)) {
      filter = new SenderFilter(filterText, filterOperator);
      return filter;
    } else if (firstCom.equals(RECIPIENT)) {
      filter = new RecipientFilter(filterText, filterOperator);
      return filter;
    } else if (firstCom.equals(SUBJECT)) {
      filter = new SubjectFilter(filterText, filterOperator);
      return filter;
    } else if (firstCom.equals(TEXT)) {
      filter = new TextFilter(filterText, filterOperator);
      return filter;
    } else if (firstCom.equals(IMPORTANCE)) {
      if (filterText.equals("low")) {
        Importance imp = Importance.LOW;
        filter = new ImportanceFilter(imp);
      } else if (filterText.equals("normal")) {
        Importance imp = Importance.NORMAL;
        filter = new ImportanceFilter(imp);
      } else if (filterText.equals("high")) {
        Importance imp = Importance.HIGH;
        filter = new ImportanceFilter(imp);
      } else {
        throw new IllegalArgumentException("importance should be low, normal or high");
      }
      return filter;
    } else if (firstCom.equals(READ)) {
      if (filterText.toLowerCase().equals("true")) {
        boolean read = true;
        filter = new ReadFilter(read);
      } else if (filterText.toLowerCase().equals("false")) {
        boolean read = false;
        filter = new ReadFilter(read);
      } else {
        throw new IllegalArgumentException("read parameter should be true or false");
      }
      return filter;
    } else {
      throw new IllegalArgumentException("undefined filter operator.");
    }

  }
}
