from pydantic import BaseModel


class RawImage(BaseModel):
    b64_image: str

class ProcessedImageData(BaseModel):
    classes: list[str]
    hsv_color_space: tuple[float, float]
