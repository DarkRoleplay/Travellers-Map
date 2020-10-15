package net.dark_roleplay.library;

import net.minecraft.client.settings.KeyBinding;

public class ChargedKeybinding extends KeyBinding {
	private boolean cancelledCharge = false;

	public ChargedKeybinding(String p_i45001_1_, int p_i45001_2_, String p_i45001_3_) {
		super(p_i45001_1_, p_i45001_2_, p_i45001_3_);
	}

	public boolean isChargedPress(){
		return this.pressTime >= 10;
	}

	public boolean cancelledChargedPress(){
		if(cancelledCharge){
			cancelledCharge = false;
			return true;
		}
		return false;
	}

	@Override
	public void unpressKey() {
		if(pressTime > 0){
			cancelledCharge = true;
		}
		super.unpressKey();
	}
}
