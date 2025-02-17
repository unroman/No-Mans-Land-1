//// Made with Blockbench 4.12.2
//// Exported for Minecraft version 1.17 or later with Mojang mappings
//// Paste this class into your mod and generate all required imports
//
//
//public class sheep<T extends Entity> extends EntityModel<T> {
//	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
//	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "sheep"), "main");
//	private final ModelPart body;
//	private final ModelPart body_adult;
//	private final ModelPart sheared_tail;
//	private final ModelPart body_wool;
//	private final ModelPart tail_wool;
//	private final ModelPart body_baby;
//	private final ModelPart tail_baby;
//	private final ModelPart right_front_leg_baby;
//	private final ModelPart left_front_leg_baby;
//	private final ModelPart right_hind_leg_baby;
//	private final ModelPart left_hind_leg_baby;
//	private final ModelPart head_baby;
//	private final ModelPart right_ear_baby;
//	private final ModelPart left_ear_baby;
//	private final ModelPart head;
//	private final ModelPart right_ear;
//	private final ModelPart left_ear;
//	private final ModelPart right_front_leg;
//	private final ModelPart right_front_leg_wool;
//	private final ModelPart left_front_leg;
//	private final ModelPart left_front_leg_wool;
//	private final ModelPart right_hind_leg;
//	private final ModelPart right_hind_leg_wool;
//	private final ModelPart left_hind_leg;
//	private final ModelPart left_hind_leg_wool;
//
//	public sheep(ModelPart root) {
//		this.body = root.getChild("body");
//		this.body_adult = this.body.getChild("body_adult");
//		this.sheared_tail = this.body_adult.getChild("sheared_tail");
//		this.body_wool = this.body_adult.getChild("body_wool");
//		this.tail_wool = this.body_wool.getChild("tail_wool");
//		this.body_baby = this.body.getChild("body_baby");
//		this.tail_baby = this.body_baby.getChild("tail_baby");
//		this.right_front_leg_baby = this.body_baby.getChild("right_front_leg_baby");
//		this.left_front_leg_baby = this.body_baby.getChild("left_front_leg_baby");
//		this.right_hind_leg_baby = this.body_baby.getChild("right_hind_leg_baby");
//		this.left_hind_leg_baby = this.body_baby.getChild("left_hind_leg_baby");
//		this.head_baby = this.body.getChild("head_baby");
//		this.right_ear_baby = this.head_baby.getChild("right_ear_baby");
//		this.left_ear_baby = this.head_baby.getChild("left_ear_baby");
//		this.head = root.getChild("head");
//		this.right_ear = this.head.getChild("right_ear");
//		this.left_ear = this.head.getChild("left_ear");
//		this.right_front_leg = root.getChild("right_front_leg");
//		this.right_front_leg_wool = this.right_front_leg.getChild("right_front_leg_wool");
//		this.left_front_leg = root.getChild("left_front_leg");
//		this.left_front_leg_wool = this.left_front_leg.getChild("left_front_leg_wool");
//		this.right_hind_leg = root.getChild("right_hind_leg");
//		this.right_hind_leg_wool = this.right_hind_leg.getChild("right_hind_leg_wool");
//		this.left_hind_leg = root.getChild("left_hind_leg");
//		this.left_hind_leg_wool = this.left_hind_leg.getChild("left_hind_leg_wool");
//	}
//
//	public static LayerDefinition createBodyLayer() {
//		MeshDefinition meshdefinition = new MeshDefinition();
//		PartDefinition partdefinition = meshdefinition.getRoot();
//
//		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
//
//		PartDefinition body_adult = body.addOrReplaceChild("body_adult", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -7.0F, 8.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, -1.0F));
//
//		PartDefinition sheared_tail = body_adult.addOrReplaceChild("sheared_tail", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 7.0F));
//
//		PartDefinition body_wool = body_adult.addOrReplaceChild("body_wool", CubeListBuilder.create().texOffs(45, 1).addBox(-4.5F, -4.5F, -7.0F, 9.0F, 9.0F, 15.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//		PartDefinition tail_wool = body_wool.addOrReplaceChild("tail_wool", CubeListBuilder.create().texOffs(52, 9).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -2.3F, 8.3F));
//
//		PartDefinition body_baby = body.addOrReplaceChild("body_baby", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.5F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, 0.0F));
//
//		PartDefinition tail_baby = body_baby.addOrReplaceChild("tail_baby", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, 4.0F));
//
//		PartDefinition right_front_leg_baby = body_baby.addOrReplaceChild("right_front_leg_baby", CubeListBuilder.create().texOffs(14, 13).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 2.5F, -3.0F));
//
//		PartDefinition left_front_leg_baby = body_baby.addOrReplaceChild("left_front_leg_baby", CubeListBuilder.create().texOffs(14, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 2.5F, -3.0F));
//
//		PartDefinition right_hind_leg_baby = body_baby.addOrReplaceChild("right_hind_leg_baby", CubeListBuilder.create().texOffs(22, 13).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 2.5F, 3.0F));
//
//		PartDefinition left_hind_leg_baby = body_baby.addOrReplaceChild("left_hind_leg_baby", CubeListBuilder.create().texOffs(22, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 2.5F, 3.0F));
//
//		PartDefinition head_baby = body.addOrReplaceChild("head_baby", CubeListBuilder.create().texOffs(0, 13).addBox(-1.5F, -1.0778F, -3.0015F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, -3.0F, -0.6981F, 0.0F, 0.0F));
//
//		PartDefinition right_ear_baby = head_baby.addOrReplaceChild("right_ear_baby", CubeListBuilder.create().texOffs(0, 4).mirror().addBox(-1.5F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.0829F, -1.693F, 0.6981F, 0.0F, 0.0F));
//
//		PartDefinition left_ear_baby = head_baby.addOrReplaceChild("left_ear_baby", CubeListBuilder.create().texOffs(0, 4).addBox(-0.75F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0829F, -1.693F, 0.6981F, 0.0F, 0.0F));
//
//		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(94, 0).addBox(-2.0F, -3.0F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 22).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 33).addBox(-3.0F, -4.0F, -5.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 11.0F, -8.0F, -0.2738F, 0.0F, 0.0F));
//
//		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -0.9F, -3.0F));
//
//		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, -0.9F, -2.5F));
//
//		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.4F, 16.0F, -5.5F));
//
//		PartDefinition right_front_leg_wool = right_front_leg.addOrReplaceChild("right_front_leg_wool", CubeListBuilder.create().texOffs(78, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(42, 0).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.4F, 16.0F, -5.5F));
//
//		PartDefinition left_front_leg_wool = left_front_leg.addOrReplaceChild("left_front_leg_wool", CubeListBuilder.create().texOffs(78, 8).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(24, 22).mirror().addBox(-1.5F, -2.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.4F, 16.0F, 5.5F));
//
//		PartDefinition right_hind_leg_wool = right_hind_leg.addOrReplaceChild("right_hind_leg_wool", CubeListBuilder.create().texOffs(48, 25).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(36, 22).mirror().addBox(-1.5F, -2.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.4F, 16.0F, 5.5F));
//
//		PartDefinition left_hind_leg_wool = left_hind_leg.addOrReplaceChild("left_hind_leg_wool", CubeListBuilder.create().texOffs(64, 25).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//
//		return LayerDefinition.create(meshdefinition, 128, 64);
//	}
//
//	@Override
//	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//
//	}
//
//	@Override
//	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//		right_front_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//		left_front_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//		right_hind_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//		left_hind_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//	}
//}