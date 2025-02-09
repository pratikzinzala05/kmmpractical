import UIKit
import PhotosUI
import TOCropViewController
import ComposeApp

class IosImagePickAndCrop: NSObject, ImagePickAndCrop, UIImagePickerControllerDelegate, UINavigationControllerDelegate, TOCropViewControllerDelegate {

    private var viewController: UIViewController
    private var completion: ((KotlinByteArray?) -> Void)?

    init(viewController: UIViewController) {
        self.viewController = viewController
    }

    func pickAndCropImage(onResult: @escaping (KotlinByteArray?) -> Void) {
        self.completion = onResult

        let picker = UIImagePickerController()
        picker.delegate = self
        picker.sourceType = .photoLibrary
        viewController.present(picker, animated: true)
    }

    // UIImagePickerController delegate method when an image is selected
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        picker.dismiss(animated: true) {
            // Ensure that the selected media is an image
            guard let image = info[.originalImage] as? UIImage else {
                self.completion?(nil)
                return
            }
            // Proceed to cropping
            self.presentCropViewController(image: image)
        }
    }

    // UIImagePickerController delegate method when the picker is cancelled
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true)
        self.completion?(nil)
    }

    // Present the cropping interface using TOCropViewController
    private func presentCropViewController(image: UIImage) {
        let cropViewController = TOCropViewController(image: image)
        cropViewController.delegate = self
        viewController.present(cropViewController, animated: true)
    }

    // TOCropViewController delegate method after cropping
    func cropViewController(_ cropViewController: TOCropViewController, didCropTo image: UIImage, with cropRect: CGRect, angle: Int) {
        cropViewController.dismiss(animated: true) {
            // Convert the cropped image to a KotlinByteArray and return it
            if let imageData = image.jpegData(compressionQuality: 0.8) {
                self.completion?(imageData.toKotlinByteArray())
            } else {
                self.completion?(nil)
            }
        }
    }

    // TOCropViewController delegate method when the crop is cancelled
    func cropViewController(_ cropViewController: TOCropViewController, didFinishCancelled cancelled: Bool) {
        cropViewController.dismiss(animated: true)
        self.completion?(nil)
    }
}

extension Data {
    func toKotlinByteArray() -> KotlinByteArray {
        let byteArray = KotlinByteArray(size: Int32(self.count))
        self.withUnsafeBytes { buffer in
            guard let pointer = buffer.baseAddress?.assumingMemoryBound(to: UInt8.self) else { return }
            for i in 0..<self.count {
                byteArray.set(index: Int32(i), value: Int8(bitPattern: pointer[i]))
            }
        }
        return byteArray
    }
}
