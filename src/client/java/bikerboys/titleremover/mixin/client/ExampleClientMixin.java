package bikerboys.titleremover.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class ExampleClientMixin {

	@Shadow
	@Nullable
	private Text title;

	@Shadow
	@Nullable
	private Text subtitle;

	@Shadow
	private int titleRemainTicks;

	private boolean messageSent = false; // Flag to track if the message was sent
	private Text lastTitle = null;

	@Inject(at = @At("HEAD"), method = "renderTitleAndSubtitle", cancellable = true)
	private void init(CallbackInfo ci) {
		@Nullable Text titles = this.title;
		@Nullable Text subtitles = this.subtitle;

		// Check if the title has changed
		if (titles != null && !titles.equals(lastTitle)) {
			// If the title has changed, reset messageSent to allow sending the new title
			messageSent = false;
			lastTitle = titles; // Update the lastTitle to the current title
		}

		// Check if title exists and there are still ticks remaining
		if (titles != null && this.titleRemainTicks > 0) {
			MinecraftClient client = MinecraftClient.getInstance();
			if (client.player != null) {
				// Only send the message once
				if (!messageSent) {
					String titleString = titles.getString();
					client.player.sendMessage(titles);
					if (subtitles != null) {
						client.player.sendMessage(subtitles);
					}
				}
				messageSent = true; // Set flag to true to prevent re-sending
			}

		} else {
			// Reset the flag when titleRemainTicks reaches zero or title is null
			messageSent = false;
		}

		ci.cancel(); // Cancel the original rendering
	}
}