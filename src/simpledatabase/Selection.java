package simpledatabase;
import java.util.ArrayList;

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
		attributeList = new ArrayList<Attribute>();

	}
	
	
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next(){
		Tuple tuple = getChild().next();
		Boolean hasAttr = false;
		while (tuple != null) {
			for (Attribute attr: tuple.getAttributeList()) {
				if (attr.getAttributeName().equals(this.whereAttributePredicate)) {
					hasAttr = true;
					if (attr.getAttributeValue().toString().equals(this.whereValuePredicate)) {
						return tuple;
					}
					else {
						tuple = getChild().next();
						break;
					}
				}
			}
			if (!hasAttr) {
				return tuple;
			}
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
	
	
}