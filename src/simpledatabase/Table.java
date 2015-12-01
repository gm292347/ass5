package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Table extends Operator{
	private BufferedReader br = null;
	private boolean getAttribute=false;
	private Tuple tuple;
	String attributeLine,dataTypeLine;
	
	public Table(String from){
		this.from = from;
		
		//Create buffer reader
		try{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
	}

	
	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	@Override
	public Tuple next(){
		try {
			if (!getAttribute){
				attributeLine = br.readLine();
				dataTypeLine = br.readLine();
				tuple = new Tuple(attributeLine, dataTypeLine ,br.readLine());
				tuple.setAttributeName();
				tuple.setAttributeType();
				tuple.setAttributeValue();
				getAttribute = true;
			}
			else{
				String tempLine = br.readLine();
				
				if (tempLine != null){
					tuple =  new Tuple(attributeLine, dataTypeLine,tempLine);
					tuple.setAttributeName();
					tuple.setAttributeType();
					tuple.setAttributeValue();
					return tuple;
				}
				else {
					return null;
				}
			}
			
			
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tuple;
	}
	

	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return tuple.getAttributeList();
	}

	public static void main(String args[]){
		Table a = new Table("Student");
	}

	
}