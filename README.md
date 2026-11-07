# Protocol

[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Build](https://github.com/PowerNukkitX/Protocol/actions/workflows/build.yml/badge.svg)](https://github.com/PowerNukkitX/Protocol/actions/workflows/build.yml)
[![Publish](https://github.com/PowerNukkitX/Protocol/actions/workflows/publish.yml/badge.svg)](https://github.com/PowerNukkitX/Protocol/actions/workflows/publish.yml)
[![Discord](https://img.shields.io/discord/944227466912870410?label=discord&color=7289DA&logo=discord)](https://discord.gg/apwd7uauZg)

The Minecraft Bedrock Edition protocol library used by [PowerNukkitX](https://github.com/PowerNukkitX/PowerNukkitX).

It is a fork of [Kaooot/Protocol](https://github.com/Kaooot/Protocol), where packets and data types are aligned with
[Mojang's protocol docs](https://github.com/Mojang/bedrock-protocol-docs). Changes from both upstreams are merged or
ported here regularly, together with fixes PowerNukkitX needs.

## Modules

| Module               | What it contains                                                                                 |
|----------------------|--------------------------------------------------------------------------------------------------|
| `bedrock-codec`      | Packets, data types and one codec per protocol version that reads and writes them                |
| `bedrock-connection` | Netty pipeline on top of RakNet: batching, compression, encryption, login and session handling |
| `common`             | Shared helpers such as `VarInts` and `TypeMap`                                                   |

Most projects only need `bedrock-connection`, which pulls in the other two.

## Supported Versions

The full list is in [VERSIONS.md](VERSIONS.md).

## Adding to Your Project

Builds are published to the PowerNukkitX Maven repository.

<details open>
<summary><b>Gradle (Kotlin DSL)</b></summary>

```kotlin
repositories {
    maven("https://repo.powernukkitx.org/releases")
    // transitive dependencies such as netty-transport-raknet live here
    maven("https://repo.opencollab.dev/maven-snapshots")
}

dependencies {
    implementation("org.powernukkitx.protocol:bedrock-connection:3.0.0.Beta8-SNAPSHOT")
}
```

</details>

<details>
<summary><b>Maven</b></summary>

```xml
<repositories>
    <repository>
        <id>powernukkitx</id>
        <url>https://repo.powernukkitx.org/releases</url>
    </repository>
    <repository>
        <id>opencollab-snapshots</id>
        <url>https://repo.opencollab.dev/maven-snapshots</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>org.powernukkitx.protocol</groupId>
        <artifactId>bedrock-connection</artifactId>
        <version>3.0.0.Beta8-SNAPSHOT</version>
    </dependency>
</dependencies>
```

</details>

## Usage

[EXAMPLES.md](EXAMPLES.md) shows how to start a server and connect as a client.

A server starts every session on a compatibility codec that can read any `LoginPacket`. Once the login arrives, set
the codec that matches the client's protocol version with `session.setCodec(...)` before sending anything else.

Limits for incoming data (string lengths, list sizes, NBT size and so on) come from `EncodingSettings`, and every
codec helper starts with `EncodingSettings.DEFAULT`. A server usually wants the stricter `EncodingSettings.SERVER`, a
client the looser `EncodingSettings.CLIENT`. `setCodec` creates a fresh helper, so apply them after it:

```java
session.setCodec(Bedrock_v2225.CODEC);
session.getPeer().getCodecHelper().setEncodingSettings(EncodingSettings.SERVER);
```

## Building

You need a JDK between 8 and 23 to run Gradle 8.10.2. The sources are compiled for Java 8 through a toolchain, which
Gradle downloads on its own if it is missing.

```shell
./gradlew build
```

To use a local build from another project, run `./gradlew publishToMavenLocal` and add `mavenLocal()` to that
project's repositories.

## Contributing

Pull requests are welcome. Please keep each one to a single change and say which Minecraft version and packet it
touches. When a fix also applies to upstream, consider sending it to
[CloudburstMC/Protocol](https://github.com/CloudburstMC/Protocol) as well.

Wire format questions are best checked against [Mojang's protocol docs](https://github.com/Mojang/bedrock-protocol-docs)
first.

## License

Apache License 2.0, see [LICENSE](LICENSE). Based on the work of the
[CloudburstMC](https://github.com/CloudburstMC) team and its contributors.
