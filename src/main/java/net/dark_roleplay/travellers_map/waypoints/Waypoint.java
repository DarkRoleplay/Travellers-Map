package net.dark_roleplay.travellers_map.waypoints;

import net.dark_roleplay.travellers_map.waypoints.categories.Category;
import net.dark_roleplay.travellers_map.waypoints.icons.Icon;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

import java.util.Set;
import java.util.UUID;

public class Waypoint {

	private UUID identifier;
	private String name;
	private Icon icon;
	private Category category;
	private BlockPos pos;
	private int color;
	private Set<Waypoint> children;
	private Waypoint parent;

	private ITextComponent displayComponent;

	public Waypoint(String displayName, Icon icon, int color, Category category, BlockPos pos) {
		this.name = displayName;
		this.icon = icon;
		this.color = color;
		this.category = category;
		this.pos = pos;
	}

	public void setIdentifier(UUID id){
		this.identifier = id;
	}

	public UUID getIdentifier() {
		return identifier;
	}

	public ITextComponent getDisplayComponent() {
		return displayComponent;
	}

	public void setDisplayComponent(ITextComponent displayComponent) {
		this.displayComponent = displayComponent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public BlockPos getPos() {
		return pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public int getDistanceSq(BlockPos target){
		int dX = this.pos.getX() - target.getX();
		int dY = this.pos.getY() - target.getY();
		int dZ = this.pos.getZ() - target.getZ();
		return dX * dX + dY * dY + dZ * dZ;
	}

	public double getDistance(BlockPos target){
		return Math.sqrt(getDistanceSq(target));
	}

	public Waypoint shallowClone(){
		return new Waypoint(this.name, this.icon, this.color, this.category, this.pos);
	}
}
