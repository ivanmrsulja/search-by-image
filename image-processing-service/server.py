from model import RawImage, ProcessedImageData
from handlers import handle_image_processing_request
from fastapi import FastAPI
from processing_service import build_model
from contextlib import asynccontextmanager


ML_MODELS = {}


@asynccontextmanager
async def lifespan(app: FastAPI):
    global ML_MODELS

    # On startup
    ML_MODELS["yolo_v5"] = build_model()
    yield

    # Graceful shutdown
    ML_MODELS.clear()


app = FastAPI(lifespan=lifespan)


@app.post("/preprocess")
async def test(image: RawImage) -> ProcessedImageData:
    global ML_MODELS

    return handle_image_processing_request(image, ML_MODELS["yolo_v5"])
    
