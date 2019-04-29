package model.prototype;

public class Brand {

	private String brandName;
	private String brandLogoFileName;
	private String brandLogoDirName;
	private String brandLogoFilePath;
	private boolean hasBrandLogoFileName;
	
	public Brand() {
	}
	
	public Brand(String name) {
		setBrandName(name);
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String name) {
		this.brandName = name;
	}

	public String getBrandLogoFileName() {
		return brandLogoFileName;
	}

	public void setBrandLogoFileName(String logoFile) {
		this.brandLogoFileName = logoFile;
	}

	public String getBrandLogoDirName() {
		return brandLogoDirName;
	}

	public void setBrandLogoDirName(String logoPath) {
		this.brandLogoDirName = logoPath;
	}

	public boolean hasBrandLogoFileName() {
		return hasBrandLogoFileName;
	}
	
	public String getBrandLogoFilePath() {
		return brandLogoFilePath;
	}

	public void setBrandLogoFilePath(String brandLogoFilePath) {
		this.brandLogoFilePath = brandLogoFilePath;
	}
}
