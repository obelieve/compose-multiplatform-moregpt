### 这是一个针对 Android、iOS、Desktop的 Kotlin Compose 多平台的实验性Demo
- [Compose 多平台](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform)
- 实现Android、ios、Desktop共享Compose UI组件
- 支持多平台数据库库 [sqldelight](https://github.com/cashapp/sqldelight)
- 支持多平台key-values存储库 [multiplatform-settings](https://github.com/russhwolf/multiplatform-settings)
- 支持多平台Navigation组件 [PreCompose](https://github.com/Tlaster/PreCompose)
- 支持多平台mvvm组件 [moko-mvvm](https://github.com/icerockdev/moko-mvvm)
- 使用openai的GPT API提供gpt生成式文本
`目前支持android 平台，需要在'com.zxy.core.cache.GlobalCacheRepository'中配置token key。`
### 预览
##### Android
<div>
<img src="https://github.com/obelieve/compose-multiplatform-moregpt/blob/master/screenshot/android-1.jpeg" width = "30%" />
<img src="https://github.com/obelieve/compose-multiplatform-moregpt/blob/master/screenshot/android-3.gif" width = "30%" />
</div>

##### IOS

<img src="https://github.com/obelieve/compose-multiplatform-moregpt/blob/master/screenshot/ios-1.png" width = "30%" />

##### Desktop

<img src="https://github.com/obelieve/compose-multiplatform-moregpt/blob/master/screenshot/desktop-1.png" width = "80%" />

### 开发环境
- Android Studio Hedgehog | 2023.1.1 RC 1
- Runtime version: 17.0.7+0-17.0.7b1000.6-10550314 aarch64
### 项目目录
* `/composeApp` 用于在您的 Compose 多平台应用程序之间共享的代码。
  它包含几个子文件夹：
  - `commonMain` 用于所有目标通用的代码。
  - 其他文件夹用于存放 Kotlin 代码，这些代码将仅针对文件夹名称中指定的平台进行编译。
    例如，如果您想将 Apple 的 CoreCrypto 用于 Kotlin 应用程序的 iOS 部分，
    `iosMain` 将是此类调用的正确文件夹。
  - `androidMain` 存放Kotlin应用程序的android部分。
* `/iosApp` 包含 iOS 应用程序。即使您与 Compose Multiplatform 共享 UI，
  您的 iOS 应用程序需要此入口点。这也是您应该为项目添加 SwiftUI 代码的地方。