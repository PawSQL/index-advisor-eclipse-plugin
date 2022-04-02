package cn.sqllabs.ia.plugin.properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

public class IAPropertyPage extends PropertyPage {

	private static final int TEXT_FIELD_WIDTH = 40;
	private static QualifiedName queryModeQN = new QualifiedName("", IAPropertiesConstants.QUERY_MODE);
	private static QualifiedName sqlFolderQN = new QualifiedName("", IAPropertiesConstants.SQL_FOLDER);
	private static QualifiedName dbTypeQN = new QualifiedName("", IAPropertiesConstants.DB_TYPE);
	private static QualifiedName dbHostQN = new QualifiedName("", IAPropertiesConstants.DB_HOST);
	private static QualifiedName dbUserQN = new QualifiedName("", IAPropertiesConstants.DB_USER);
	private static QualifiedName dbPwdQN = new QualifiedName("", IAPropertiesConstants.DB_PWD);
	private static QualifiedName dbQN = new QualifiedName("", IAPropertiesConstants.DATABASE);
	private static QualifiedName schemaListQN = new QualifiedName("", IAPropertiesConstants.SCHEMALIST);
	private static QualifiedName iaValidateQN = new QualifiedName("", IAPropertiesConstants.IA_VALIDATE);
	
	
	private Text ownerText;
	Text queryMode;

	/**
	 * Constructor for SamplePropertyPage.
	 */
	public IAPropertyPage() {
		super();
	}


	private void addIAConfig(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		queryMode = createTextProperty(composite, IAPropertiesConstants.QUERY_MODE_LABEL, IAPropertiesConstants.QUERY_MODE);
		Text sqlFolder = createTextProperty(composite, IAPropertiesConstants.SQL_FOLDER_LABEL, IAPropertiesConstants.SQL_FOLDER);
		addSeparator(parent);
		createTextProperty(composite, IAPropertiesConstants.DB_TYPE_LABEL, IAPropertiesConstants.DB_TYPE);
		createTextProperty(composite, IAPropertiesConstants.DB_HOST_LABEL, IAPropertiesConstants.DB_HOST);
		createTextProperty(composite, IAPropertiesConstants.DB_USER_LABEL, IAPropertiesConstants.DB_USER);
		createTextProperty(composite, IAPropertiesConstants.DB_PWD_LABEL, IAPropertiesConstants.DB_PWD);
		createTextProperty(composite, IAPropertiesConstants.DATABASE_LABEL, IAPropertiesConstants.DATABASE);
		createTextProperty(composite, IAPropertiesConstants.SCHEMALIST_LABEL, IAPropertiesConstants.SCHEMALIST);
		addSeparator(parent);
		createTextProperty(composite, IAPropertiesConstants.IA_VALIDATE_LABEL, IAPropertiesConstants.IA_VALIDATE);
	}


	private Text createTextProperty(Composite composite, String label, String qualifier) {
		Label queryModeL = new Label(composite, SWT.NONE);
		queryModeL.setText(label);

		Text text = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		text.setLayoutData(gd);

		String value=null;
		try {
			value = ((IResource) getElement()).getPersistentProperty(
					new QualifiedName(qualifier, qualifier));
		} catch (CoreException e) {
			
		}
		if(value==null) {
			value = "";
		}
		text.setText(value);
		return text;
	}
	
	
	private void addSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}


	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		addIAConfig(composite);
		return composite;
	}

	private Composite createDefaultComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

	protected void performDefaults() {
		super.performDefaults();
	}
	
	public boolean performOk() {
		try {
			((IResource) getElement()).setPersistentProperty(queryModeQN, queryMode.getText());
		} catch (CoreException e) {
			return false;
		}
		return true;
	}

}