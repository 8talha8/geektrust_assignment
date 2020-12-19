package family.model;

public class Person {
	String name;
	String parent;
	boolean male;
//int level;
//int order;
	String partner;

	public Person(String name, String parent, boolean male, String partner) {
		super();
		this.name = name;
		this.parent = parent;
		this.male = male;
		this.partner = partner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	/*
	 * public int getLevel() { return level; } public void setLevel(int level) {
	 * this.level = level; } public int getOrder() { return order; } public void
	 * setOrder(int order) { this.order = order; }
	 */
	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", parent=" + parent + ", male=" + male + "]";
	}

}
