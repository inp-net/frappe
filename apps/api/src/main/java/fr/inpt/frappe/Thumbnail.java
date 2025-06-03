package fr.inpt.frappe;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inpt.frappe.auth.JwtProvider;
import net.coobird.thumbnailator.Thumbnails;

public class Thumbnail {
	private static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	private static final int THUMBNAIL_WIDTH = 512;
	private static final int THUMBNAIL_HEIGHT = 512;
	private static final String THUMBNAIL_TEMP_PATH = Paths.get(System.getProperty("java.io.tmpdir")).toString()
			+ "/preview/";

	public static void saveThumnail(String basePath, UUID fileID, String mimeType) {
		String filePath = basePath + "/" + fileID.toString();
		String tmpPath = THUMBNAIL_TEMP_PATH + fileID.toString();
		String thumbnailPath = basePath + "/preview/" + fileID.toString();

		// Make sure the temporary preview directory exists
		java.io.File previewTempDir = new java.io.File(THUMBNAIL_TEMP_PATH);
		if (!previewTempDir.exists()) {
			previewTempDir.mkdirs();
		}

		switch (mimeType) {
			case "application/pdf":
				try (PDDocument document = Loader.loadPDF(new File(filePath))) {
					PDFRenderer pdfRenderer = new PDFRenderer(document);

					// Only convert the first page of the PDF
					BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 100);

					ImageIO.write(bim, "jpg", new File(tmpPath));

					Thumbnails.of(tmpPath).size(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT).outputFormat("jpg")
							.toFile(thumbnailPath);

				} catch (IOException e) {
					Thumbnail.logger
							.error("Error while creating thumbnail for file " + fileID.toString() + " \n"
									+ e.toString());
					e.printStackTrace();
				}
				break;

			case "image/jpeg":
			case "image/png":
				try {
					BufferedImage image = ImageIO.read(new File(filePath));

					Thumbnails.of(image)
							.size(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
							.outputFormat("jpg")
							.toFile(thumbnailPath);

				} catch (IOException e) {
					Thumbnail.logger
							.error("Error while creating thumbnail for file " + fileID.toString() + " \n"
									+ e.toString());
					e.printStackTrace();
				}

			default:
				break;
		}

		// Remove the temporary file
		new java.io.File(tmpPath).delete();
	}
}
