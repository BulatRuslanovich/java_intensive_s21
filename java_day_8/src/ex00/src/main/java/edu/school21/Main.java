package edu.school21;

import edu.school21.classes.impl.PrinterWithDateTimeImpl;
import edu.school21.classes.impl.PrinterWithPrefixImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        PrinterWithPrefixImpl printerWithPrefix = context.getBean("printerWithPrefixOutToUpper", PrinterWithPrefixImpl.class);
        printerWithPrefix.print("Hello!");

        printerWithPrefix = context.getBean("printerWithPrefixErrToLower", PrinterWithPrefixImpl.class);
        printerWithPrefix.setPrefix("LOL:");
        printerWithPrefix.print("Ryan");

        PrinterWithDateTimeImpl printerWithDateTime = context.getBean("printerWithDateTimeErrToUpper", PrinterWithDateTimeImpl.class);
        printerWithDateTime.print("I finished this code");

        context.close();
    }
}