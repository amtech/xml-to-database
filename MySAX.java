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
	
	String EURO_DELIMITER = "�" ;
	String NEW_LINE_SEPERATOR = "\n";
	
    public static void main (String args[]) throws Exception {
    	
		XMLReader xr = XMLReaderFactory.createXMLReader();
		MySAX handler = new MySAX();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);

		itemFile = new FileWriter("itemFile.csv");
		itemLocationFile = new FileWriter("itemLocationFile.csv");
		categoryItemFile = new FileWriter("categoryItemFile.csv");
		bidFile = new FileWriter("bidFile.csv");
		sellerFile = new FileWriter("sellerFile.csv");
		bidderFile = new FileWriter("bidderFile.csv");
		
		for (int i = 0; i < 40; i++) {
			File xmlSource = new File("items-"+ i +".xml");
//			File xmlSource = new File("text.xml");
		    FileReader r = new FileReader(xmlSource);
		    xr.parse(new InputSource(r));
		}
		
		itemFile.close();
		itemLocationFile.close();
		categoryItemFile.close();
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
    boolean bCategory = false;
    boolean bCurrently = false;
    boolean bFirstBid = false;
    boolean bNumberOfBids = false;
    
    boolean bBidder = false;
    
    boolean bTime = false;
    boolean bAmount = false;
    
    boolean bLatitude = false;
    boolean bLongitude = false;
    boolean bItemLocation = false;
    boolean bCountry = false;
    boolean bStarted = false;
    boolean bEnds = false;
    
    boolean checkUppperNodeBids = false;
    String itemID = null;
    boolean checkExistLatLngLocation = false;


    public void startElement (String uri, String name, String qName, Attributes atts) {
//    	System.out.println("Start Element");
    	if(qName.equalsIgnoreCase("Item")){
            try {
            	 itemID = atts.getValue("ItemID");
            	 itemFile.append(itemID);
            	 itemFile.append(EURO_DELIMITER);
			} catch (IOException e) {
				e.printStackTrace();
			}
        } else if(qName.equalsIgnoreCase("Name")) {
    		bName = true;
    	} else if(qName.equalsIgnoreCase("Category")) {
    		try {
				categoryItemFile.append(itemID);
				categoryItemFile.append(EURO_DELIMITER);
			} catch (IOException e) {
				e.printStackTrace();
			}
    		bCategory = true;
    	} else if(qName.equalsIgnoreCase("Currently")) {
    		bCurrently = true;
    	} else if(qName.equalsIgnoreCase("First_Bid")) {
    		bFirstBid = true;
    	} else if(qName.equalsIgnoreCase("Number_of_Bids")) {
    		bNumberOfBids = true;
    	} else if(qName.equalsIgnoreCase("Bid")) {
    		try {
				bidFile.append(itemID);
				bidFile.append(EURO_DELIMITER);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} else if(qName.equalsIgnoreCase("Bidder")) {
    		try {
    			String bidderUserID = atts.getValue("UserID");
				bidFile.append(bidderUserID);
				bidFile.append(EURO_DELIMITER);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} else if(qName.equalsIgnoreCase("Time")) {
    		bTime = true;
    	} else if(qName.equalsIgnoreCase("Amount")) {
    		bAmount = true;
    	} else if(qName.equalsIgnoreCase("Location") && checkUppperNodeBids) {
			try {
				if(atts.getValue("Latitude") != null) {
					itemLocationFile.append(itemID);
	           	 	itemLocationFile.append(EURO_DELIMITER);
	           	 	checkExistLatLngLocation = true;
					itemLocationFile.append(atts.getValue("Latitude"));
					itemLocationFile.append(EURO_DELIMITER);
					itemLocationFile.append(atts.getValue("Longitude"));
					itemLocationFile.append(EURO_DELIMITER);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
			bItemLocation = true;
    	} else if(qName.equalsIgnoreCase("Country")) {
    		bCountry = true;
    	} else if(qName.equalsIgnoreCase("Started")) {
    		bStarted = true;
    	} else if(qName.equalsIgnoreCase("Ends")) {
    		bEnds = true;
    	}
    }


    public void endElement (String uri, String name, String qName) {
		if(qName.equalsIgnoreCase("Item")) {
			try {
				itemFile.append(NEW_LINE_SEPERATOR);
		
				if(checkExistLatLngLocation) {
					itemLocationFile.append(NEW_LINE_SEPERATOR);
				} 
				checkExistLatLngLocation = false;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(qName.equalsIgnoreCase("Category")) {
    		try {
				categoryItemFile.append(NEW_LINE_SEPERATOR);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} else if(qName.equalsIgnoreCase("Bids")) {
			checkUppperNodeBids = true;
    	} else if(qName.equalsIgnoreCase("Bid")) {
    		try {
				bidFile.append(NEW_LINE_SEPERATOR);	
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
//    	System.out.println("Character");
    	String value = new String(ch, start, length);
    	
    	if(bName) {
    		bName = writeFile(itemFile, value);
    	} else if(bCategory) {
    		bCategory = writeFile(categoryItemFile, value);
    	} else if(bCurrently) {
    		bCurrently = writeFile(itemFile, value);
    	} else if(bFirstBid) {
    		bFirstBid = writeFile(itemFile, value);
    	} else if(bNumberOfBids) {
    		bNumberOfBids = writeFile(itemFile, value);
    	} else if(bTime) {
    		bTime = writeFile(bidFile, value);
    	} else if(bAmount) {
    		bAmount = writeFile(bidFile, strip(value));
    	} else if(bItemLocation) {
    		bItemLocation = writeFile(itemFile, value);
    		checkUppperNodeBids = false;
    	} else if(bCountry) {
    		bCountry = writeFile(itemFile, value);
    	} else if(bStarted) {
    		bStarted = writeFile(itemFile, value);
    	} else if(bEnds) {
    		bEnds = writeFile(itemFile, value);
    	}
    	
    }

}
