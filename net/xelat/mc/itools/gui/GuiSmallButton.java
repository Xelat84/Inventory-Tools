package net.xelat.mc.itools.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;


public class GuiSmallButton extends GuiButton {

	public GuiSmallButton(int id, int x, int y, int width, int height, String label) {
		super(id, x, y, width, height, label);
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
        if (this.drawButton)
        {
            FontRenderer var4 = par1Minecraft.fontRenderer;
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/gfx/itools/gui/isupplier.png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int var5 = this.getHoverState(this.field_82253_i);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 176, var5 * 16, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 256 - this.width / 2, var5 * 16, this.width / 2, this.height);
            this.mouseDragged(par1Minecraft, par2, par3);
            int var6 = 14737632;

            if (!this.enabled)
            {
                var6 = -6250336;
            }
            else if (this.field_82253_i)
            {
                var6 = 16777120;
            }

            this.drawCenteredString(var4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, var6);
        }
	}
	
	
}
