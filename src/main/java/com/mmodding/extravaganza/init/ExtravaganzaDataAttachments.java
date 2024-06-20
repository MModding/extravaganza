package com.mmodding.extravaganza.init;

import com.mmodding.extravaganza.Extravaganza;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.math.Vec3d;

public class ExtravaganzaDataAttachments {

	public static final AttachmentType<Vec3d> BEFORE_ENTERING_POOL = AttachmentRegistry.createPersistent(Extravaganza.createId("before_entering_pool"), Vec3d.CODEC);

	public static void register() {}
}
