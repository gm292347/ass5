package simpledatabase;
import java.util.ArrayList;
import java.util.Arrays;

public class Selection extends Operator{
	
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;

	
	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate, String whereValuePredicate) {
		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
//		System.out.println(whereTablePredicate + whereAttributePredicate + whereValuePredicate);
		attributeList = new ArrayList<Attribute>();

	}
	
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next(){
		Tuple tempRow = child.next();
		if (child.from.equals(whereTablePredicate)){
			while (tempRow != null){
				for (int i = 0;i < this.getAttributeList().size();i++){
					if (tempRow.getAttributeName(i).equals(whereAttributePredicate) && (tempRow.getAttributeValue(i).toString().equals(whereValuePredicate)) ){
						return tempRow;
					}
				}
				tempRow = child.next();
			}
		}
		else {
			return tempRow;
		}
		
		return null;
		
			
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		
		return(child.getAttributeList());
	}
	public static void main(String args[]){
		Table a = new Table("Student");
		Table b = new Table("CourseEnroll");
		Operator selection = new Selection(a,"CourseEnroll","courseID","\"COMP2021\"");
		System.out.println(selection.next().getAttributeName(0));
		
	}

	
}