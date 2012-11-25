package net.xelat.mc.itools.gui;

import java.util.List;

import net.minecraft.src.ItemStack;
import net.xelat.mc.itools.LinkedItemStack;

public class SearchResultInventory extends BaseInventory {
	public int[] resultTargetSlotIds;
	private int _nextResultSlot = 0;
	
	public SearchResultInventory(int size) {
		super(size);
	}

	public void clearFoundResults() {
		for (int i = 0; i < _nextResultSlot; i++) {
			setInventorySlotContents(i, null);
		}
		_nextResultSlot = 0;
	}
	
	public void addFoundResult(ItemStack item, int targetSlotId) {
		if (_nextResultSlot == getSizeInventory()) {
			return;
		}
		
		resultTargetSlotIds[_nextResultSlot] = targetSlotId;
		setInventorySlotContents(_nextResultSlot, item);
		_nextResultSlot++;
	}

	public void update(List<LinkedItemStack> list) {
		clearFoundResults();
		for (int i = 0; i < list.size(); i++) {
			LinkedItemStack item = list.get(i);
			addFoundResult(item.item, item.slotId);
		}
	}
}
