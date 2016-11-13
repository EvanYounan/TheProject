
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ImageNode extends FileNode {
	
	private ArrayList<Tag> tags;
	private long ID;
	Random x;
	private ImageNode parent;
	private ImageNode child;
	
	public ImageNode(String path, String name) {
		super(path, name);
		this.tags = new ArrayList<>();
		x = new Random();
		this.ID = x.nextLong(); 
	}
	
	public ImageNode() {}
	
	public void addTag(Tag tag) {
		tags.add(tag);
	}
	
	public void removeTag(Tag tag) {
		tags.remove(tag);
	}
	
	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}
	
	@Override
	public String toString() {
		return "ImageNode [ID=" + ID + ", path=" + this.getPathName() + ", name=" + this.getName() +']';
	}
	
	public ImageNode getParent() {
		return this.parent;
	}
	
	public ImageNode findChild(ImageNode img) {
		if (img.hasChild()) {
			return findChild(img.getChild());
		}
		return img;
	}
	
	public ImageNode getChild() {
		return this.child;
	}
	
	public ImageNode findRoot(ImageNode img) {
		if (img.hasParent()) {
			return findRoot(img.getParent());
		}
		return img;
	}
	
	public boolean hasParent() {
		return this.parent != null;
	}
	
	public boolean hasChild() {
		return this.child != null;
	}
	
	public Tag retrieveTag(Tag tag) {
		for (Tag existingTag : this.tags) {
			if (tag.equals(existingTag)) {
				return existingTag;
			}
		}
		return null;
	}
	
	public ArrayList<Tag> getTags() {
		return this.tags;
	}
	
	public boolean hasTag(Tag tag) {
		for (Tag thisTag : this.tags) {
			if (thisTag.getName() == tag.getName()) {
				return true;
			}
		}
		return false;
	}

	public boolean equals(ImageNode otherImgNode) {
		return this.ID == otherImgNode.ID;
	}
	
	public void setParent(ImageNode temp) {
		this.parent = temp;
	}
	
	public void setChild(ImageNode temp) {
		this.child = temp;
	}

}
