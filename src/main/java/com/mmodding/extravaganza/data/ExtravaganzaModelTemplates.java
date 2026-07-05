package com.mmodding.extravaganza.data;

import com.mmodding.extravaganza.Extravaganza;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;

import java.util.Optional;

public class ExtravaganzaModelTemplates {

	public static final ModelTemplate PAPER_LANTERN = create("template_paper_lantern", TextureSlot.TEXTURE);
	public static final ModelTemplate HANGING_PAPER_LANTERN = create("template_hanging_paper_lantern", TextureSlot.TEXTURE);
	public static final ModelTemplate TRASH_CAN = create("template_trash_can", TextureSlot.TEXTURE);
	public static final ModelTemplate TRASH_CAN_LID = create("template_trash_can_lid", TextureSlot.TEXTURE);
	public static final ModelTemplate TRASH_CAN_LID_OPEN = create("template_trash_can_lid_open", TextureSlot.TEXTURE);

	private static ModelTemplate create(final String id, final TextureSlot... slots) {
		return new ModelTemplate(Optional.of(Extravaganza.createId("block/" + id)), Optional.empty(), slots);
	}
}
