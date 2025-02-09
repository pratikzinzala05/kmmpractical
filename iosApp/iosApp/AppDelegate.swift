import UIKit
import SwiftUI
import ComposeApp

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        let rootComponent = RootComponent(componentContext: DefaultComponentContext())
        window = UIWindow(frame: UIScreen.main.bounds)
        window?.rootViewController = ComposeUIViewController { AppRoot(component: rootComponent) }
        window?.makeKeyAndVisible()
        return true
    }
}
