package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;


	public Projection(Operator child, String attributePredicate){
		
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
		
	}
	
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
	 Tuple tempRow = child.next();
	 if (tempRow != null) {
		 for (int i = 0; i < this.getAttributeList().size();i++){
			 if (tempRow.getAttributeName(i).equals(attributePredicate)){
//				 System.out.println(tempRow.getAttributeValue(i));
				 newAttributeList = new ArrayList<Attribute>();
				 newAttributeList.add(tempRow.getAttributeList().get(i));
				 return new Tuple(newAttributeList);
			 }
		 }
	 }
	 return null;
		
	}
		

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}
	public static void main(String args[]){
		Table a = new Table("Student");
		Projection b = new Projection(a,"Name");
		System.out.println(b.next().getAttributeName(0));
		System.out.println(b.next().getAttributeName(0));
		System.out.println(b.next().getAttributeName(0));
		

	}

}