package net.xelat.mc.itools.gui;


import org.lwjgl.opengl.GL11;

import buildcraft.builders.TileFiller;
import buildcraft.core.DefaultProps;
import buildcraft.core.utils.StringUtil;
import net.minecraft.src.Container;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;
import net.xelat.mc.itools.InventoryTools;
import net.xelat.mc.itools.TileInventorySupplier;

public class GuiInventorySupplier extends GuiContainer {
	
	private IInventory playerInventory;
	private TileInventorySupplier supplier;
	
	private GuiSmallButton scanBtn;

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
		}
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
