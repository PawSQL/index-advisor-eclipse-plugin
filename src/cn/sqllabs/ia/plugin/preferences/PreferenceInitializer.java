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
		store.setDefault(IAPropertiesConstants.P_BOOLEAN, true);
		store.setDefault(IAPropertiesConstants.P_CHOICE, "choice2");
		store.setDefault(IAPropertiesConstants.P_STRING,
				"Default value");
	}

}
