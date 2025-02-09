import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> UIViewController {
        let viewController = MainViewControllerKt.MainViewController()

        let rootViewController = MainViewControllerKt.rootViewController(root: MainViewControllerKt.defaultMainComponent, backDispatcher: MainViewControllerKt.back)

        
        // Initialize Koin with the required components
        InitKoinIosKt.doInitKoinIos(appComponent: IosApplicationComponent(
            deviceConfig: IosDeviceConfig(),
            imagePickAndCrop: IosImagePickAndCrop(viewController: rootViewController)
        ))
        return rootViewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard).edgesIgnoringSafeArea(.all) 
                   
    }
}



