package cn.sqllabs.ia.plugin.properties;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPropertyPage;

import com.bdaum.overlayPages.FieldEditorOverlayPage;

public class IndexAdvisorPropertyPage extends FieldEditorOverlayPage implements IWorkbenchPropertyPage {

	/**
	 * Constructor for SamplePropertyPage.
	 */
	public IndexAdvisorPropertyPage() {
		super(GRID);
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */

	public void createFieldEditors() {
		addSeparator(getFieldEditorParent());
		addField(new RadioGroupFieldEditor(
				IAPropertiesConstants.QUERY_MODE,
			"How to get queries to analyze",
			1,
			new String[][] { { "Extract SQL queries in mapper directory", "mapper" }, {
				"SQL statements in a file directory", "offline" }
		}, getFieldEditorParent()));
		
		addField(new DirectoryFieldEditor(IAPropertiesConstants.SQL_FOLDER, 
				"&Directory for query:", getFieldEditorParent()));

		
		ComboFieldEditor combo = new ComboFieldEditor(
				IAPropertiesConstants.DB_TYPE,
			"Specify database type",
			new String[][] { { "MySQL", "mysql" }, {
				"PostgreSQL", "postgres" },{ "Open Gauss", "opengauss" }
		}, getFieldEditorParent());
		
		addField(combo);
		
		
		addField(
			new StringFieldEditor(IAPropertiesConstants.DB_HOST, "Database host:",20,  getFieldEditorParent()));
		addField(
				new StringFieldEditor(IAPropertiesConstants.DB_USER, "Database user name:",12, getFieldEditorParent()));
		addField(
				new StringFieldEditor(IAPropertiesConstants.DB_PWD, "Database password:", 12, getFieldEditorParent()));
		addField(
				new StringFieldEditor(IAPropertiesConstants.DATABASE, "Default database:", 12, getFieldEditorParent()));
		addField(
				new StringFieldEditor(IAPropertiesConstants.SCHEMALIST, "Schema/database list:",20, getFieldEditorParent()));
		
		addSeparator(getFieldEditorParent());
		addField(
				new BooleanFieldEditor(
					IAPropertiesConstants.IA_VALIDATE,
					"Validate the recommended indice",
					getFieldEditorParent()));
	}

//	private void loadValues() {
//		String queryFolderValue = getProperty(IAPropertiesConstants.SQL_FOLDER);
//		if(queryFolderValue!=null) 
//			queryFolder.setChangeButtonText(queryFolderValue);
//		
////		String dbTypeValue = getProperty(IAPropertiesConstants.DB_TYPE);
//		
//		String dbHostValue = getProperty(IAPropertiesConstants.DB_HOST);
//		if(dbHostValue==null) 
//			dbHost.setStringValue("localhost");
//		else
//			dbHost.setStringValue(dbHostValue);
//		
//		String dbUserValue = getProperty(IAPropertiesConstants.DB_USER);
//		if(dbUserValue==null) 
//			dbUser.setStringValue("root");
//		else
//			dbUser.setStringValue(dbUserValue);
//		
//		String passwordValue = getProperty(IAPropertiesConstants.DB_PWD);
//		if(passwordValue==null) 
//			dbPwd.setStringValue("root");
//		else
//			dbPwd.setStringValue(passwordValue);
//		
//		String dbNameValue = getProperty(IAPropertiesConstants.DATABASE);
//		if(dbNameValue!=null) 
//			database.setStringValue(dbNameValue);
//		
//		String schemaListValue = getProperty(IAPropertiesConstants.SCHEMALIST);
//		if(schemaListValue!=null) 
//			schemaList.setStringValue(schemaListValue);
//		
//		String validateValue = getProperty(IAPropertiesConstants.IA_VALIDATE);
//		if(validateValue==null) validate.getBooleanValue();
//	}
//
//	private void saveValues() {
//		String queryFolderValue = getProperty(IAPropertiesConstants.SQL_FOLDER);
//		if(queryFolderValue!=null) 
//			queryFolder.setChangeButtonText(queryFolderValue);
//		
////		String dbTypeValue = getProperty(IAPropertiesConstants.DB_TYPE);
//		
//		String dbHostValue = getProperty(IAPropertiesConstants.DB_HOST);
//		if(dbHostValue==null) 
//			dbHost.setStringValue("localhost");
//		else
//			dbHost.setStringValue(dbHostValue);
//		
//		String dbUserValue = getProperty(IAPropertiesConstants.DB_USER);
//		if(dbUserValue==null) 
//			dbUser.setStringValue("root");
//		else
//			dbUser.setStringValue(dbUserValue);
//		
//		String passwordValue = getProperty(IAPropertiesConstants.DB_PWD);
//		if(passwordValue==null) 
//			dbPwd.setStringValue("root");
//		else
//			dbPwd.setStringValue(passwordValue);
//		
//		String dbNameValue = getProperty(IAPropertiesConstants.DATABASE);
//		if(dbNameValue!=null) 
//			database.setStringValue(dbNameValue);
//		
//		String schemaListValue = getProperty(IAPropertiesConstants.SCHEMALIST);
//		if(schemaListValue!=null) 
//			schemaList.setStringValue(schemaListValue);
//		
//		String validateValue = getProperty(IAPropertiesConstants.IA_VALIDATE);
//		if(validateValue==null) validate.getBooleanValue();
//	}
//	
//	private String getProperty(String key) {
//		String value;
//		try {
//			value = resource.getPersistentProperty(new QualifiedName("", key));
//		} catch (CoreException e) {
//			e.printStackTrace();
//			return null;
//		}
//		return value;
//	}

//	public boolean performOk() {
//		try {
//
//		} catch (CoreException e) {
//			return false;
//		}
//		return true;
//	}



	private void addSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}

	@Override
	protected String getPageId() {
		return "cn.sqllabs.ia.plugin.defaultPreferences";
	}
}