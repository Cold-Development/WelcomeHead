# WelcomeHead
![Static Badge](https://img.shields.io/badge/Version-v1.0-brightgreen?logo=files&logoColor=ffffff&color=799aca)

WelcomeHead is a Minecraft plugin that shows player's head with a welcome message on server join.

![Static Badge](https://img.shields.io/badge/config.yml-brightgreen?logo=files&logoColor=ffffff)

```yaml
prefix: "&8「&#6E00A5W&#760EAEe&#7D1CB7l&#8529C0c&#8D37C9o&#9545D2m&#9C53DBe&#A461E4H&#AC6EEDe&#B37CF6a&#BB8AFFd&8」&7» &f"

# Spaces = clean the chat before giving the text (for player only)
Spaces-Top: 2

# Spaces = clean the chat after giving the text (for player only)
Spaces-Bot: 0

# Time when you want the message to appear (in seconds)
Timer: 3

# ----------------- First time join -----------------------------

# Face the new players! Change the texts made available to you.
Players-FirstJoin:
  enable: true
  # If you need to have the head of the player put "enable: true"
  head-text:
    1: ""
    2: ""
    3: ""
    4: "        &#6E00A5W&#7B17B4e&#882EC3l&#9545D2c&#A15CE1o&#AE73F0m&#BB8AFFe &#A53CFF%player_name%&f!"
    5: "        You can read the rules on our website!"
    6: ""
    7: ""
    8: ""
  # If you don't want the head put "enable: false"
  center: true
  # Set as many lines as you need!
  no-head-text:
    - "Line1"
    - "Line2"
    - "Line3"
    - "Line4"
    - "Line5"

# You need something when the player spawns?
# Put your commands without "/"
Commands-First:
  - ""

# Need a Sound when the player joins for the first time?
# Sounds : https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html
SoundA:
  enable: true
  sound: ENTITY_VILLAGER_CELEBRATE
  # Values: 0.0 -> 1.0
  volume: 1.0
  # Values: 0.0 -> 1.0
  pitch: 1.0

# Send fireworks when they spawn for the first time?
# Set to 0 to disable this
Firework:
  amount: 5

# ----------------- Basic joining (everytime user joins the server) -----------------------------

# Face the returning players! Change the texts made available to you.
Players-Back:
  enable: true
  # If you need to have the head of the player put "enable: true"
  head-text:
    1: ""
    2: ""
    3: ""
    4: "        &#6E00A5W&#7B17B4e&#882EC3l&#9545D2c&#A15CE1o&#AE73F0m&#BB8AFFe &#A53CFF%player_name%&#FFFFFF!"
    5: ""
    6: ""
    7: ""
    8: ""
  # If you don't want the head put "enable: false"
  center: true
  # Set as many lines as you need!
  no-head-text:
    - "----------------------------------"
    - " "
    - "&fWelcome &#A53CFF%player_name%&f!"
    - " "
    - "----------------------------------"

# You need something when the player spawns?
# Put your commands without "/"
Commands-Back: []

# Need a Sound when the player joins again?
# Sounds : https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html
SoundB:
  enable: true
  sound: ENTITY_VILLAGER_CELEBRATE
  # Values: 0.0 -> 1.0
  volume: 1.0
  # Values: 0.0 -> 1.0
  pitch: 1.0
```
# Permissions
- **welcomehead.use:** gives you total access to this plugin

![image](https://github.com/user-attachments/assets/a2e74113-2b93-41bd-a2c2-9fe5923ab2e0)
