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
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.bdaum.overlayPages.FieldEditorOverlayPage;

import cn.sqllabs.ia.plugin.Activator;

public class IndexAdvisorPropertyPage extends FieldEditorOverlayPage implements IWorkbenchPreferencePage  {
	/**
	 * Constructor for SamplePropertyPage.
	 */
	public IndexAdvisorPropertyPage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("A configration page for index advisor");
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
			"Query type",
			1,
			new String[][] { { "Mapper files", "mapper" }, {
				"SQL files", "offline" }
		}, getFieldEditorParent()));
//		
//		addField(new DirectoryFieldEditor(IAPropertiesConstants.SQL_FOLDER, 
//				"&Directory for query:", getFieldEditorParent()));
		ComboFieldEditor combo = new ComboFieldEditor(
				IAPropertiesConstants.DB_TYPE,
			"Database type",
			new String[][] { { "MySQL", "mysql" }, {
				"PostgreSQL", "postgres" },{ "Open Gauss", "opengauss" }
		}, getFieldEditorParent());
		
		addField(combo);
		
		addField(
			new StringFieldEditor(IAPropertiesConstants.DB_HOST, "Database host&port:",20,  getFieldEditorParent()));
		addField(
				new StringFieldEditor(IAPropertiesConstants.DB_USER, "Database user:",12, getFieldEditorParent()));
		addField(
				new StringFieldEditor(IAPropertiesConstants.DB_PWD, "Database password:", 12, getFieldEditorParent()));
		addField(
				new StringFieldEditor(IAPropertiesConstants.DATABASE, "Default database:", 12, getFieldEditorParent()));
		addField(
				new StringFieldEditor(IAPropertiesConstants.SCHEMALIST, "Schema/database list:",20, getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(
					IAPropertiesConstants.VALIDATE_FLAG,
					"&Validate recommended indice",
					getFieldEditorParent()));
		addField(
				new BooleanFieldEditor(
					IAPropertiesConstants.DEDUP_FLAG,
					"&Deduplicate existing indice",
					getFieldEditorParent()));
	}
	private void addSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}

	private void addNone(Composite parent) {
		Label separator = new Label(parent, SWT.SHADOW_NONE);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}
	
	@Override
	protected String getPageId() {
		return IAPropertiesConstants.PAGE_ID;
	}

	@Override
	public void init(IWorkbench workbench) {
		
	}
}