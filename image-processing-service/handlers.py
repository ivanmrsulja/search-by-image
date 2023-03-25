from model import RawImage, ProcessedImageData
from processing_service import load_classes, format_yolov5, load_image, detect, wrap_detection, get_dominant_hue_and_saturation

def handle_image_processing_request(image: RawImage, ml_model) -> ProcessedImageData:
    class_list = load_classes()
    image = load_image(image.b64_image)

    input_image = format_yolov5(image)
    outs = detect(input_image, ml_model)

    class_ids, _, _ = wrap_detection(input_image, outs[0])

    classes = {}
    for classid in class_ids:
        try:
            if classes[class_list[classid]] > 1:
                classes[class_list[classid] + "_multiple"] = 1
            classes[class_list[classid]] += 1
        except:
            classes[class_list[classid]] = 1
            continue

    hue, saturation = get_dominant_hue_and_saturation(image)
    return ProcessedImageData(classes=list(classes.keys()), hsv_color_space=(hue, saturation))
