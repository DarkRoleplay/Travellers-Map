package net.dark_roleplay.library;

import net.minecraft.client.settings.KeyBinding;

public class ChargedKeybinding extends KeyBinding {
	private boolean wasPressed = false;
	private boolean cancelledCharge = false;

	public ChargedKeybinding(String p_i45001_1_, int p_i45001_2_, String p_i45001_3_) {
		super(p_i45001_1_, p_i45001_2_, p_i45001_3_);
	}

	public int getPressTime(){
		return this.pressTime;
	}

	public boolean isChargedPress(){
		if(this.pressTime >= 10){
			this.pressTime -= 10;
			this.wasPressed = false;
			return true;
		}
		return false;
	}

	public boolean cancelledChargedPress(){
		if(cancelledCharge){
			cancelledCharge = false;
			return true;
		}
		return false;
	}

	@Override
	public void setPressed(boolean value) {
		if(value){
			wasPressed = true;
			cancelledCharge = false;
		}else if(wasPressed){
			wasPressed = false;
			cancelledCharge = true;
		}
		super.setPressed(value);
	}

}
