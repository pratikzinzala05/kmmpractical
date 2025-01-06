import SwiftUI
import ComposeApp


@main
struct iOSApp: App {

    init(){
    
        InitKoinIosKt.doInitKoinIos(appComponent: IosApplicationComponent(deviceConfig:IosDeviceConfig()))

    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
