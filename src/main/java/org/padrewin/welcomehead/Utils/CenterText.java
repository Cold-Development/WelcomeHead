package org.padrewin.welcomehead.Utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class CenterText
{
  private static final int CENTER_PX = 154;

  public static void sendCenteredMessage(Player player, String message) {
if (message == null || message.equals("")) player.sendMessage("");
message = PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", message));

int messagePxSize = 0;
boolean previousCode = false;
boolean isBold = false;

for (char c : message.toCharArray()) {
  if (c == '§')
  { previousCode = true; }

  else if (previousCode == true)
  { previousCode = false;
    if (c == 'l' || c == 'L') {
      isBold = true;
        } else {
      isBold = false;
        }  }
  else { InfoEnum dFI = InfoEnum.getDefaultFontInfo(c);
    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
    messagePxSize++; }

    }

int halvedMessageSize = messagePxSize / 2;
int toCompensate = 154 - halvedMessageSize;
int spaceLength = InfoEnum.SPACE.getLength() + 1;
int compensated = 0;
StringBuilder sb = new StringBuilder();
while (compensated < toCompensate) {
  sb.append(" ");
  compensated += spaceLength;
    }
player.sendMessage(sb.toString() + message);
  }
}
