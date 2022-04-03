package cn.sqllabs.ia.plugin.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import cn.sqllabs.ia.plugin.Activator;
import cn.sqllabs.ia.plugin.properties.IAPropertiesConstants;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(IAPropertiesConstants.QUERY_MODE, "mapper");
		store.setDefault(IAPropertiesConstants.DB_HOST, "localhost");
		store.setDefault(IAPropertiesConstants.DB_USER, "root");
		store.setDefault(IAPropertiesConstants.DB_PWD, "root");
		store.setDefault(IAPropertiesConstants.DB_TYPE, "mysql");
		store.setDefault(IAPropertiesConstants.VALIDATE_FLAG, true);
		store.setDefault(IAPropertiesConstants.DEDUP_FLAG, true);
		
	}

}
