package inventoryModel;

import java.io.Serializable;

public class RegularItem extends Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegularItem () {
		super();
		this.setItemType("nonelectric");
	}
	
	@Override
	protected void setPower(int itemPower) {
		return;
	}
	
	@Override
	public String toString() {
		return super.toString() + "%" + "0";
	}

}
