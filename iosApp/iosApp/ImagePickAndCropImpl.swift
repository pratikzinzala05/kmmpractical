import UIKit
import PhotosUI
import TOCropViewController
import ComposeApp

class IosImagePickAndCrop: NSObject, ImagePickAndCrop, UIImagePickerControllerDelegate, UINavigationControllerDelegate, TOCropViewControllerDelegate {

    private var viewController: UIViewController
    private var completion: ((KotlinByteArray?) -> Void)?
    private var selectedImage: UIImage? // Store the picked image
    private var cropCompletion: ((KotlinByteArray?) -> Void)? // Callback for cropped image

    init(viewController: UIViewController) {
        self.viewController = viewController
    }

    // Step 1: Pick Image (but don't crop immediately)
    func pickImage(onResult: @escaping (KotlinByteArray?) -> Void) {
        self.completion = onResult // Store callback
        let picker = UIImagePickerController()
        picker.delegate = self
        picker.sourceType = .photoLibrary
        viewController.present(picker, animated: true)
    }

    // UIImagePickerController delegate method when an image is selected
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        picker.dismiss(animated: true) {
            guard let image = info[.originalImage] as? UIImage else {
                self.completion?(nil)
                return
            }
            self.selectedImage = image // Store the selected image

            // Convert to ByteArray and return
            if let imageData = image.jpegData(compressionQuality: 0.8) {
                self.completion?(imageData.toKotlinByteArray())
            } else {
                self.completion?(nil)
            }
        }
    }

    // UIImagePickerController delegate method when the picker is cancelled
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true)
        self.completion?(nil)
    }

    // Step 2: Crop the picked image when user presses crop button
    func cropImage(onResult: @escaping (KotlinByteArray?) -> Void) {
        guard let image = selectedImage else {
            onResult(nil) // No image picked
            return
        }
        self.cropCompletion = onResult // Store crop callback

        let cropViewController = TOCropViewController(image: image)
        cropViewController.delegate = self
        viewController.present(cropViewController, animated: true)
    }

    // TOCropViewController delegate method after cropping
    func cropViewController(_ cropViewController: TOCropViewController, didCropTo image: UIImage, with cropRect: CGRect, angle: Int) {
        cropViewController.dismiss(animated: true) {
            if let imageData = image.jpegData(compressionQuality: 0.8) {
                self.cropCompletion?(imageData.toKotlinByteArray())
            } else {
                self.cropCompletion?(nil)
            }
        }
    }

    // TOCropViewController delegate method when crop is cancelled
    func cropViewController(_ cropViewController: TOCropViewController, didFinishCancelled cancelled: Bool) {
        cropViewController.dismiss(animated: true)
        self.cropCompletion?(nil)
    }
}

// Extension to convert Data to KotlinByteArray
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
