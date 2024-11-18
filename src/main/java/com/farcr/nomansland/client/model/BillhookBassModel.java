package com.farcr.nomansland.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BillhookBassModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart tail;
    private final ModelPart tailFin;

    public BillhookBassModel(ModelPart root) {
        this.root = root;
        this.tail = root.getChild("body").getChild("tail");
        this.tailFin = tail.getChild("tail_fin");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2, -3.5F, -5.5F, 4, 7, 11), PartPose.offset(0, 20, 0));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-1, -2, -3, 2, 4, 3)
                .texOffs(19, 0).addBox(-1, -2, -8, 2, 2, 5), PartPose.offset(0, 1.5F, -5.5F));

        head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(28, 0).addBox(-0.5F, -1, -3, 1, 2, 3), PartPose.offset(0, 1, -3));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(33, 0).addBox(-1.5F, -2, 0, 3, 4, 5), PartPose.offset(0, -0.5F, 5.5F));

        tail.addOrReplaceChild("tail_fin", CubeListBuilder.create().texOffs(30, 4).addBox(0, -2.5F, 0, 0, 5, 5), PartPose.offset(0, 0.5F, 5));

        body.addOrReplaceChild("dorsal_fin1", CubeListBuilder.create().texOffs(0, 3).addBox(0, -2, -2, 0, 2, 4), PartPose.offset(0, -3.5F, -0.5F));

        body.addOrReplaceChild("dorsal_fin2", CubeListBuilder.create().texOffs(0, 5).addBox(0, -2, -1, 0, 2, 4), PartPose.offset(0, -3.5F, 3.5F));

        body.addOrReplaceChild("right_anal_fin", CubeListBuilder.create().texOffs(19, 3).mirror().addBox(0, 0, -1, 0, 2, 4).mirror(false), PartPose.offsetAndRotation(-1, 3.5F, 3.5F, 0, 0, 0.2618F));

        body.addOrReplaceChild("left_anal_fin", CubeListBuilder.create().texOffs(19, 5).addBox(0, 0, -1, 0, 2, 4), PartPose.offsetAndRotation(1, 3.5F, 3.5F, 0, 0, -0.2618F));

        body.addOrReplaceChild("right_pectoral_fin", CubeListBuilder.create().texOffs(5, 0).mirror().addBox(-2, 0, -1, 2, 0, 2).mirror(false), PartPose.offsetAndRotation(-2, 3.5F, -2.5F, 0, 0, -1.0472F));

        body.addOrReplaceChild("left_pectoral_fin", CubeListBuilder.create().texOffs(5, 0).addBox(0, 0, -1, 2, 0, 2), PartPose.offsetAndRotation(2, 3.5F, -2.5F, 0, 0, 1.0472F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = 1.0F;
        float f1 = 1.0F;

        if (!entity.isInWater()) {
            f = 1.3F;
            f1 = 1.7F;
        }

        tailFin.yRot = -f * 0.25F * Mth.sin(f1 * 0.6F * ageInTicks);
        tail.yRot = tailFin.yRot / 2;
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
