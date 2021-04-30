package io.github.chaosawakens.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.BrownAntEntityModel;
import io.github.chaosawakens.entity.BrownAntEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BrownAntEntityRender extends GeoEntityRenderer<BrownAntEntity> {

    private IRenderTypeBuffer renderTypeBuffer;
    private BrownAntEntity antEntity;

    public BrownAntEntityRender(EntityRendererManager renderManager) {
        super(renderManager, new BrownAntEntityModel());
        this.shadowSize = 0.125F;
    }

    @Override
    public void renderEarly(BrownAntEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        this.antEntity = animatable;
        this.renderTypeBuffer = renderTypeBuffer;

        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(BrownAntEntity entity) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/ant/brown_ant.png");
    }

    @Override
    public RenderType getRenderType(BrownAntEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntitySmoothCutout(getTextureLocation(animatable));
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}