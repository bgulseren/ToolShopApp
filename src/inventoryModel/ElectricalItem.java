package inventoryModel;

import java.io.Serializable;

public class ElectricalItem extends Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int power;
	
	public ElectricalItem () {
		super();
		this.setItemType("electric");
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	public int getPower() {
		return power;
	}
	
	@Override
	public String toString() {
		return super.toString() + "%" + getPower();
	}
}
