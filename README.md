[![Banner](https://i.imgur.com/xypsvWn.png)](https://terium.cloud)
[![Version](https://img.shields.io/badge/Terium%20Version-OXYGEN--v1.4-blue?style=for-the-badge&logo=appveyor)](https://terium.cloud/download/terium-1.5-OXYGEN.zip) [![Discord](https://img.shields.io/badge/Discord%20Server-JOIN%20NOW-%237289da?style=for-the-badge&logo=discord)](https://discord.com/invite/5VrY59sffQ) [![Twitter](https://img.shields.io/twitter/follow/teriumcloud?color=%231DA1F2&logo=twitter&style=for-the-badge)](https://twitter.com/@teriumcloud)

## Terium

#### Terium is a simple usable cloud-system for minecraft networks with a few special features

## Why Terium?

#### Terium has a lot of useful features like: Multi-root, simple api, a module system where you can write your own modules and many more. Our template system is very advanced, and multiple templates can be used for one service group.
#### You can install Terium in a few seconds with no complications and errors.

## All features

- Dynamic and static services
- Template-System
- Basic and simple api
- Module system
- Multi-root (Master/Wrapper)
- [Velocity](https://velocitypowered.com) support
- [Paper](https://papermc.io) and [Purpur](https://purpurmc.org/) support

## Links

- [Latest Release](https://github.com/TeriumCloud/Terium/releases)
- [Documentation](https://github.com/TeriumCloud/Terium/wiki)
- [GitHub repo](https://github.com/TeriumCloud/Terium)

## Installation (MASTER/CLUSTER)

- Download the .zip folder and move the terium-OXYGEN.jar and the start-master.sh file to your folder
- Give the start-master.sh file '777' rights (Only on linux systems)
- Execute start-master.sh
- Everything will be downloaded and created automatically.
- Now you can create groups, start services and more. Have fun!

## Installation (WRAPPER/NODE)

- Download the .zip folder and move the terium-OXYGEN.jar and the start-node.sh file to your folder
- Give the start-node.sh file '777' rights (Only on linux systems)
- Execute start-node.sh
- Everything will be downloaded and created automatically.
- Add all informations about your master in the config.json under "master".
- Now you need to go into your master console and type: "node add CURRENT-NODE-NAME YOUR-IP YOUR-PORT"
- After that you execute the start-node.sh again and enjoy your mulit-root action!

## Supported Java Versions

- Java 23 and higher - Supported

## Supported Minecraft Versions

- [Paper](https://papermc.io/software/paper) 1.12.2 - 1.21.8
- [Purpur](https://purpurmc.org/download/purpur) 1.16.5 - 1.21.8
- [Velocity](https://papermc.io/software/velocity)
- [Folia](https://papermc.io/software/folia) 1.21.8

## Update Endpoints

In order to receive updates from the Terium Cloud, you must ensure that on your server, that at least one of following endpoints are accessible:

| Service              | Endpoint                  | Port |
|----------------------|---------------------------|------|
| Releaseteam          | cdn.releaseteam.de        | 443  |
| ReleaseNetworks      | cdn.releasenetworks.cloud | 443  |,

Custom Endpoints can be defined in the config.json under "updateEndpoints".