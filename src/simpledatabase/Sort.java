package simpledatabase;
import java.util.ArrayList;
import java.util.Comparator;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;

	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
	}
	
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		
		if (!tuplesResult.isEmpty()) {
			Tuple result = tuplesResult.get(0);
			for (int i = 0; i < tuplesResult.size()-1; ++i) {
				tuplesResult.set(i, tuplesResult.get(i+1));
			}
			tuplesResult.remove(tuplesResult.size()-1);
			return result;
		}
		
		AttributeComparator c = new AttributeComparator();
		
		Tuple tuple = this.getChild().next();
		if (tuple == null) return null;
		int attrIndex = 0;
		for (Attribute attr: tuple.getAttributeList()) {
			if (attr.getAttributeName().equals(this.orderPredicate)) 
				break;
			attrIndex++;
		}
		while (tuple != null) {
			if (tuplesResult.size() == 0) {
				tuplesResult.add(tuple);
			}
			else {
				tuplesResult.add(null);
				for (int i = 0; i < tuplesResult.size(); ++i) {
					if (tuplesResult.get(i) == null) {
						tuplesResult.set(i, tuple);
						break;
					}
					Attribute attr1 = tuplesResult.get(i).getAttributeList().get(attrIndex);
					Attribute attr2 = tuple.getAttributeList().get(attrIndex);
					if (c.compare(attr1, attr2) > 0) {

						for (int j = tuplesResult.size()-1; j > i; --j) {
							tuplesResult.set(j, tuplesResult.get(j-1));
							System.out.println("size: "+tuplesResult.size());
						}
						tuplesResult.set(i, tuple);
						break;
					}
				}
			}
			System.out.println("result size: "+tuplesResult.size());
			tuple = this.getChild().next();
		}
		for (Tuple tpl: tuplesResult) {
			System.out.println(tpl.getAttributeValue(0));
		}
		Tuple result = tuplesResult.get(0);
		for (int i = 0; i < tuplesResult.size()-1; ++i) {
			tuplesResult.set(i, tuplesResult.get(i+1));
		}
		tuplesResult.remove(tuplesResult.size()-1);
		System.out.println("result: "+result.getAttributeValue(0));
		return result;
		
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}
	
	class AttributeComparator implements Comparator<Attribute> {
		public int compare(Attribute attr1, Attribute attr2) {
			Type type = attr1.getAttributeType();
			switch (type.type) {
				case INTEGER:
					int intA = Integer.parseInt(attr1.getAttributeValue().toString());
					int intB = Integer.parseInt(attr2.getAttributeValue().toString());
					if (intA > intB) return 1;
					else if (intA == intB) return 0;
					else return -1;
					
				case DOUBLE:
					double doubleA = Double.parseDouble(attr1.getAttributeValue().toString());
					double doubleB = Double.parseDouble(attr2.getAttributeValue().toString());
					if (doubleA > doubleB) return 1;
					else if (doubleA == doubleB) return 0;
					else return -1;
					
				case LONG:
					long longA = Long.parseLong(attr1.getAttributeValue().toString());
					long longB = Long.parseLong(attr2.getAttributeValue().toString());
					if (longA > longB) return 1;
					else if (longA == longB) return 0;
					else return -1;		
					
				case SHORT:
					short shortA = Short.parseShort(attr1.getAttributeValue().toString());
					short shortB = Short.parseShort(attr2.getAttributeValue().toString());
					if (shortA > shortB) return 1;
					else if (shortA == shortB) return 0;
					else return -1;
					
				case FLOAT:
					float floatA = Float.parseFloat(attr1.getAttributeValue().toString());
					float floatB = Float.parseFloat(attr2.getAttributeValue().toString());
					if (floatA > floatB) return 1;
					else if (floatA == floatB) return 0;
					else return -1;
					
				case STRING:
					return attr1.getAttributeValue().toString().compareTo(attr2.getAttributeValue().toString());
					
				case BOOLEAN:
					return attr1.getAttributeValue().toString().compareTo(attr2.getAttributeValue().toString());
					
				case CHAR:
					return attr1.getAttributeValue().toString().compareTo(attr2.getAttributeValue().toString());
					
				case BYTE:
					return attr1.getAttributeValue().toString().compareTo(attr2.getAttributeValue().toString());
				
				default:
					return 0;
			}
		}
		
		public boolean equals(Attribute attr) {
			return true;
		}
	}
	
	/*public static void main(String [] args) {
		String selectText = "Name";
		String fromText = "Student";
		String joinText = "CourseEnroll";
		String whereText = "CourseEnroll.courseID=\"COMP2021\"";
		String orderText = "Name";
       
		FormRAtree tree = new FormRAtree(selectText,fromText,joinText,whereText,orderText);
		Operator root = tree.structureTree();
       
		Tuple tuple = root.next();
	}*/
}