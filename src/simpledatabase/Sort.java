package simpledatabase;
import java.util.ArrayList;
import java.util.Arrays;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;
	boolean imported = false;
	int indexNow;

	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
	}
	public int getAttributeNameIndex(Tuple t,String name){
		int i = 0;
		while (i < t.getAttributeList().size()){
			if (t.getAttributeName(i).equals(name)){
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public int findOrderKeyIndex(){
		int count = 0;
		for (Attribute a : newAttributeList){
			if (a.getAttributeName().equals(orderPredicate)){
				return count;
			}
			count++;
		}
		return -1;
	}
	public void sortTuples(){
		int i = 0;
		int sortedIndex = 0;
		String tempa;
		String tempb;
		int index = getAttributeNameIndex(tuplesResult.get(0),orderPredicate);
		
		for (i = 0; i < tuplesResult.size() - 1; i++){
			

			for (int j = 0; j < tuplesResult.size() - 1 - i; j++){
				tempa = tuplesResult.get(j).getAttributeValue(index).toString();
				tempb = tuplesResult.get(j+1).getAttributeValue(index).toString();
				if (tempa.compareTo(tempb) > 0){
					
					Tuple temp = tuplesResult.get(j);
					
					tuplesResult.set(j, tuplesResult.get(j+ 1));
					tuplesResult.set(j+1,temp);
				}
			}
		}

	}
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	
	public Tuple next(){
		if (!imported){
			Tuple a = child.next();
			while (a != null){
				tuplesResult.add(a);
				a = child.next();
			}			
			sortTuples();
			imported = true;
		}
		if (!(tuplesResult.isEmpty())) {
			return tuplesResult.remove(0);
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
		  String selectText;
		     String fromText;
		     String joinText;
		     String whereText;
		     String orderText;
		    FormRAtree tree;
		    Operator root;
		    Tuple tuple;
			boolean next = true;
		selectText = "Name";
	       fromText = "Student";
	       joinText = "CourseEnroll";
	       whereText = "CourseEnroll.courseID=\"COMP2021\"";
	       orderText = "Name";
	       
		Operator table = new Table(fromText);
    	Operator table1 = new Table(joinText);
    	Operator selection = new Selection(table,"CourseEnroll","courseID","\"COMP2021\"");
    	Operator selection1 = new Selection(table1,"CourseEnroll","courseID","\"COMP2021\"");
    	Operator join = new Join(selection,selection1,joinText); 
//    	join.next();
    	Operator sort = new Sort(join,orderText);
    	Operator projection = new Projection(sort,"Name");
//    	projection.next();
    	System.out.println(projection.next().getAttributeValue(0));
    	System.out.println(projection.next().getAttributeValue(0));
//    	System.out.println(projection.next().getAttributeValue(0));
    	
    	
//    	System.out.println(Arrays.deepToString(sort.next().col2));

//    	sort.next();
//    	Operator projection = new Projection(sort,selectText);
    	
	}

}