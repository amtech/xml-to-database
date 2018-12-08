import java.util.ArrayList;

public class CheckExistValue {

	public static void main(String[] args) {
		ArrayList<String> scripts = new ArrayList<String>();
//		scripts.add("test3");
//		scripts.add("test4");
//		scripts.add("test5");
//		scripts.add("test");
		
		String[] arr = {"1","2","3","4"};
		System.out.println(checkExistValue(scripts,"test"));

	}
	
	 static boolean checkExistValue(ArrayList<String> scripts, String targetValue) {
	        for(String s: scripts){
	            if(s.equals(targetValue))
	                return true;
	        }
	        return false;
	    }

}
