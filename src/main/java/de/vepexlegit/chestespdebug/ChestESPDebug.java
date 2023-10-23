package de.vepexlegit.chestespdebug;

import de.vepexlegit.chestespdebug.commands.ChestESPCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

@Mod(modid = "chestespdebug", version = "1.0")
public class ChestESPDebug {
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new ChestESPCommand());
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        for (TileEntity chest : Minecraft.getMinecraft().theWorld.loadedTileEntityList) {
            if (ChestESP.INSTANCE.isEnabled()) {
                double x = chest.getPos().getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX;
                double y = chest.getPos().getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY;
                double z = chest.getPos().getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
                if (chest instanceof TileEntityChest) {
                    GL11.glColor4f(1.0F, 0.5F, 0.0F, 1.0F);
                } else if (chest instanceof TileEntityEnderChest) {
                    GL11.glColor4f(0.5F, 0.0F, 0.5F, 1.0F);
                } else {
                    continue;
                }
                GL11.glBegin(GL11.GL_LINE_LOOP);
                GL11.glVertex3d(x, y, z);
                GL11.glVertex3d(x, y, z + 1);
                GL11.glVertex3d(x + 1, y, z + 1);
                GL11.glVertex3d(x + 1, y, z);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_LOOP);
                GL11.glVertex3d(x, y + 1, z);
                GL11.glVertex3d(x, y + 1, z + 1);
                GL11.glVertex3d(x + 1, y + 1, z + 1);
                GL11.glVertex3d(x + 1, y + 1, z);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINES);
                GL11.glVertex3d(x, y, z);
                GL11.glVertex3d(x, y + 1, z);
                GL11.glVertex3d(x, y, z + 1);
                GL11.glVertex3d(x, y + 1, z + 1);
                GL11.glVertex3d(x + 1, y, z + 1);
                GL11.glVertex3d(x + 1, y + 1, z + 1);
                GL11.glVertex3d(x + 1, y, z);
                GL11.glVertex3d(x + 1, y + 1, z);
                GL11.glEnd();
            }
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }
}
