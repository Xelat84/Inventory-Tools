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
	
	private IInventory playerInventory;
	private TileInventorySupplier supplier;
	
	private GuiSmallButton scanBtn;
	
	private boolean found = false;

	public GuiInventorySupplier(IInventory playerInventory, TileInventorySupplier supplier) {
		super(new ContainerInventorySupplier(playerInventory, supplier, new TempInventory(10)));
		this.playerInventory = playerInventory;
		this.supplier = supplier;
		xSize = 176;
		ySize = 222;
		
		//TODO Use TempInventory for Sample & Find slots
		
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
			
			ContainerInventorySupplier container = (ContainerInventorySupplier) inventorySlots;
			if (container.dummyInventory.getStackInSlot(0) == null) {
				return;
			}
			
			container.foundSlotIds[0] = 13;
			container.dummyInventory.setInventorySlotContents(1, container.dummyInventory.getStackInSlot(0).copy());
			found = true;
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
				ItemStack item = container.dummyInventory.getStackInSlot(i + 1);
				if (item == null) continue;
				fontRenderer.drawString(Integer.toString(container.foundSlotIds[i]), j + 8 + i * 18, k + 34, 0x000000);
			}
		}
		
		IInventory mask = supplier.getMask();
		int[] maskSlotIds = supplier.getMaskSlotIds();
		for (int i = 0; i < 9; i++) {
			ItemStack item = mask.getStackInSlot(i);
			if (item == null) continue;
			fontRenderer.drawString(Integer.toString(maskSlotIds[i]), j + 8 + i * 18, k + 76, 0x000000);
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
