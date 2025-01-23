package com.farcr.nomansland.client.model.deer;

import com.farcr.nomansland.common.entity.deer.Deer;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeerModel<T extends Deer> extends QuadrupedModel<T> {
    protected final ModelPart babyHead;
    protected final ModelPart babyBody;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightHindBabyLeg;
    private final ModelPart leftHindBabyLeg;
    private final ModelPart rightFrontBabyLeg;
    private final ModelPart leftFrontBabyLeg;

    public DeerModel(ModelPart root) {
        super(root, false, 0, 0, 1, 1, 0);
        rightHindLeg = root.getChild("right_hind_leg");
        leftHindLeg = root.getChild("left_hind_leg");
        rightFrontLeg = root.getChild("right_front_leg");
        leftFrontLeg = root.getChild("left_front_leg");

        babyBody = root.getChild("baby_body");
        babyHead = babyBody.getChild("baby_head");

        rightHindBabyLeg = babyBody.getChild("right_hind_baby_leg");
        leftHindBabyLeg = babyBody.getChild("left_hind_baby_leg");
        rightFrontBabyLeg = babyBody.getChild("right_front_baby_leg");
        leftFrontBabyLeg = babyBody.getChild("left_front_baby_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition adultBody = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4, -4, -16.5F, 8, 8, 17), PartPose.offset(0, 9, 7.5F));

        adultBody.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, -2, -8, 3, 4, 3), PartPose.offset(0, -4, 7.5F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2, -11, -13, 4, 11, 5)
                .texOffs(0, 16).addBox(-1.5F, -10, -17, 3, 3, 4), PartPose.offset(0, 9, 2));

        head.addOrReplaceChild("left_antler", CubeListBuilder.create().texOffs(0, 41).mirror().addBox(-2, -8, -4, 2, 8, 11).mirror(false), PartPose.offsetAndRotation(-2, -11, -9, 0.1309F, -0.3491F, -0.2618F));

        head.addOrReplaceChild("right_antler", CubeListBuilder.create().texOffs(0, 41).addBox(0, -8, -4, 2, 8, 11), PartPose.offsetAndRotation(2, -11, -9, 0.1309F, 0.3491F, 0.2618F));

        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(18, 13).addBox(0.2657F, -2.8751F, -7.2505F, 3, 2, 0, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2, -9, -1, 0.1201F, 0.4205F, 0.2875F));

        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(18, 13).mirror().addBox(-3.2657F, -2.8751F, -7.2505F, 3, 2, 0, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(2, -9, -1, 0.1201F, -0.4205F, -0.2875F));

        partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(18, 0).addBox(0, 0, -6, 2, 11, 2), PartPose.offset(-4, 13, 0));

        partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(34, 0).addBox(0, 0, -6, 2, 11, 2), PartPose.offset(-4, 13, 12));

        partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-2, 0, -6, 2, 11, 2), PartPose.offset(4, 13, 0));

        partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(42, 0).addBox(-2, 0, -6, 2, 11, 2), PartPose.offset(4, 13, 12));

        PartDefinition babyBody = partdefinition.addOrReplaceChild("baby_body", CubeListBuilder.create().texOffs(0, 0).addBox(-2, -2, -3.5F, 4, 4, 7), PartPose.offset(0, 17, 0));

        babyBody.addOrReplaceChild("right_front_baby_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0, -0.5F, 1, 5, 1), PartPose.offset(-1.5F, 2, -2));

        babyBody.addOrReplaceChild("left_front_baby_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0, -0.5F, 1, 5, 1), PartPose.offset(1.5F, 2, -2));

        babyBody.addOrReplaceChild("right_hind_baby_leg", CubeListBuilder.create().texOffs(15, 0).addBox(-0.5F, 0, -0.5F, 1, 5, 1), PartPose.offset(-1.5F, 2, 3));

        babyBody.addOrReplaceChild("left_hind_baby_leg", CubeListBuilder.create().texOffs(15, 0).addBox(-0.5F, 0, -0.5F, 1, 5, 1), PartPose.offset(1.5F, 2, 3));

        PartDefinition babyHead = babyBody.addOrReplaceChild("baby_head", CubeListBuilder.create().texOffs(22, 0).addBox(-1.5F, -5, -2, 3, 6, 3)
                .texOffs(8, 11).addBox(-1, -4, -4, 2, 2, 2), PartPose.offset(0, -1, -2.5F));

        babyHead.addOrReplaceChild("right_ear_baby", CubeListBuilder.create().texOffs(20, 0).mirror().addBox(-2, -2, 0, 2, 2, 0).mirror(false), PartPose.offsetAndRotation(-1.5F, -3, 0.5F, 0, 0.5236F, 0.1745F));

        babyHead.addOrReplaceChild("left_ear_baby", CubeListBuilder.create().texOffs(20, 0).addBox(0, -2, 0, 2, 2, 0), PartPose.offsetAndRotation(1.5F, -3, 0.5F, 0, -0.5236F, -0.1745F));

        babyBody.addOrReplaceChild("tail_baby", CubeListBuilder.create().texOffs(0, 11).addBox(-1, -2, -1, 2, 2, 2), PartPose.offset(0, -1, 3.5F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T deer, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(deer, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        head.xRot /= 3;
        head.yRot /= 3;
        head.xRot = (float) Math.min(Math.abs(head.xRot), Math.PI/4);
        head.yRot = (float) Math.min(Math.abs(head.yRot), Math.PI*0.0625F);

        babyHead.xRot = head.xRot / 2;
        babyHead.yRot = head.yRot / 2;

        leftHindLeg.xRot /= 5;
        rightHindLeg.xRot /= 5;
        leftFrontLeg.xRot /= 5;
        rightFrontLeg.xRot /= 5;

        rightHindBabyLeg.xRot = rightHindLeg.xRot;
        leftHindBabyLeg.xRot = leftHindLeg.xRot;
        rightFrontBabyLeg.xRot = rightFrontLeg.xRot;
        leftFrontBabyLeg.xRot = leftFrontLeg.xRot;
    }

    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);

        boolean baby = entity.isBaby();
        head.visible = !baby;
        body.visible = !baby;
        rightHindLeg.visible = !baby;
        leftHindLeg.visible = !baby;
        rightFrontLeg.visible = !baby;
        leftFrontLeg.visible = !baby;
        babyHead.visible = baby;
        babyBody.visible = baby;
        rightHindBabyLeg.visible = baby;
        leftHindBabyLeg.visible = baby;
        rightFrontBabyLeg.visible = baby;
        leftFrontBabyLeg.visible = baby;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(head);
    }


    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(body, babyBody, leftFrontLeg, rightFrontLeg, leftHindLeg, rightHindLeg);
    }
}
