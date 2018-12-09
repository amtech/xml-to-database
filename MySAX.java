import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.xml.sax.XMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

public class MySAX extends DefaultHandler
{
	static FileWriter itemFile = null, categoryItemFile = null, itemLocationFile = null, 
			bidFile = null, sellerFile = null, bidderFile = null, buyPriceFile = null;
	
	static String FIELD_DELIMITER = "$" ;
	static String NEW_LINE_SEPERATOR = "\n";
	
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
		buyPriceFile = new FileWriter("buyPriceFile.csv");
		
		// must be remove before submit assignment
//		for (int i = 0; i < 40; i++) {
//			File xmlSource = new File("items-"+ i +".xml");
////			File xmlSource = new File("text.xml");
//		    FileReader r = new FileReader(xmlSource);
//		    xr.parse(new InputSource(r));
//		}
//		
		for (int i = 0; i < args.length; i++) {
		    FileReader r = new FileReader(args[i]);
		    xr.parse(new InputSource(r));
		}
	
		itemFile.close();
		itemLocationFile.close();
		categoryItemFile.close();
		bidFile.close();
		sellerFile.close();
		bidderFile.close();
		buyPriceFile.close();
		
		// must be remove before submit assignment
//		System.out.println("Done");
    }


    public MySAX () {
    	super();
    }

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
    
    boolean checkExistValue(ArrayList<String> sellerArray, String targetValue) {
        for(String s: sellerArray){
            if(s.equalsIgnoreCase(targetValue))
                return true;
        }
        return false;
    }

    public void startDocument () {

    }


    public void endDocument () {

    }
    
    private boolean bItemId = false;
    private boolean bName = false;
    private boolean bCategory = false;
    
    private boolean bBuyPrice = false;
    
    private boolean bCurrently = false;
    private boolean bFirstBid = false;
    private boolean bNumberOfBids = false;
    
    private boolean bBidder = false;
    private boolean bBidderLocation = false;
    private boolean bBidderCountry = false;
    
    private boolean bTime = false;
    private boolean bAmount = false;
    
    private boolean bLatitude = false;
    private boolean bLongitude = false;
    private boolean bItemLocation = false;
    private boolean bItemCountry = false;
    private boolean bStarted = false;
    private boolean bEnds = false;
    private boolean bDescription = false;
    
    private boolean checkItemLocationCountry = false;
    private String itemID = null;
    private boolean checkExistLatLngLocation = false;
    
    private ArrayList<String> sellerArray = new ArrayList<String>();
    private ArrayList<String> bidderArray = new ArrayList<String>();
    
    private int categoryCount = 1;
    private int bidCount = 1;

    public void startElement (String uri, String name, String qName, Attributes atts) {
    	if(qName.equalsIgnoreCase("Item")){
            try {
            	 itemID = atts.getValue("ItemID");
            	 itemFile.append(itemID);
            	 itemFile.append(FIELD_DELIMITER);
			} catch (IOException e) {
				e.printStackTrace();
			}
        } else if(qName.equalsIgnoreCase("Name")) {
    		bName = true;
    	} else if(qName.equalsIgnoreCase("Category")) {
    		try {
    			String strI = Integer.toString(categoryCount);
				categoryItemFile.append(strI);
    			categoryItemFile.append(FIELD_DELIMITER);
				categoryItemFile.append(itemID);
				categoryItemFile.append(FIELD_DELIMITER);
				categoryCount++;
			} catch (IOException e) {
				e.printStackTrace();
			}
    		bCategory = true;
    	} else if(qName.equalsIgnoreCase("Currently")) {
    		bCurrently = true;
    	} else if(qName.equalsIgnoreCase("Buy_Price")) {
    		bBuyPrice = true;
    		try {
				buyPriceFile.append(itemID);
				buyPriceFile.append(FIELD_DELIMITER);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} else if(qName.equalsIgnoreCase("First_Bid")) {
    		bFirstBid = true;
    	} else if(qName.equalsIgnoreCase("Number_of_Bids")) {
    		bNumberOfBids = true;
    	} else if(qName.equalsIgnoreCase("Bidder")) {
    		try {
    			String bidderUserID = atts.getValue("UserID");
    			String strI = Integer.toString(bidCount);
				bidFile.append(strI);
    			bidFile.append(FIELD_DELIMITER);
    			bidCount++;
    			
    			bidFile.append(itemID);
				bidFile.append(FIELD_DELIMITER);
				bidFile.append(bidderUserID);
				bidFile.append(FIELD_DELIMITER);
				
            	if(!checkExistValue(bidderArray, bidderUserID)) {
            
            		bidderArray.add(bidderUserID);
            		
    				bidderFile.append(bidderUserID);
    				bidderFile.append(FIELD_DELIMITER);
    				bidderFile.append(atts.getValue("Rating"));
    				bidderFile.append(FIELD_DELIMITER);
    				
    				bBidder = true;  
//    				checkValidityForTimeAmount = true;
				} 
				
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} else if(qName.equalsIgnoreCase("Time")) {
    		bTime = true;
    	} else if(qName.equalsIgnoreCase("Amount")) {
    		bAmount = true;
    	} else if(qName.equalsIgnoreCase("Location")) {
    		if(checkItemLocationCountry) {
    			try {
    				if(atts.getValue("Latitude") != null) {
    					itemLocationFile.append(itemID);
    	           	 	itemLocationFile.append(FIELD_DELIMITER);
    	           	 	checkExistLatLngLocation = true;
    					itemLocationFile.append(atts.getValue("Latitude"));
    					itemLocationFile.append(FIELD_DELIMITER);
    					itemLocationFile.append(atts.getValue("Longitude"));
    					itemLocationFile.append(FIELD_DELIMITER);
    				}
    			} catch (IOException e) {
    				e.printStackTrace();
    			}	
    			bItemLocation = true;
    		} 
    		
    		if(bBidder) {
    			bBidderLocation = true;
    		}
    		
    	} else if(qName.equalsIgnoreCase("Country")) {
    		if(checkItemLocationCountry) {
    			bItemCountry = true;
    		} 
    		
    		if (bBidder) {
    			bBidderCountry = true;
    		}
    	} else if(qName.equalsIgnoreCase("Started")) {
    		bStarted = true;
    	} else if(qName.equalsIgnoreCase("Ends")) {
    		bEnds = true;
    	} else if(qName.equalsIgnoreCase("Seller")) {
            try {
            	String sellerUserID = atts.getValue("UserID");
            	itemFile.append(sellerUserID);
            	itemFile.append(FIELD_DELIMITER);
            	if(!checkExistValue(sellerArray, sellerUserID)) {
            		sellerArray.add(sellerUserID);
                	sellerFile.append(sellerUserID);
                	sellerFile.append(FIELD_DELIMITER);
                	sellerFile.append(atts.getValue("Rating"));
               	 	sellerFile.append(FIELD_DELIMITER);
               	 	sellerFile.append(NEW_LINE_SEPERATOR);	
            	}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} else if(qName.equalsIgnoreCase("Description")) {
    		bDescription = true;
    	} 
    }


    private CharSequence string(int count2) {
		// TODO Auto-generated method stub
		return null;
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
    	} else if(qName.equalsIgnoreCase("Buy_Price")) {
    		try {
				buyPriceFile.append(NEW_LINE_SEPERATOR);	
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} else if(qName.equalsIgnoreCase("Bids")) {
    		checkItemLocationCountry = true;
    	} else if(qName.equalsIgnoreCase("Bid")) {
    		try {
				bidFile.append(NEW_LINE_SEPERATOR);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} else if(qName.equalsIgnoreCase("Bidder") && bBidder) {
    		bBidder = false;
    		try {
				bidderFile.append(NEW_LINE_SEPERATOR);	
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} 
    }
    
    public boolean writeFile(FileWriter fileName, String value) {
    	try {
    		fileName.append(value);
    		fileName.append(FIELD_DELIMITER);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    private static String convertTimeToMySQL(String eBayTime) {
        SimpleDateFormat ebayTimePattern = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        SimpleDateFormat MySQLPattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String MySQLFormattedPattern = "";
        try {
            Date parsedEbayTime = ebayTimePattern.parse(eBayTime);
            MySQLFormattedPattern = MySQLPattern.format(parsedEbayTime);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return MySQLFormattedPattern;
    }
    
    
    public void characters (char ch[], int start, int length)
    {
    	String value = new String(ch, start, length);
    	
    	if(bName) {
    		bName = writeFile(itemFile, value);
    	} else if(bCategory) {
    		bCategory = writeFile(categoryItemFile, value);
    	} else if(bCurrently) {
    		bCurrently = writeFile(itemFile, strip(value));
    	} else if(bBuyPrice) {
    		bBuyPrice = writeFile(buyPriceFile, strip(value));
    	} else if(bFirstBid) {
    		bFirstBid = writeFile(itemFile, strip(value));
    	} else if(bNumberOfBids) {
    		bNumberOfBids = writeFile(itemFile, value);
    	} else if(bTime) {
    		bTime = writeFile(bidFile, convertTimeToMySQL(value));
    	} else if(bAmount) {
    		bAmount = writeFile(bidFile, strip(value));
    	} else if(bBidderLocation) {
    		bBidderLocation = writeFile(bidderFile, value);
    	} else if(bBidderCountry) {
    		bBidderCountry = writeFile(bidderFile, value);
    	} else if(bItemLocation) {
    		bItemLocation = writeFile(itemFile, value);
    	} else if(bItemCountry) {
    		bItemCountry = writeFile(itemFile, value);
    		checkItemLocationCountry = false;
    	} else if(bStarted) {
    		bStarted = writeFile(itemFile, convertTimeToMySQL(value));
    	} else if(bEnds) {
    		bEnds = writeFile(itemFile, convertTimeToMySQL(value));
    	} else if(bDescription) {
    		int maxDescription = 4000;
    		String description = value.replace(NEW_LINE_SEPERATOR, "").replace("\r", "");
    		if(description.length() > maxDescription) {
    			description = description.substring(0, maxDescription);
    		}
    		bDescription = writeFile(itemFile, description);
    	}
    }

}
