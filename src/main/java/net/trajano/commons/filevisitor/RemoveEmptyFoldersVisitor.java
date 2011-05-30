package net.trajano.commons.filevisitor;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;

public class RemoveEmptyFoldersVisitor extends SimpleFileVisitor<Path> {
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc)
			throws IOException {
		DirectoryStream<Path> ds = Files.newDirectoryStream(dir);
		boolean hasFiles = ds.iterator().hasNext();
		ds.close();
		if (!hasFiles) {
			Files.delete(dir);
		}
		return FileVisitResult.CONTINUE;
	}
}
