package org.padrewin.welcomehead.Utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class AnimatedMessage
{
  private ImageMessage[] images;
  private int index = 0;

  public AnimatedMessage(ImageMessage... images) {
this.images = images;
  }

  public AnimatedMessage(File gifFile, int height, char imgChar) throws IOException {
List<BufferedImage> frames = getFrames(gifFile);
this.images = new ImageMessage[frames.size()];
for (int i = 0; i < frames.size(); i++) {
  this.images[i] = new ImageMessage(frames.get(i), height, imgChar);
    }
  }

  public List<BufferedImage> getFrames(File input) {
List<BufferedImage> images = new ArrayList<>();
    try {
  ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
  ImageInputStream in = ImageIO.createImageInputStream(input);
  reader.setInput(in);
  for (int i = 0, count = reader.getNumImages(true); i < count; i++) {
    BufferedImage image = reader.read(i);
    images.add(image);
      }
} catch (IOException ex) {
  ex.printStackTrace();
    }
return images;
  }

  public ImageMessage current() {
return this.images[this.index];
  }

  public ImageMessage next() {
this.index++;
if (this.index >= this.images.length) {
  this.index = 0;
  return this.images[this.index];
    }
return this.images[this.index];
  }


  public ImageMessage previous() {
this.index--;
if (this.index <= 0) {
  this.index = this.images.length - 1;
  return this.images[this.index];
    }
return this.images[this.index];
  }


  public ImageMessage getIndex(int index) {
return this.images[index];
  }
}
