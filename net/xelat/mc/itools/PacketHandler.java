package net.xelat.mc.itools;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import buildcraft.core.network.BuildCraftPacket;
import buildcraft.core.network.PacketSlotChange;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.xelat.mc.itools.network.PacketIds;
import net.xelat.mc.itools.network.SlotSearchResultPacket;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		TileEntity tile;
		InventoryTools.logger.info("Has packet!");
		try {
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.REQUEST_SCAN:
				InventoryTools.logger.info("Handled REQUEST_SCAN");
				PacketSlotChange scPacket = new PacketSlotChange();
				scPacket.readData(data);
				tile = ((EntityPlayer)player).worldObj.getBlockTileEntity(scPacket.posX, scPacket.posY, scPacket.posZ);
				if (tile != null && (tile instanceof TileInventorySupplier)) {
					BuildCraftPacket result = ((TileInventorySupplier)tile).getSearchResultPacket(((EntityPlayer)player).worldObj, scPacket.stack);
					PacketDispatcher.sendPacketToPlayer(result.getPacket(), player);
				}
				break;
			case PacketIds.RESPONSE_SCAN:
				InventoryTools.logger.info("Handled RESPONSE_SCAN");
				SlotSearchResultPacket ssPacket = new SlotSearchResultPacket();
				ssPacket.readData(data);
				tile = ((EntityPlayer)player).worldObj.getBlockTileEntity(ssPacket.posX, ssPacket.posY, ssPacket.posZ);
				if (tile != null && (tile instanceof TileInventorySupplier)) {
					((TileInventorySupplier)tile).setSearchResult(ssPacket.list);
				}
				break;
			default:
				InventoryTools.logger.info("Unknown packet " + Integer.toString(packetID));
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
