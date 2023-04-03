package net.avamaco.alchemicalutilities.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.avamaco.alchemicalutilities.AlchemicalUtilities;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SynthesisStationScreen extends AbstractContainerScreen<SynthesisStationMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(AlchemicalUtilities.MOD_ID, "textures/gui/synthesis_station_gui.png");
    public SynthesisStationScreen(SynthesisStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        this.blit(pPoseStack, x + 25, y + 39, 230, 0, menu.getScaledFuel(), 4);

        if (menu.isCrafting()) {
            renderArrow(pPoseStack, menu.getScaledProgress(), x, y);
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    private void renderArrow(PoseStack poseStack, int progress, int x ,int y) {
        this.blit(poseStack, x + 42, y + 26, 176, 0, clamp(progress, 0, 16), 4);
        progress -= 16;
        this.blit(poseStack, x + 54, y + 30, 188, 4, 4, clamp(progress, 0, 9));
        progress -= 13;
        this.blit(poseStack, x + 54, y + 43, 188, 17, 4, clamp(progress, 0, 9));
        progress -= 9;
        this.blit(poseStack, x + 54 - clamp(progress, 0, 9), y + 48, 188 - clamp(progress, 0, 9), 22, clamp(progress, 0, 9), 4);
        progress -= 9;
        this.blit(poseStack, x + 45, y + 48 - clamp(progress, 0, 9), 179, 22 - clamp(progress, 0, 9), 4, clamp(progress, 0, 9));
        progress -= 9;
        this.blit(poseStack, x + 49, y + 26, 183, 0, clamp(progress, 0, 46), 29);
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
