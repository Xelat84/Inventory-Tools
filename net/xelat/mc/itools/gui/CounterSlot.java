package net.xelat.mc.itools.gui;


import org.lwjgl.input.Keyboard;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public class CounterSlot extends CustomDummySlot implements IInteractiveSlot {
	
	private static final int MBUTTON_LEFT = 0;
	private static final int MBUTTON_RIGHT = 1;
	

	public CounterSlot(IInventory inventory, int slotId, int xPos, int yPos) {
		super(inventory, slotId, xPos, yPos);
	}
	
	public void processClick(int mouseButton, int isShift, EntityPlayer entityplayer) {
		ItemStack item = getStack();
		if (item == null) {
			return;
		}
		
		switch (mouseButton) {
		case MBUTTON_LEFT:
			if (item.stackSize == 1 || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
				clearStack();
				return;
			}
			
			if (isShift == 0) {
				item.stackSize--;
			}
			else {
				item.stackSize /= 2;
			}
			break;
		case MBUTTON_RIGHT:
			item.stackSize = Math.min(item.getMaxStackSize(), item.stackSize + (isShift == 0 ? 1 : item.stackSize));
			break;
		default:
			break;
		}
	}
}
