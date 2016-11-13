import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImageNodeHandler implements Serializable {
	private Map<ImageNode, ArrayList<Log>> imgs = new HashMap<ImageNode, ArrayList<Log>>();
	
	/**
	 * Create an ImageNodeHandler object which loads all of the
	 * ImageNodes into a map with no Logs.
	 * @param loadedImages
	 */
	public ImageNodeHandler(ArrayList<ImageNode> loadedImages) {
		for (ImageNode img : loadedImages) {
			imgs.put(img, null);
		}
	}
	
	/** Empty constructor for serialization */
	public ImageNodeHandler() {}
	
	public void addImageNodes(ArrayList<ImageNode> imagesToAdd) {
		for (ImageNode img : imagesToAdd) {
			if (!this.hasImage(img)) {
				imgs.put(img, null);
			}
		}
	}
	
	public boolean hasImage(ImageNode img) {
		for (ImageNode theseImgs : imgs.keySet()) {
			if (img.getPathName().equals(theseImgs.getChild().getPathName())) {
				return true;
			}
		}
		return false;
	}
	
	public void addTag(ImageNode img, Tag tag) {
		
		img.findRoot(img).addTag(tag);
		System.out.println("The tags added to " + img.findRoot(img).getPathName()
				+ ":" + img.findRoot(img).getTags());
		int endIndex = img.getPathName().lastIndexOf(".");
		String extension = "";
		if (endIndex > 0) {
			extension = img.getPathName().substring(endIndex);
		}
		System.out.println("This extension: " + extension);

		
		ImageNode temp = new ImageNode();
		System.out.println(img.getPathName().substring(0, endIndex) +
				"@" + tag.getName() + extension);
		temp.setPathName(img.getPathName().substring(0, endIndex) +
				"@" + tag.getName() + extension);
		temp.setName(img.getPathName().substring(
				img.getPathName().lastIndexOf("/")+1, endIndex) +
				"@" + tag.getName() + extension);
		temp.setID(img.getID());
		img.setChild(temp);
		temp.setParent(img);
		Log forTemp = new Log(true);
		ImageNode rootParent = img.findRoot(img);
		addLog(rootParent, forTemp);
		System.out.println("Getting pathname of the img: " + img.getPathName());
		System.out.println("Getting pathname of temp: " + temp.getPathName());
		
		File oldFile = new File(img.getPathName());
		File newFile = new File(temp.getPathName());
		oldFile.renameTo(newFile);
	}
	
	public ArrayList<ImageNode> getKeys() {
		ArrayList<ImageNode> temp = new ArrayList<ImageNode>();
		for (ImageNode i : imgs.keySet()) {
			temp.add(i);
		}
		return temp;
	}
	
	public void removeTag(ImageNode thisImg, Tag tag) {
		if (thisImg.findRoot(thisImg).hasTag(tag)) {
			thisImg.findRoot(thisImg).removeTag(thisImg.findRoot(thisImg).retrieveTag(tag));
		}
		System.out.println(thisImg.findRoot(thisImg).getTags());
		
		System.out.println("Path for thisImg in the beginning: " + thisImg.getPathName());
		ImageNode temp = new ImageNode();
		System.out.println(thisImg.getPathName().replace("@"
				+ tag.getName(), ""));
		temp.setPathName(thisImg.getPathName().replace("@"
				+ tag.getName(), ""));
		System.out.println(temp.getPathName().substring(
				temp.getPathName().lastIndexOf("/")+1));
		temp.setName(temp.getPathName().substring(
				temp.getPathName().lastIndexOf("/")+1));
		
		temp.setID(thisImg.getID());
		thisImg.setChild(temp);
		temp.setParent(thisImg);
		Log forTemp = new Log(false);
		ImageNode rootParent = thisImg.findRoot(thisImg);
		addLog(rootParent, forTemp);
		System.out.println("Path for thisImg after all the operations: " + thisImg.getPathName());
		
		File imgFile = new File(thisImg.getPathName());
		System.out.println("Does this file exist? " + imgFile.exists());
		System.out.println("imgFile's path: " + imgFile.getAbsolutePath());
		File tempFile = new File(temp.getPathName());
		System.out.println("tempFile's path: " + tempFile.getAbsolutePath());
		imgFile.renameTo(tempFile);
	}
	
	public boolean canRemoveTag(Tag tag, ImageNode thisImg) {
		if (thisImg.getPathName().contains("@" + tag.getName())) {
			System.out.println("Apparently this file reports a truth!");
			return true;
		}
		System.out.println("Could not remove Tags that do not exist for the picture!");
		return false;
	}
	
	public void removeTagFromAll(Tag tag) {
		for (ImageNode thisImg : imgs.keySet()) {
			if (canRemoveTag(tag, thisImg.getChild())) {
				removeTag(thisImg.getChild(), tag);
			}
		}
	}
	
	public ImageNode findImageNode(String path) {
		for (ImageNode tempImage : imgs.keySet()) {
			if (path.equals(tempImage.findChild(tempImage).getPathName())) {
				return tempImage;
			}
		}
		return null;
	}
	
	public void addTagToAll(Tag tag) {
		for (ImageNode thisImg : imgs.keySet()) {
			addTag(thisImg.findChild(thisImg), tag);
		}
	}
	
	public void addLog(ImageNode img, Log log) {
		if (imgs.get(img) == null) {
			imgs.put(img, new ArrayList<Log>());
		}
		imgs.get(img).add(log);
	}
	
	public String toString() {
		String retString = "";
		for (ImageNode thisImg : imgs.keySet()) {
			retString += thisImg.getChild().toString() + "\n";
			if (imgs.get(thisImg) != null) {
				for (Log thisLog : imgs.get(thisImg)) {
					retString += thisLog + "\n";
				}
			}
		}
		return retString;
	}
	
//	public boolean addTag(Tag tag, long ID) {
//	for (Tag exisTag : existing.keySet()) {
//		if (tag.equals(exisTag)) {
//			existing.get(exisTag).add(ID);
//			return true;
//		}
//	}
//	existing.put(tag, new ArrayList<Long>());
//	return existing.get(tag).add(ID);
//}
	
}
