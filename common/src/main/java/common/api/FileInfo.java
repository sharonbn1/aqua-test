package common.api;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import com.fasterxml.jackson.annotation.*;
import common.*;

/**
 * data structure for file attributes
 * This is produced as json/xml from stage1 and received in stage2 as json/xml payload of http request  
 */
public class FileInfo
{
	public String   name;
	@JsonIgnore
	public FileTime creationTime;
	@JsonIgnore
	public FileTime lastModifiedTime;
	@JsonIgnore
	public FileTime lastAccessTime;
	public long     size;
	public String   owner;
	public Set<AclEntryPermission> permissions = new HashSet<>();

	public FileInfo() {}

	public FileInfo(Path file, BasicFileAttributes battrs) throws IOException {
		if (file == null) return;
		name = file.toString();
		creationTime = battrs.creationTime();
		lastAccessTime = battrs.lastAccessTime();
		lastModifiedTime = battrs.lastModifiedTime();
		size = battrs.size();
		FileOwnerAttributeView foattrs = Files.getFileAttributeView(file, FileOwnerAttributeView.class);
		if (foattrs != null) owner = foattrs.getOwner().getName();
		AclFileAttributeView aclattrs = Files.getFileAttributeView(file, AclFileAttributeView.class);
		if (aclattrs != null) aclattrs.getAcl().stream().map(acl -> acl.permissions()).forEach(permissions::addAll);
	}

	@JsonProperty
	public String getCreationTime() { return FileUtils.toString(creationTime); }
	@JsonProperty
	public void   setCreationTime(String time) { this.creationTime = FileUtils.fromString(time); }

	@JsonProperty
	public String getLastModifiedTime() { return FileUtils.toString(lastModifiedTime); }
	@JsonProperty
	public void   setLastModifiedTime(String time) { this.lastModifiedTime = FileUtils.fromString(time); }

	@JsonProperty
	public String getLastAccessTime() { return FileUtils.toString(lastAccessTime); }
	@JsonProperty
	public void   setLastAccessTime(String time) { this.lastAccessTime = FileUtils.fromString(time); }

}
