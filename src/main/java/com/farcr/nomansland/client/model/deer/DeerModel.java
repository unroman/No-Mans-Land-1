package com.farcr.nomansland.client.model.deer;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeerModel<T extends AgeableMob> extends QuadrupedModel<T> {
    private final ModelPart root;
    public DeerModel(ModelPart root) {
        super(root, false, 10, 4, 2, 2, 24);
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(0, 0, 0, 0, 0, 0), PartPose.offset(0, 0, 0));

        PartDefinition bodyAdult = body.addOrReplaceChild("body_adult", CubeListBuilder.create().texOffs(0, 16).addBox(-4, -4, -16.5F, 8, 8, 17), PartPose.offset(0, 9, 7.5F));

        bodyAdult.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, -2, -8, 3, 4, 3), PartPose.offset(0, -4, 7.5F));

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

        PartDefinition bodyBaby = body.addOrReplaceChild("body_baby", CubeListBuilder.create().texOffs(0, 0).addBox(-2, -2, -3.5F, 4, 4, 7), PartPose.offset(0, 17, 0));

        bodyBaby.addOrReplaceChild("right_front_leg_baby", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0, -0.5F, 1, 5, 1), PartPose.offset(-1.5F, 2, -2));

        bodyBaby.addOrReplaceChild("left_front_leg_baby", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0, -0.5F, 1, 5, 1), PartPose.offset(1.5F, 2, -2));

        bodyBaby.addOrReplaceChild("right_hind_leg_baby", CubeListBuilder.create().texOffs(15, 0).addBox(-0.5F, 0, -0.5F, 1, 5, 1), PartPose.offset(-1.5F, 2, 3));

        bodyBaby.addOrReplaceChild("left_hind_leg_baby", CubeListBuilder.create().texOffs(15, 0).addBox(-0.5F, 0, -0.5F, 1, 5, 1), PartPose.offset(1.5F, 2, 3));

        PartDefinition headBaby = body.addOrReplaceChild("head_baby", CubeListBuilder.create().texOffs(22, 0).addBox(-1.5F, -5, -2, 3, 6, 3)
                .texOffs(8, 11).addBox(-1, -4, -4, 2, 2, 2), PartPose.offset(0, -1, -2.5F));

        headBaby.addOrReplaceChild("right_ear_baby", CubeListBuilder.create().texOffs(20, 0).mirror().addBox(-2, -2, 0, 2, 2, 0).mirror(false), PartPose.offsetAndRotation(-1.5F, -3, 0.5F, 0, 0.5236F, 0.1745F));

        headBaby.addOrReplaceChild("left_ear_baby", CubeListBuilder.create().texOffs(20, 0).addBox(0, -2, 0, 2, 2, 0), PartPose.offsetAndRotation(1.5F, -3, 0.5F, 0, -0.5236F, -0.1745F));

        bodyBaby.addOrReplaceChild("tail_baby", CubeListBuilder.create().texOffs(0, 11).addBox(-1, -2, -1, 2, 2, 2), PartPose.offset(0, -1, 3.5F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T deer, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(deer, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        ModelPart head = root.getChild("head");
        ModelPart body = root.getChild("body");
        ModelPart bodyAdult = body.getChild("body_adult");
        ModelPart rightHindLeg = root.getChild("right_hind_leg");
        ModelPart leftHindLeg = root.getChild("left_hind_leg");
        ModelPart rightFrontLeg = root.getChild("right_front_leg");
        ModelPart leftFrontLeg = root.getChild("left_front_leg");
        ModelPart headBaby = body.getChild("head_baby");
        ModelPart bodyBaby = body.getChild("body_baby");
        ModelPart rightFrontLegBaby = bodyBaby.getChild("right_front_leg_baby");
        ModelPart leftFrontLegBaby = bodyBaby.getChild("left_front_leg_baby");
        ModelPart rightHindLegBaby = bodyBaby.getChild("right_hind_leg_baby");
        ModelPart leftHindLegBaby = bodyBaby.getChild("left_hind_leg_baby");
        boolean baby = deer.isBaby();

        headBaby.xScale = 2;
        headBaby.yScale = 2;
        headBaby.zScale = 2;
        bodyBaby.xScale = 2;
        bodyBaby.yScale = 2;
        bodyBaby.zScale = 2;

        headBaby.xRot = head.xRot / 2;
        headBaby.yRot = head.yRot / 2;

        bodyAdult.visible = !baby;
        head.visible = !baby;
        leftHindLeg.visible = !baby;
        rightHindLeg.visible = !baby;
        leftFrontLeg.visible = !baby;
        rightFrontLeg.visible = !baby;
        bodyBaby.visible = baby;
        headBaby.visible = baby;

        rightHindLegBaby.xRot = rightHindLeg.xRot;
        leftHindLegBaby.xRot = leftHindLeg.xRot;
        rightFrontLegBaby.xRot = rightFrontLeg.xRot;
        leftFrontLegBaby.xRot = leftFrontLeg.xRot;
    }

    public ModelPart getHead() {
        return this.head;
    }
}
