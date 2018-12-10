package ui;

import java.util.Enumeration;
import java.util.Properties;

public class SysInfoController {

	private SysInfoView view;
	
	public SysInfoController() {
		initView();
	}
	
	private void initView() {
		view = new SysInfoView(this);
		view.setVisible(true);
		String keyValues = "SYSTEM PROPERTIES:" + "\n\n";
		Properties p = System.getProperties();
		Enumeration keys = p.keys();
		while (keys.hasMoreElements()) {
		    String key = (String)keys.nextElement();
		    String value = (String)p.get(key);
		    keyValues = keyValues + key + ": " + value + "\n";
		}
		view.txtpnSystemProperties.setText(keyValues);
	}
}
