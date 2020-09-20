package net.dark_roleplay.travellers_map.waypointer;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WaypointPosition {

	private RegistryKey<World> dimension;
	private BlockPos pos;
	private boolean isVisible;

	public WaypointPosition(RegistryKey<World> dimension, BlockPos pos, boolean isVisible) {
		this.dimension = dimension;
		this.pos = pos;
		this.isVisible = isVisible;
	}

	public RegistryKey<World> getDimension() {
		return dimension;
	}

	public void setDimension(RegistryKey<World> dimension) {
		this.dimension = dimension;
	}

	public BlockPos getPos() {
		return pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean visible) {
		isVisible = visible;
	}
}
