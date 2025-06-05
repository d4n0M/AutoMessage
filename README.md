# AutoMessage Minecraft Plugin

**AutoMessage** is a simple Minecraft plugin that allows server administrators to automate broadcast messages at configurable intervals. Useful for announcements, tips, reminders, and more.

## Features

- Automatically sends messages to all players after a set delay.
- Configurable message list and delay interval via `config.yml`.
- Lightweight and easy to use.

## Installation

1. Download the plugin JAR file from the `target/` directory (e.g., `AutoMessage-1.0.jar`).
2. Place the JAR file into your Minecraft server's `plugins` folder.
3. Start or restart your server.
4. Edit the `config.yml` in the `plugins/AutoMessage/` directory to customize messages and delay.

## Configuration

Edit the `config.yml` file:

```yaml
messages:
  - "Welcome to the server!"
  - "Don't forget to join our Discord."
delay: 300  # Time in seconds between messages
```

- `messages`: A list of strings to broadcast.
- `delay`: Time (in seconds) between each message.

## Commands

| Command            | Description                         |
| ------------------ | ----------------------------------- |
| `/setdelay <time>` | Updates the delay between messages. |

## Building from Source

This project uses Maven. To build:

```bash
mvn clean package
```

The built JAR will be located in the `target/` directory.

## License

This project is provided under the MIT License.

---

Feel free to contribute or suggest improvements!
