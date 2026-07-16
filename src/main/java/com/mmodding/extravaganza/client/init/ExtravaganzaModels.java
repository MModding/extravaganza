package com.mmodding.extravaganza.client.init;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

// Blockbench Generated Models
public class ExtravaganzaModels {

	public static LayerDefinition createFestiveBall() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition modelPartData = meshdefinition.getRoot();

		PartDefinition ball = modelPartData.addOrReplaceChild("ball", CubeListBuilder.create().texOffs(-6, 0).addBox(-3.0f, 3.0f, -3.0f, 6.0f, 0.0f, 6.0f, new CubeDeformation(0.0f))
			.texOffs(-6, 0).addBox(-3.0f, -3.0f, -3.0f, 6.0f, 0.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.rotation(0.0f, 24.0f, 0.0f));
		ball.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(-6, 0).addBox(-3.0f, 0.0f, -3.0f, 6.0f, 0.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-3.0f, 0.0f, 0.0f, -3.1416f, 1.5708f, 1.5708f));
		ball.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(-6, 0).addBox(-3.0f, 0.0f, -3.0f, 6.0f, 0.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(3.0f, 0.0f, 0.0f, 1.5708f, -1.5708f, 0.0f));
		ball.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(-6, 0).addBox(-3.0f, 0.0f, -3.0f, 6.0f, 0.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, -3.0f, 1.5708f, 0.0f, 0.0f));
		ball.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(-6, 0).addBox(-3.0f, 0.0f, -3.0f, 6.0f, 0.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 3.0f, 1.5708f, 0.0f, 1.5708f));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public static LayerDefinition createHeliumBalloon() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition modelPartData = meshdefinition.getRoot();

		PartDefinition all = modelPartData.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.rotation(0.0f, 22.0f, 0.0f));

		PartDefinition rope = all.addOrReplaceChild("rope", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, 0.0f, 0.0f, 2.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.rotation(0.5f, -12.0f, 0.0f));

		PartDefinition rope2 = rope.addOrReplaceChild("rope2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0f, 0.0f, 0.0f, 2.0f, 5.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.rotation(0.0f, 4.0f, 0.0f));

		PartDefinition rope3 = rope2.addOrReplaceChild("rope3", CubeListBuilder.create().texOffs(4, 0).addBox(-1.0f, 1.0f, 0.0f, 2.0f, 5.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.rotation(0.0f, 4.0f, 0.0f));

		PartDefinition gro = all.addOrReplaceChild("gro", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.rotation(0.0f, -12.0f, 0.0f));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public static LayerDefinition createTurnstile() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition down = partdefinition.addOrReplaceChild("down", CubeListBuilder.create().texOffs(0, 54).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
			.texOffs(0, 33).addBox(-9.0F, -7.0F, -9.0F, 18.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition top = partdefinition.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -2.0F, -14.0F, 28.0F, 5.0F, 28.0F, new CubeDeformation(0.0F))
			.texOffs(48, 54).addBox(-14.0F, 3.0F, 0.0F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.0F))
			.texOffs(77, 81).addBox(1.0F, 3.0F, -12.0F, 11.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
			.texOffs(33, 81).addBox(1.0F, 3.0F, 1.0F, 11.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
			.texOffs(0, 72).addBox(-12.0F, 3.0F, -12.0F, 11.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
			.texOffs(53, 68).addBox(-12.0F, 3.0F, 1.0F, 11.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition cube_r1 = top.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(54, 33).addBox(-14.0F, -7.0F, 0.0F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}
