package cn.sqllabs.ia.plugin.handlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

import com.bdaum.overlayPages.FieldEditorOverlayPage;
import com.tigerose.sqllab.foundation.utils.Config;
import com.tigerose.sqllab.foundation.utils.connector.ServerInfo;

import cn.sqllabs.ia.plugin.properties.IAPropertiesConstants;

public class IndexAdvisorHandler extends AbstractHandler {

	Config config;

	public static String getOverlayedPreferenceValue(IPreferenceStore store, IResource resource, String pageId,
			String name) {
		IProject project = resource.getProject();
		String value = null;
		if (useProjectSettings(project, pageId)) {
			value = getProperty(resource, pageId, name);
		}
		if (value != null)
			return value;
		return store.getString(name);
	}

	private static boolean useProjectSettings(IResource resource, String pageId) {
		String use = getProperty(resource, pageId, FieldEditorOverlayPage.USEPROJECTSETTINGS);
		return "true".equals(use);
	}

	private static String getProperty(IResource resource, String pageId, String key) {
		try {
			return resource.getPersistentProperty(new QualifiedName(pageId, key));
		} catch (CoreException e) {
		}
		return null;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IFile file = null;
		IProject project = null;
		IFolder fold = null;
		String resultfolder= null;
		IResource resource = null;
		IFile resultFile = null;
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
				.getSelection();
		if (selection instanceof IStructuredSelection) {
			Object item = ((IStructuredSelection) selection).getFirstElement();
			file = Adapters.adapt(item, IFile.class);
			fold = Adapters.adapt(item, IFolder.class);
			project = Adapters.adapt(item, IProject.class);
			
		}

		if(file==null && fold==null && project==null && resource==null) {
			return null;
		}
		
		if (file != null) {
			project = file.getProject();
			String fileName = file.getName();
			fileName = fileName.substring(0,fileName.lastIndexOf('.'));
			fileName = fileName.concat(".sql");
			IContainer container = file.getParent();
			resultFile = container.getFile(new Path(fileName));
			resultfolder = resultFile.getFullPath().toPortableString();
		}else if(fold !=null){
			//fold
			project = fold.getProject();
			String path = fold.getFullPath().toPortableString();
			resultfolder = path.concat("/index.sql");
			
			resultFile = fold.getFile(new Path("index.sql"));
			
		}else if(project!=null){
			//project
			String path = project.getFullPath().toPortableString();
			resultfolder = path.concat("/index.sql");
			resultFile = project.getFile(new Path("index.sql"));
		}else {
			//resource
			String path = resource.getLocation().toPortableString();
			resultfolder = path.concat("/index.sql");
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource1 = root.findMember(new Path(path));
			if (!resource1.exists() || !(resource1 instanceof IContainer)) {
				throw new ExecutionException("Container \"" + path + "\" does not exist.");
			}
			IContainer container = (IContainer) resource1;
			resultFile = container.getFile(new Path("index.sql"));
		}

		try {
			InputStream stream = new ByteArrayInputStream(null);
			if (resultFile.exists()) {
					resultFile.setContents(stream, true, true, null);

			} else {
				resultFile.create(stream, true, null);
			}
			stream.close();
		} catch (IOException | CoreException e) {
		}
		
		
		if (project == null)
			return null;

		Config config = getConfig(project);
		config.setResultFolder(resultfolder);

		return null;
	}

	private Config getConfig(IProject project) {
		Config config = new Config();
		config.setDdlMode("online");	
		config.setExecMode("workload");

		String pageId = IAPropertiesConstants.PAGE_ID;
		IPreferenceStore ps = PlatformUI.getPreferenceStore();
		String dbType = getOverlayedPreferenceValue(ps, project, pageId, IAPropertiesConstants.DB_TYPE);
		String host = getOverlayedPreferenceValue(ps, project, pageId, IAPropertiesConstants.DB_HOST);
		String user = getOverlayedPreferenceValue(ps, project, pageId, IAPropertiesConstants.DB_USER);
		String pwd = getOverlayedPreferenceValue(ps, project, pageId, IAPropertiesConstants.DB_PWD);
		String database = getOverlayedPreferenceValue(ps, project, pageId, IAPropertiesConstants.DATABASE);
		String schemaList = getOverlayedPreferenceValue(ps, project, pageId, IAPropertiesConstants.SCHEMALIST);
		
		String queryMode = getOverlayedPreferenceValue(ps, project, pageId, IAPropertiesConstants.QUERY_MODE);
//		String sqlFolder = getOverlayedPreferenceValue(ps, project, pageId, IAPropertiesConstants.SQL_FOLDER);
		
		String validate = getOverlayedPreferenceValue(ps, project, pageId, IAPropertiesConstants.VALIDATE_FLAG);
		String dedup = getOverlayedPreferenceValue(ps, project, pageId, IAPropertiesConstants.DEDUP_FLAG);
		ServerInfo server = new ServerInfo(host, user, pwd, database, dbType);
		config.setQueryMode(queryMode);
//		config.setMapperFolder(sqlFolder);
//		config.setQueryFolder(sqlFolder);
		config.setServer(server);
		config.setDatabaseList(Arrays.asList(schemaList.split(",")));
		config.setDedupExistingFlag(dedup);
		config.setValidate(validate);
		return config;
	}
}
