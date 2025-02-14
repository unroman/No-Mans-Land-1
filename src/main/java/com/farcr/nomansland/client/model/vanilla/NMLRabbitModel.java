package com.farcr.nomansland.client.model.vanilla;

// Made with Blockbench 4.11.0
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Rabbit;

public class NMLRabbitModel {
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(16, 0).addBox(-1.5F, -3.0F, -4.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, -1.5F));

        PartDefinition ear_right = head.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(24, 12).mirror().addBox(-1.5F, -6.0F, 0.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, -3.0F, 0.0F));

        PartDefinition ear_left = head.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(24, 12).addBox(-0.5F, -6.0F, 0.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -3.0F, 0.0F));

        PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 21.0F, -2.0F));

        PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 21.0F, -2.0F));

        PartDefinition right_hind_foot = partdefinition.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(0, 9).addBox(-1.5F, -1.0F, -1.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 20.05F, 1.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition foot_right = right_hind_foot.addOrReplaceChild("foot_right", CubeListBuilder.create().texOffs(10, 9).mirror().addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 3.0F, -1.0F));

        PartDefinition left_hind_foot = partdefinition.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(0, 9).mirror().addBox(-0.5F, -1.0F, -1.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, 20.05F, 1.0F, 0.0F, -0.3491F, 0.0F));

        PartDefinition foot_left = left_hind_foot.addOrReplaceChild("foot_left", CubeListBuilder.create().texOffs(10, 9).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 3.0F, -1.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(19, 8).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 2.5F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -7.0F, -2.5F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);    }

    public static void setupAnim(Rabbit rabbit, ModelPart root, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float jumpRotation) {
        ModelPart head = root.getChild("head");
        ModelPart rightEar = root.getChild("ear_right");
        ModelPart leftEar = root.getChild("ear_left");
        ModelPart leftRearFoot = root.getChild("left_hind_foot");
        ModelPart rightRearFoot = root.getChild("right_hind_foot");
        ModelPart leftFrontLeg = root.getChild("body");
        ModelPart rightFrontLeg = root.getChild("body");
//
//
        float f = ageInTicks - (float) rabbit.tickCount;
        head.xRot = headPitch * 0.017453292F;
        rightEar.xRot = headPitch * 0.017453292F;
        leftEar.xRot = headPitch * 0.017453292F;
        head.yRot = netHeadYaw * 0.017453292F;
        rightEar.yRot = 0.2617994F;
        leftEar.yRot = 0.2617994F;
        jumpRotation = Mth.sin(rabbit.getJumpCompletion(f) * 3.1415927F);
//        leftHaunch.xRot = (this.jumpRotation * 50.0F - 21.0F) * 0.017453292F;
//        rightHaunch.xRot = (this.jumpRotation * 50.0F - 21.0F) * 0.017453292F;
//        leftRearFoot.xRot = this.jumpRotation * 50.0F * 0.017453292F;
//        rightRearFoot.xRot = this.jumpRotation * 50.0F * 0.017453292F;
//        leftFrontLeg.xRot = (this.jumpRotation * -40.0F - 11.0F) * 0.017453292F;
//        rightFrontLeg.xRot = (this.jumpRotation * -40.0F - 11.0F) * 0.017453292F;
    }

    public static void renderToBuffer(ModelPart root, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        root.getChild("head").render(poseStack, buffer, packedLight, packedOverlay, color);
        root.getChild("right_front_leg").render(poseStack, buffer, packedLight, packedOverlay, color);
        root.getChild("left_front_leg").render(poseStack, buffer, packedLight, packedOverlay, color);
        root.getChild("right_hind_foot").render(poseStack, buffer, packedLight, packedOverlay, color);
        root.getChild("left_hind_foot").render(poseStack, buffer, packedLight, packedOverlay, color);
        root.getChild("tail").render(poseStack, buffer, packedLight, packedOverlay, color);
        root.getChild("body").render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
