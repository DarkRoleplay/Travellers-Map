package net.dark_roleplay.travellers_map.waypointer;

import com.mojang.serialization.Codec;
import net.dark_roleplay.travellers_map.util.MapManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public class Waypoint2{

	private ResourceLocation waypointIcon;
	private UUID uuid;
	private String name;
	private BlockPos pos;
	private boolean hasY;

	private boolean isDirty;



	public Waypoint2(UUID uuid){
		this.uuid = uuid;
	}

	public Waypoint2(UUID uuid, String name, BlockPos pos) {
		this(uuid);
		this.name = name;
		this.pos = pos;
	}

	public BlockPos getPos() {
		return pos;
	}

	public String getName() {
		return name;
	}

	/** ----- Helper Methods for Stream Based Rendering ----- **/

	public static int widestNameWidth = 0;
	private float lastRenderedYaw;
	private float lastRenderedOffset;
	private int nameWidth;

	public Waypoint2 setLastRenderedData(float lastRenderedYaw, float lastRenderedOffset) {
		this.lastRenderedYaw = lastRenderedYaw;
		this.lastRenderedOffset = lastRenderedOffset;
		return this;
	}

	public Waypoint2 initNameWidth(FontRenderer renderer){
		this.nameWidth = renderer.getStringWidth(this.name);
		if(this.nameWidth > widestNameWidth)
			widestNameWidth = this.nameWidth;
		return this;
	}

	public float getLastRenderedYaw() {
		return lastRenderedYaw;
	}

	public float getLastRenderedOffset() {
		return lastRenderedOffset;
	}

	public int getNameWidth() {
		return nameWidth;
	}
}
