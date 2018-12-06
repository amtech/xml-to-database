import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import org.xml.sax.XMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

public class MySAX extends DefaultHandler
{
	static FileWriter itemFile = null, categoryFile = null, categoryItemFile = null, itemLocationFile = null, 
			bidFile = null, sellerFile = null, bidderFile = null;
	
	String EURO_DELIMITER = "€" ;
	String NEW_LINE_SEPERATOR = "\n";
	
    public static void main (String args[]) throws Exception {
    	
		XMLReader xr = XMLReaderFactory.createXMLReader();
		MySAX handler = new MySAX();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);

		itemFile = new FileWriter("itemFile.csv");
		categoryFile = new FileWriter("categoryFile.csv");
		categoryItemFile = new FileWriter("categoryItemFile.csv");
		itemLocationFile = new FileWriter("itemLocationFile.csv");
		bidFile = new FileWriter("bidFile.csv");
		sellerFile = new FileWriter("sellerFile.csv");
		bidderFile = new FileWriter("bidderFile.csv");
		
		for (int i = 0; i < 40; i++) {
			File xmlSource = new File("items-"+ i +".xml");
		    FileReader r = new FileReader(xmlSource);
		    xr.parse(new InputSource(r));
		}
		
		itemFile.close();
		categoryFile.close();
		categoryItemFile.close();
		itemLocationFile.close();
		bidderFile.close();
		sellerFile.close();
		bidderFile.close();
		
		System.out.println("Done");
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

    public void startDocument () {

    }


    public void endDocument () {

    }
    
    boolean bItemId = false;
    boolean bName = false;
    boolean bCurrently = false;
    boolean bFirstBid = false;
    boolean bNumberOfBids = false;
    boolean bCountry = false;
    boolean bStarted = false;
    boolean bEnds = false;


    public void startElement (String uri, String name, String qName, Attributes atts) {
    	if(name.equalsIgnoreCase("Item")){
            try {
            	 itemFile.append(atts.getValue("ItemID"));
            	 itemFile.append(EURO_DELIMITER);
			} catch (IOException e) {
				e.printStackTrace();
			}
        } else if(qName.equalsIgnoreCase("Name")) {
    		bName = true;
    	} else if(qName.equalsIgnoreCase("Currently")) {
    		bCurrently = true;
    	} else if(qName.equalsIgnoreCase("First_Bid")) {
    		bFirstBid = true;
    	}
    }


    public void endElement (String uri, String name, String qName) {
		if(qName.equalsIgnoreCase("Item")) {
			try {
				itemFile.append(NEW_LINE_SEPERATOR);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    public boolean writeFile(FileWriter fileName, String value) {
    	try {
    		fileName.append(value);
    		fileName.append(EURO_DELIMITER);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    
    public void characters (char ch[], int start, int length)
    {
    	String value = new String(ch, start, length);
    	
    	if(bName) {
    		bName = writeFile(itemFile, value);
    	} else if(bCurrently) {
    		bCurrently = writeFile(itemFile, value);
    	} else if(bFirstBid) {
    		bFirstBid = writeFile(itemFile, value);
    	}
    	
    }

}
