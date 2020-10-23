package net.dark_roleplay.travellers_map;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TravellersMap.MODID)
public class TravellersMap {

	public static final String MODID = "travellers_map";
	public static final Logger LOG = LogManager.getLogger(MODID);

	public TravellersMap() {
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> TravellersMapClient::modConstructorInit);
	}

}
