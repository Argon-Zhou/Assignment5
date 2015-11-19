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
		Tuple tuple = getChild().next();
		if (tuple == null) return null;
		for (int i = 0; i < tuple.getAttributeList().size(); ++i) {
			Attribute attr = tuple.getAttributeList().get(i);
			if (this.attributePredicate.equals(tuple.getAttributeName(i))) {
				if (newAttributeList.size() == 0) newAttributeList.add(attr);
				else newAttributeList.set(0, attr);
				Tuple result = new Tuple(newAttributeList);	
				return result;
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

}