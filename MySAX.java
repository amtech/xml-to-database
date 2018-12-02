import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

/* Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 */


import java.util.*;

import org.xml.sax.XMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

public class MySAX extends DefaultHandler
{
	FileWriter fileWriter = null;
    public static void main (String args[]) throws Exception {
		XMLReader xr = XMLReaderFactory.createXMLReader();
		MySAX handler = new MySAX();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);
					// Parse each file provided on the
					// command line.
		for (int i = 0; i < 1; i++) {
			File xmlSource = new File("src\\data\\items-"+ i +".xml");
//			File xmlSource = new File("src\\data\\text.xml");
		    FileReader r = new FileReader(xmlSource);
		    xr.parse(new InputSource(r));
		}
    }


    public MySAX () {
    	super();
    }

    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }

    ////////////////////////////////////////////////////////////////////
    // Event handlers.
    ////////////////////////////////////////////////////////////////////


    public void startDocument () {
//    	System.out.println("Start document");
    	try {
			fileWriter = new FileWriter("src\\data\\text.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    public void endDocument () {
//    	System.out.println("End document");
    	try {
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    boolean iName = false;


    public void startElement (String uri, String name, String qName, Attributes atts) {
    	if(qName.equalsIgnoreCase("name")) {
    		iName = true;
    	}
//    	 System.out.println(name + ":");
  
//		if ("".equals (uri))
//		    System.out.println("Start element: " + qName);
//		else
//		    System.out.println("Start element: {" + uri + "}" + name);
//		
//		for (int i = 0; i < atts.getLength(); i++) {
//		    System.out.println("Attribute: " + atts.getLocalName(i) + "=" + atts.getValue(i));
//		}
    }


    public void endElement (String uri, String name, String qName) {
//		if ("".equals (uri))
//		    System.out.println("End element: " + qName);
//		else
//		    System.out.println("End element:   {" + uri + "}" + name);
    }
    
    
    public void characters (char ch[], int start, int length)
    {
//		System.out.print("Characters:    \"");
//		for (int i = start; i < start + length; i++) {
//			    switch (ch[i]) {
//				    case '\\':
//						System.out.print("\\\\");
//						break;
//				    case '"':
//						System.out.print("\\\"");
//						break;
//				    case '\n':
//						System.out.print("\\n");
//						break;
//				    case '\r':
//						System.out.print("\\r");
//						break;
//				    case '\t':
//						System.out.print("\\t");
//						break;
//				    default:
//						System.out.print(ch[i]);
//						break;
//		    }
//		}
//		System.out.print("\"\n");
    	if(iName) {
    		System.out.println("Name: " + new String(ch, start, length));
    		try {
				fileWriter.append(new String(ch, start, length));
				fileWriter.append('\n');
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		iName = false;
    	}
    	
    }

}
