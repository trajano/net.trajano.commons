package net.trajano.commons.test;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.DatatypeConverter;

import net.trajano.commons.filevisitor.RemoveEmptyFoldersVisitor;
import net.trajano.commons.filevisitor.RemoveFoldersVisitor;
import net.trajano.commons.io.FilesUtil;

import org.junit.Assert;
import org.junit.Test;

public class FilesTest {
	@Test
	public void testDigest() throws Exception {
		final byte[] nullDigest = DatatypeConverter
				.parseHexBinary("d41d8cd98f00b204e9800998ecf8427e");
		final Path file = Paths.get("target/empty");
		final OutputStream os = Files.newOutputStream(file);
		try {
			os.close();
			Assert.assertTrue(FilesUtil.verifyDigest(file, "MD5", nullDigest));
		} finally {
			Files.deleteIfExists(file);
		}

	}

	@Test
	public void testEmptyFolderWalker() throws Exception {
		Files.createDirectories(Paths
				.get("target/some/long/path/with/empty/folders"));
		Files.walkFileTree(Paths.get("target/some"),
				new RemoveEmptyFoldersVisitor());
		Assert.assertFalse(Files.exists(Paths.get("target/some")));
	}

	@Test
	public void testEmptyFolderWalker2() throws Exception {
		Files.createDirectories(Paths
				.get("target/some/long/path/with/empty/folders"));
		Files.createDirectories(Paths
				.get("target/some/long/path2/with/empty/folders"));
		Files.createDirectories(Paths
				.get("target/some/long/path2/with3/empty/folders"));
		Files.walkFileTree(Paths.get("target/some"),
				new RemoveEmptyFoldersVisitor());
		Assert.assertFalse(Files.exists(Paths.get("target/some")));
	}

	@Test
	public void testEmptyFolderWalker3() throws Exception {
		Files.createDirectories(Paths
				.get("target/some/long/path/with/empty/folders"));
		Files.createTempFile(Paths.get("target/some/long/path/"), "", "");
		Files.createDirectories(Paths
				.get("target/some/long/path2/with/empty/folders"));
		Files.createDirectories(Paths
				.get("target/some/long/path2/with3/empty/folders"));
		Files.walkFileTree(Paths.get("target/some"),
				new RemoveEmptyFoldersVisitor());
		Assert.assertTrue(Files.exists(Paths.get("target/some")));
		Files
				.walkFileTree(Paths.get("target/some"),
						new RemoveFoldersVisitor());
		Assert.assertFalse(Files.exists(Paths.get("target/some")));
	}
}
