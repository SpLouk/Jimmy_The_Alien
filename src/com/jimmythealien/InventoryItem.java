package jimmyTheAlien;

public class InventoryItem {

	Item item;
	Inventory inventory;
	int quantity;
	protected Tool tool;

	public InventoryItem(Item i, Inventory i1) {

		item = i;
		inventory = i1;
		quantity = 1;

		if (item != null) {
			tool = item.getTool(inventory);
		} else {
			tool = new ToolHand(inventory);
		}

	}

	public InventoryItem(Item i, Inventory i1, int i2) {

		item = i;
		inventory = i1;

		if (i2 > 0) {
			quantity = i2;
		} else {
			quantity = 1;
		}

		if (item != null) {
			tool = item.getTool(inventory);
		} else {
			tool = new ToolHand(inventory);
		}
	}

	protected Tool getTool() {
		return tool;
	}

	protected void subtract() {
		quantity -= 1;

		if (quantity <= 0) {
			remove();
		}
	}

	protected void subtract(int i) {
		quantity -= i;

		if (quantity <= 0) {
			remove();
		}
	}

	protected void remove() {
		inventory.changeSlot(this, null, 1);
	}

	protected int add(int i) {
		int i1 = 0;

		quantity += i;

		if (quantity > 16) {
			i1 = quantity - 16;
			quantity -= i1;
		}

		return i1;
	}
}
