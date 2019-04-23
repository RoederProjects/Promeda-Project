/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.prototype;

import java.util.List;

/**
 *
 * @author Win10 Pro x64
 */
public class ImageSize {

	private int sideLength;
	private int width;
	private int height;
	private String name;
	private float scalingFactor;
	private boolean squerePadding;

	public ImageSize(int sideLength, String name) {
		this.sideLength = sideLength;
		this.name = name;
	}
	
	public ImageSize(int width, int height, String name) {
		this.width = width;
		this.height = height;
		this.name = name;
	}
	
	public ImageSize(List<Object> params) {
		this.width = (Integer) params.get(0);
		this.height = (Integer) params.get(1);
		this.name = params.get(2).toString();
	}
	
	public ImageSize(String[] params) {
		this.setWidth(Integer.parseInt(params[0]));
		this.setHeight(Integer.parseInt(params[1]));
		this.setName(params[2]);
		if(params.length >= 4) {
			try
		    {
				this.setScalingFactor(Float.valueOf(params[3].trim()).floatValue());
		    }
		    catch (NumberFormatException nfe)
		    {
		      nfe.printStackTrace();
		      this.setSquerePadding(params[3].equalsIgnoreCase("PAD_TO_SQUERE") ? true : false);
		    }
			
		}
	}
	
	public int getSideLength() {
		return sideLength;
	}

	public void setSideLength(int width) {
		this.sideLength = width;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScalingFactor() {
		return scalingFactor;
	}

	public void setScalingFactor(float scalingFactor) {
		this.scalingFactor = scalingFactor;
	}

	public boolean isSquerePadding() {
		return squerePadding;
	}

	public void setSquerePadding(boolean squerePadding) {
		this.squerePadding = squerePadding;
	}
	
}
