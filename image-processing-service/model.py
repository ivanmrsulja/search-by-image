from pydantic import BaseModel


class RawImage(BaseModel):
    b64_image: str

class ProcessedImage(BaseModel):
    classes: list[str]
    hsv_color_space: tuple[float, float]
