package simpledatabase;

import java.util.ArrayList;

public class Join extends Operator {

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;

	// Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();

	}

	/**
	 * It is used to return a new tuple which is already joined by the common
	 * attribute
	 * 
	 * @return the new joined tuple
	 */
	// The record after join with two tables
	@Override
	public Tuple next() {
		Tuple tpl1, tuple2;
		Tuple result = null;
		tpl1 = leftChild.next();
		tuple2 = rightChild.next();
		while (tpl1 != null) {
			tuples1.add(tpl1);
			tpl1 = leftChild.next();
		}
		if (tuple2 != null) {
			for (Tuple tuple1 : tuples1) {
				for (Attribute attr1: tuple1.getAttributeList()) {
					newAttributeList.add(attr1);
				}
				for (Attribute attr1: tuple1.getAttributeList()) {
					for (Attribute attr2: tuple2.getAttributeList()) {
						newAttributeList.add(attr2);
					}
					for (Attribute attr2: tuple2.getAttributeList()) {
						if (attr1.getAttributeName().equals(attr2.getAttributeName()) && attr1.getAttributeValue().equals(attr2.getAttributeValue())) {
							newAttributeList.remove(attr1);
							result = new Tuple(newAttributeList);
							newAttributeList = new ArrayList<Attribute>();
							return result;
						}
					}
				}
				newAttributeList = new ArrayList<Attribute>();
			}
		}
		return null;
	}

	/**
	 * The function is used to get the attribute list of the tuple
	 * 
	 * @return attribute list
	 */
	public ArrayList<Attribute> getAttributeList() {
		if (joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return (newAttributeList);
	}
}