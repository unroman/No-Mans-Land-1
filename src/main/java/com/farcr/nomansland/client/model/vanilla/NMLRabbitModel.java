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

        PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 21.0F, -2.0F));

        PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 21.0F, -2.0F));

        PartDefinition right_hind_foot = partdefinition.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(10, 9).mirror().addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 23.05F, 1.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition left_hind_foot = partdefinition.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(10, 9).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 23.05F, 1.0F, 0.0F, -0.3491F, 0.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(19, 8).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 2.5F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -7.0F, -2.5F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition left_haunch = partdefinition.addOrReplaceChild("left_haunch", CubeListBuilder.create().texOffs(0, 9).mirror().addBox(-0.5F, -1.0F, 0.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 20.05F, 0.0F));

        PartDefinition right_haunch = partdefinition.addOrReplaceChild("right_haunch", CubeListBuilder.create().texOffs(0, 9).addBox(-1.5F, -1.0F, 0.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 20.05F, 0.0F));

        PartDefinition right_ear = partdefinition.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(24, 12).mirror().addBox(-1.5F, -6.0F, 0.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 14.0F, -1.5F));

        PartDefinition left_ear = partdefinition.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(24, 12).addBox(-0.5F, -6.0F, 0.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 14.0F, -1.5F));

        PartDefinition nose = partdefinition.addOrReplaceChild("nose", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public static void setupAnim(Rabbit rabbit, ModelPart root, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ModelPart rightHaunch = root.getChild("right_haunch");
        ModelPart leftHaunch = root.getChild("left_haunch");

        ModelPart rightFoot = root.getChild("right_hind_foot");
        ModelPart leftFoot = root.getChild("left_hind_foot");

        rightHaunch.xRot += 0.36651916F;
        leftHaunch.xRot += 0.36651916F;

        rightFoot.xRot = rightHaunch.xRot;
        leftFoot.xRot = leftHaunch.xRot;
    }
}
