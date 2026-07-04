package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import com.mmodding.library.core.api.AdvancedContainer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.world.phys.Vec3;

public class ExtravaganzaDataAttachments {

	public static final AttachmentType<Vec3> BEFORE_BALL_PIT = AttachmentRegistry.createPersistent(Extravaganza.createId("before_ball_pit"), Vec3.CODEC);

	public static void register(AdvancedContainer mod) {
	}
}
