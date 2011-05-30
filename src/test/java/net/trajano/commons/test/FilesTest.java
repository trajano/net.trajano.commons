package net.trajano.commons.test;

import java.nio.file.Files;
import java.nio.file.Paths;

import net.trajano.commons.filevisitor.RemoveEmptyFoldersVisitor;
import net.trajano.commons.filevisitor.RemoveFoldersVisitor;

import org.junit.Assert;
import org.junit.Test;

public class FilesTest {
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
