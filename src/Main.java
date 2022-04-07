import classes.GameProgress;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

	private static final String PATH = "/Users/aleksey/projects/files/savegames/";

	public static void main(String[] args) {

		unzipSavedGames(PATH + "saved_games.zip", PATH);

	}

	public static void unzipSavedGames(String archivePath, String unzipPath) {
		try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(archivePath))) {
			ZipEntry entry;
			String name;
			while ((entry = zipInputStream.getNextEntry()) != null) {
				name = entry.getName();
				FileOutputStream fout = new FileOutputStream(PATH + name);
				for (int c = zipInputStream.read(); c != -1; c = zipInputStream.read()) {
					fout.write(c);
				}

				fout.flush();
				zipInputStream.closeEntry();
				fout.close();
				openProgress(PATH + name);
			}
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}


	}

	public static void openProgress(String pathProgress) {
		GameProgress gameProgress = null;
		try (FileInputStream fis = new FileInputStream(pathProgress);
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			gameProgress = (GameProgress) ois.readObject();
			System.out.println(gameProgress);
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
