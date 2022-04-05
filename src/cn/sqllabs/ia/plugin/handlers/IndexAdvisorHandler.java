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
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;

import com.bdaum.overlayPages.FieldEditorOverlayPage;
import com.tigerose.sqllab.foundation.utils.Config;
import com.tigerose.sqllab.foundation.utils.connector.ServerInfo;
import com.tigerose.sqllab.indexadvisor.IndexAdvisor;

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
		IProject project = null;
		String resultPath= null;
		String sourcePath = null;
		IFile resultFile = null;
		
		Object selectItem = null;
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
				.getSelection();
		if (selection instanceof IStructuredSelection) {
			selectItem = ((IStructuredSelection) selection).getFirstElement();
		}

		String fileName = "/ia_index.sql";
		if (selectItem instanceof IFile) {
			IFile file = (IFile)selectItem;
			project = file.getProject();
			sourcePath = file.getRawLocation().toOSString();
			
			fileName = file.getName();
			fileName = fileName.substring(0,fileName.lastIndexOf('.'));
			fileName = fileName.concat("_ia_index.sql");
			
			IContainer container = file.getParent();
			resultFile = container.getFile(new Path(fileName));
			resultPath = resultFile.getRawLocation().toOSString();
		}else if(selectItem instanceof IFolder){
			//fold
			IFolder fold = (IFolder)selectItem;
			project = fold.getProject();
			sourcePath = fold.getRawLocation().toOSString();
			
			String path = fold.getRawLocation().toOSString();
			resultPath = path.concat(fileName);
			//create file in eclipse ui
			resultFile = fold.getFile(new Path(fileName));
		}else if(Adapters.adapt(selectItem, IProject.class)!=null){
			//project
			project = Adapters.adapt(selectItem, IProject.class);
			String path = project.getLocation().toOSString();
			sourcePath = path;
			
			resultPath = path.concat(fileName);
			//create file in eclipse ui
			resultFile = project.getFile(new Path(fileName));
		}else if(selectItem instanceof IPackageFragment){
			IPackageFragment pf = (IPackageFragment)selectItem;
			project = pf.getJavaProject().getProject();
			String path = pf.getResource().getRawLocation().toOSString();
			sourcePath = path;
			
			resultPath = path.concat(fileName);
			pf.getResource().getFullPath().append(fileName).toString();
			//create file in eclipse ui
			resultFile = project.getFile(new Path(pf.getResource().getFullPath().append(fileName).toString()));
		}else if(selectItem instanceof IPackageFragmentRoot){
			IPackageFragmentRoot pfr = (IPackageFragmentRoot)selectItem;
			project = pfr.getJavaProject().getProject();
			String path = pfr.getResource().getRawLocation().toOSString();
			sourcePath = path;
			
			resultPath = path.concat(fileName);
			//create file in eclipse ui
			resultFile = project.getFile(new Path(fileName));
		}
		if (project == null || resultFile ==null)
			return null;
		
		
		try {
			InputStream stream = new ByteArrayInputStream("".getBytes());
			if (resultFile.exists()) {
					resultFile.setContents(stream, true, true, null);
			} else {
				resultFile.create(stream, true, null);
			}
			stream.close();
		} catch (IOException | CoreException e) {
		}

		Config config = getConfig(project);
		config.setResultFolder(resultPath);
		config.setQueryFolder(sourcePath);
		config.setMapperFolder(sourcePath);
		config.setStatsMode("none");
		config.setLandFlag(false);
		
		IndexAdvisor ia = new IndexAdvisor(config);
		ia.loadStatsAndDDL();
		ia.recommendIndexes();
		
		IWorkbenchWindow workbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);

		try {
			IDE.openEditor(workbenchWindow.getActivePage(), resultFile);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
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
		config.getServer().setSchemaList(config.getDatabaseList());
		config.setDedupExistingFlag(dedup);
		config.setValidate(validate);
		return config;
	}
}
