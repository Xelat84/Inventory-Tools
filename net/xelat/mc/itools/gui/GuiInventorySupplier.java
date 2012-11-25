package net.xelat.mc.itools.gui;


import org.lwjgl.opengl.GL11;

import buildcraft.builders.TileFiller;
import buildcraft.core.DefaultProps;
import buildcraft.core.utils.StringUtil;
import net.minecraft.src.Container;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.xelat.mc.itools.InventoryTools;
import net.xelat.mc.itools.TileInventorySupplier;

public class GuiInventorySupplier extends GuiContainer {
	
	private TileInventorySupplier supplier;
	private ContainerInventorySupplier container;
	private GuiSmallButton scanBtn;
	
	 
	
	private boolean found = false;

	public GuiInventorySupplier(IInventory playerInventory, TileInventorySupplier supplier) {
		super(new ContainerInventorySupplier(playerInventory, supplier));
		this.supplier = supplier;
		xSize = 176;
		ySize = 222;
		
		container = (ContainerInventorySupplier)inventorySlots;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		controlList.clear();
		
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		scanBtn = new GuiSmallButton(0, j + 111, k + 10, 41, 16, StringUtil.localize("gui.isupplier.scan"));
		
		controlList.add(scanBtn);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if (button == scanBtn) {
			InventoryTools.logger.info("Scan!!!");
			
			ItemStack sampleItem = container.sampleInventory.getStackInSlot(0);
			if (sampleItem == null) {
				InventoryTools.logger.info("Sample empty!");
				return;
			}
			
			
			
			IInventory targetInventory = supplier.getTargetInventory();
			if (targetInventory == null) {
				InventoryTools.logger.info("Target Inventory not found :(");
				return;
			}
			
			int l = targetInventory.getSizeInventory();
			InventoryTools.logger.info("Target inventory size=" + Integer.toString(l));
			for (int i = 0; i < l; i++) {
				ItemStack targetItem = targetInventory.getStackInSlot(i);
				if (targetItem == null) {
					InventoryTools.logger.info("Stack in slot " + Integer.toString(i) + " is null");
					continue;
				}
				InventoryTools.logger.info("Item[" + Integer.toString(i) + "] = " + targetItem.getItemName());
				if (targetItem.itemID != sampleItem.itemID || (sampleItem.getHasSubtypes() && sampleItem.getItemDamage() != targetItem.getItemDamage())) {
					InventoryTools.logger.info(Integer.toString(sampleItem.itemID) + " != " + Integer.toString(targetItem.itemID));
					continue;
				}
				container.addFoundResult(sampleItem, i);
				InventoryTools.logger.info("Found item: " + targetItem.getItemName());
				found = true;
			}
			
		}
	}

	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		
		ContainerInventorySupplier container = (ContainerInventorySupplier) inventorySlots;
		
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		if (found) {
			for (int i = 0; i < 9; i++) {
				ItemStack item = container.resultInventory.getStackInSlot(i);
				if (item == null) continue;
				fontRenderer.drawString(Integer.toString(container.resultTargetSlotIds[i]), j + 8 + i * 18, k + 34, 0x000000);
			}
		}
		
		MaskInventorySupplier mask = supplier.getMaskSupplier();
		for (int i = 0; i < 9; i++) {
			ItemStack item = mask.getStackInSlot(i);
			if (item == null) continue;
			fontRenderer.drawString(Integer.toString(mask.getTargetSlotId(i)), j + 8 + i * 18, k + 76, 0x000000);
		}
		
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		//String title = StringUtil.localize("tile.inventorySupplierBlock");
		//fontRenderer.drawString(title, getCenteredOffset(title), 6, 0x404040);
		String sampleStr = StringUtil.localize("gui.isupplier.sample");
		fontRenderer.drawString(sampleStr, xSize / 2 - 12 - fontRenderer.getStringWidth(sampleStr), 14, 0x404040);
		//fontRenderer.drawString(StringUtil.localize("gui.inventory"), 8, 142, 0x404040);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {

		int i = mc.renderEngine.getTexture("/gfx/itools/gui/isupplier.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(i);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	

	protected int getCenteredOffset(String string) {
		return getCenteredOffset(string, xSize);
	}
	
	protected int getCenteredOffset(String string, int xWidth) {
		return (xWidth - fontRenderer.getStringWidth(string)) / 2;
	}
}
