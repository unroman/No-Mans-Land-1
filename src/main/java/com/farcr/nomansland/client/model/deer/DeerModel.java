package com.farcr.nomansland.client.model.deer;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeerModel<T extends Entity> extends QuadrupedModel<T> {
    public DeerModel(ModelPart root) {
        super(root, false, 10, 4, 2, 2, 24);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4, -4, -16.5F, 8, 8, 17), PartPose.offset(0, 9, 7.5F));

        body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, -2, -8, 3, 4, 3), PartPose.offset(0, -4, 7.5F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2, -11, -13, 4, 11, 5)
                .texOffs(0, 16).addBox(-1.5F, -10, -17, 3, 3, 4), PartPose.offset(0, 9, 2));

        head.addOrReplaceChild("left_antler", CubeListBuilder.create().texOffs(0, 41).mirror().addBox(-2, -8, -4, 2, 8, 11).mirror(false), PartPose.offsetAndRotation(-2, -11, -9, 0.1309F, -0.3491F, -0.2618F));

        head.addOrReplaceChild("right_antler", CubeListBuilder.create().texOffs(0, 41).addBox(0, -8, -4, 2, 8, 11), PartPose.offsetAndRotation(2, -11, -9, 0.1309F, 0.3491F, 0.2618F));

        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(18, 13).addBox(0.2657F, -2.8751F, -7.2505F, 3, 2, 0), PartPose.offsetAndRotation(-2, -9, -1, 0.1201F, 0.4205F, 0.2875F));

        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(18, 13).mirror().addBox(-3.2657F, -2.8751F, -7.2505F, 3, 2, 0).mirror(false), PartPose.offsetAndRotation(2, -9, -1, 0.1201F, -0.4205F, -0.2875F));

        partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(18, 0).addBox(0, 0, -6, 2, 11, 2), PartPose.offset(-4, 13, 0));

        partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(34, 0).addBox(0, 0, -6, 2, 11, 2), PartPose.offset(-4, 13, 12));

        partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-2, 0, -6, 2, 11, 2), PartPose.offset(4, 13, 0));

        partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(42, 0).addBox(-2, 0, -6, 2, 11, 2), PartPose.offset(4, 13, 12));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public ModelPart getHead() {
        return this.head;
    }
}
