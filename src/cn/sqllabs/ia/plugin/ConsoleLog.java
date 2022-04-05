package cn.sqllabs.ia.plugin;

import java.io.PrintWriter;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class ConsoleLog {
	static MessageConsole console = null;
	static MessageConsoleStream consoleStream = null;
	static IConsoleManager consoleManager = null;
	final static String CONSOLE_NAME = "Index Advisor Console";

	static public void initConsole() {
		consoleManager = ConsolePlugin.getDefault().getConsoleManager();
		IConsole[] consoles = consoleManager.getConsoles();
		if (consoles.length > 0) {
			console = (MessageConsole) consoles[0];
		} else {
			console = new MessageConsole(CONSOLE_NAME, null);
			consoleManager.addConsoles(new IConsole[] { console });
		}
		consoleStream = console.newMessageStream();

		// link to log4j
		PrintWriter pr = new PrintWriter(consoleStream);
		PatternLayout lay = new PatternLayout("%-d{yyyy-MM-dd HH:mm:ss,SSS} [%c{1}]-[%p]%m%n");
		WriterAppender consoleWriter = new WriterAppender(lay, pr);
		Logger.getRootLogger().addAppender(consoleWriter);
	}

	static public void printMessage(String message) {
		if (message != null) {
			if (console == null) {
				initConsole();
			}
			consoleManager.showConsoleView(console);
			consoleStream.print("Index Advisor: " + message);

		}
	}
}
