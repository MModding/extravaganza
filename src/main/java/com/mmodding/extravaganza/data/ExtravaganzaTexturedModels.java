package com.mmodding.extravaganza.data;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;

public class ExtravaganzaTexturedModels {

	public static final TexturedModel.Provider PAPER_LANTERN = TexturedModel.createDefault(
		TextureMapping::defaultTexture,
		ExtravaganzaModelTemplates.PAPER_LANTERN
	);

	public static final TexturedModel.Provider HANGING_PAPER_LANTERN = TexturedModel.createDefault(
		TextureMapping::defaultTexture,
		ExtravaganzaModelTemplates.HANGING_PAPER_LANTERN
	);

	public static final TexturedModel.Provider TRASH_CAN = TexturedModel.createDefault(
		TextureMapping::defaultTexture,
		ExtravaganzaModelTemplates.TRASH_CAN
	);

	public static final TexturedModel.Provider TRASH_CAN_LID = TexturedModel.createDefault(
		TextureMapping::defaultTexture,
		ExtravaganzaModelTemplates.TRASH_CAN_LID
	);

	public static final TexturedModel.Provider TRASH_CAN_LID_OPEN = TexturedModel.createDefault(
		TextureMapping::defaultTexture,
		ExtravaganzaModelTemplates.TRASH_CAN_LID_OPEN
	);
}
