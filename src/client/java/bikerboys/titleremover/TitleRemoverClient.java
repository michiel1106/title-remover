package bikerboys.titleremover;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

public class TitleRemoverClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		//String title = packet.getTitle().getString();
		//String subtitle = packet.getSubtitle().getString();
		//MinecraftClient.getInstance().player.sendMessage(new LiteralText(title), false);
		//MinecraftClient.getInstance().player.sendMessage(new LiteralText(subtitle), false);
	}
}