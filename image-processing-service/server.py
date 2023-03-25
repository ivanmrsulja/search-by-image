import base64
from model import *
from fastapi import FastAPI
from processing_service import *
from contextlib import asynccontextmanager


ml_models = {}


@asynccontextmanager
async def lifespan(app: FastAPI):
    # On startup
    ml_models["yolo_v5"] = build_model()
    yield

    # Graceful shutdown
    ml_models.clear()


app = FastAPI(lifespan=lifespan)


@app.post("/preprocess")
async def test(image: RawImage) -> ProcessedImage:
    class_list = load_classes()
    net = ml_models["yolo_v5"]
    image = load_image(image.b64_image)

    input_image = format_yolov5(image)
    outs = detect(input_image, net)

    class_ids, confidences, boxes = wrap_detection(input_image, outs[0])

    classes = []
    for classid in class_ids:
        classes.append(class_list[classid])

    hue, saturation = get_dominant_hue_and_saturation(image)

    return ProcessedImage(classes=classes, hsv_color_space=(hue, saturation))
