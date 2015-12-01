package simpledatabase;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuplesa;
	ArrayList<Tuple> tuplesb;
	int aIndex = 0;
	int bIndex = 0;
	
	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
//		System.out.println(leftChild.from + rightChild.from + joinPredicate);
		newAttributeList = new ArrayList<Attribute>();
		tuplesa = new ArrayList<Tuple>();
		tuplesb = new ArrayList<Tuple>();
		
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		System.out.println(aIndex + "" + bIndex);
		int x = 0;
		int y = 0;
		Tuple a = rightChild.next();
		Tuple b = leftChild.next();
		while (a != null) {		
			tuplesa.add(a);
			a = rightChild.next();		
		}

		while (b != null) {
			tuplesb.add(b);
			b = leftChild.next();
		}

		if ( !(tuplesb.isEmpty()) || !(tuplesa.isEmpty())){
			Tuple tempTuplea = tuplesa.get(0);
			Tuple tempTupleb = tuplesb.get(0);
			int aSize = tempTuplea.getAttributeList().size();
			int bSize = tempTupleb.getAttributeList().size();
			outerloop:
			while (x < aSize ) {
				y = 0;
				while(y < bSize ) {
					if (tempTuplea.getAttributeName(x).equals(tempTupleb.getAttributeName(y))){
						break outerloop;
					}
					y++;
				}
				x++;
			}
		}

		while (aIndex < tuplesa.size()){
			while (bIndex < tuplesb.size()){
				a = tuplesa.get(aIndex);
				b = tuplesb.get(bIndex++);

//				System.out.println("Course Enroll: " + a.getAttributeValue(x).toString() + " Student: " +b.getAttributeValue(y).toString() );
				if (a.getAttributeValue(x).equals(b.getAttributeValue(y))){
					
					newAttributeList = new ArrayList<Attribute>();
					newAttributeList.addAll(a.getAttributeList());
					newAttributeList.addAll(b.getAttributeList());
					return new Tuple(newAttributeList);
					
				}
				
			}
			if (bIndex == tuplesb.size()){
				bIndex = 0;
			}
			aIndex++;
		}
		

		return null;
	}
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public String[] concatenate(String[] a,String[] b){
		int aLen = a.length;
		int bLen = b.length;
		String[] c = (String[]) Array.newInstance(a.getClass().getComponentType(), aLen+ bLen);
		
		System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}
	public String getTypeName(Type a){
		String typeName = a.type.toString();
		String temp = typeName.substring(0, 1) + typeName.substring(1, typeName.length());
		return temp; 
		
	}
	public static void main(String args[]){
		String selectText = "Name";
		String fromText = "Student";
		String joinText = "CourseEnroll";
		String whereText = "CourseEnroll.courseID=\"COMP2021\"";
		String orderText = "";
		Table a = new Table("Student");
		Table b = new Table("CourseEnroll");
		Operator selection = new Selection(a,"CourseEnroll","courseID","\"COMP2021\"");
    	Operator selection1 = new Selection(b,"CourseEnroll","courseID","\"COMP2021\"");
    	Operator join = new Join(selection,selection1,joinText); 
    	Operator projection = new Projection(join,"Name");
    	System.out.println(projection.next().getAttributeValue(0));
    	System.out.println(projection.next().getAttributeValue(0));
    	System.out.println(projection.next().getAttributeValue(0));
//		Tuple d = join.next();
//		int i =0;
//		
//		while (i < 7){
//			System.out.println(d.getAttributeValue(i));
//			i++;
//		}
//		System.out.println(c.next().getAttributeList().
//		c.next();
		/*Tuple d = c.next();
		while (d != null){
			System.out.println(Arrays.deepToString(d.col2));
			d = c.next();
			System.out.println(d + "");
		}*/
		

	}

}