package jimmyTheAlien;

public class Inventory {

	protected InventoryItem[] items;
	protected InventoryItem inHand;
	protected int selected = 0;
	protected EntityLiving entity;

	public Inventory(int s, EntityLiving e) {

		s += 9;
		items = new InventoryItem[s];

		for (int i = 0; i < s; i++) {
			items[i] = new InventoryItem(null, this);
		}

		inHand = new InventoryItem(null, this);
		
		entity = e;

	}

	protected InventoryItem getSelectedSlot() {
		return items[selected];
	}

	protected InventoryItem getSlot(int i) {
		return items[i];
	}

	protected void cycleSlot(int i) {
		items[selected].getTool().inActive();

		if (i < 9 && i >= 0) {
			selected = i;
		}
	}

	protected void cycleSlot(boolean b) {
		items[selected].getTool().inActive();

		if (b) {
			selected += 1;
			if (selected > 8) {
				selected = 0;
			}
		} else {
			selected -= 1;

			if (selected < 0) {
				selected = 8;
			}
		}
	}

	protected int getSize() {
		return items.length;
	}

	protected boolean addItem(Item i) {
		boolean b = false;

		for (int a = 0; a < items.length; a++) {
			if (items[a].item != null
					&& i.getClass() == items[a].item.getClass()
					&& items[a].quantity < 16) {
				items[a].add(1);
				return true;
			}
		}

		for (int a = 0; a < items.length; a++) {
			if (items[a].item == null) {
				if (selected != a) {
					changeSlot(a, i, 1);
					return true;
				} else {
					b = true;
				}
			}
		}

		if (b) {
			changeSlot(selected, i, 1);
			return true;
		}

		return false;
	}

	protected void changeSlot(InventoryItem i, Item i2, int i1) {
		for (int a = 0; a < items.length; a++) {
			if (i.equals(items[a])) {
				items[a].tool.remove();
				items[a] = new InventoryItem(i2, this, i1);
				return;
			}
		}

		changeHandSlot(i2, i1);
	}

	protected void changeSlot(int i, Item i2, int q) {

		items[i].tool.remove();
		items[i] = new InventoryItem(i2, this, q);
	}

	protected void changeHandSlot(Item i2, int i) {

		inHand = new InventoryItem(i2, this, i);

	}
}
