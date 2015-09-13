package org.madeinjs;

import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.madein.MadeIn;

@SuppressWarnings("restriction")
public class MadeInJS implements ScriptEngine {

	ScriptEngine engine;

	ScriptEngineManager manager;

	List<String> mavenCoordinates;
	List<String> classesImports;

	MadeIn madein;

	public MadeInJS() {

		madein = new MadeIn();

		/*
		manager = new ScriptEngineManager();
		engine = manager.getEngineByName("Nashorn");
		*/

		mavenCoordinates = new ArrayList<String>();
		classesImports = new ArrayList<String>();

	}


	@Override
	public Object eval(String script, ScriptContext context)
			throws ScriptException {
		return engine.eval(script, context);
	}

	@Override
	public Object eval(Reader reader, ScriptContext context)
			throws ScriptException {
		// TODO Auto-generated method stub
		return engine.eval(reader, context);
	}

	@Override
	public Object eval(String script) throws ScriptException {
		String processed = preprocess(script);
		
		return engine.eval(processed);
	}

	private String preprocess(String script) {

		Scanner scanner = new Scanner(script);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			System.out.println(line);
			if (line.startsWith("@resolve")) {
				mavenCoordinates.add(line.replace("@resolve ", "").replaceAll(" .*", ""));
			}

			if (line.startsWith("@import")) {
				classesImports.add(line.replace("@import ", ""));
			}

		}

		for (String s : mavenCoordinates) {
			try {
				madein.install(s);
			} catch (ArtifactResolutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		URLClassLoader cl;
		cl = URLClassLoader.newInstance(madein.getUrls());

		Thread.currentThread().setContextClassLoader(cl);
		
		manager = new ScriptEngineManager();
		engine = manager.getEngineByName("Nashorn");
		

		for (String s : classesImports) {
			String cmd = "var " + s.replaceAll(".*\\.",  "") + " = Java.type(\"" + s.trim() + "\");";
			System.out.println(cmd);
			try {
				engine.eval(cmd);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}



		scanner.close();

		return script;
	}


	@Override
	public Object eval(Reader reader) throws ScriptException {
		// TODO Auto-generated method stub
		return engine.eval(reader);
	}

	@Override
	public Object eval(String script, Bindings n) throws ScriptException {
		// TODO Auto-generated method stub
		return engine.eval(script, n);
	}

	@Override
	public Object eval(Reader reader, Bindings n) throws ScriptException {
		// TODO Auto-generated method stub
		return engine.eval(reader, n);
	}

	@Override
	public void put(String key, Object value) {
		// TODO Auto-generated method stub
		engine.put(key, value);
	}

	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return engine.get(key);
	}

	@Override
	public Bindings getBindings(int scope) {
		// TODO Auto-generated method stub
		return engine.getBindings(scope);
	}

	@Override
	public void setBindings(Bindings bindings, int scope) {
		engine.setBindings(bindings, scope);

	}

	@Override
	public Bindings createBindings() {
		// TODO Auto-generated method stub
		return engine.createBindings();
	}

	@Override
	public ScriptContext getContext() {
		// TODO Auto-generated method stub
		return engine.getContext();
	}

	@Override
	public void setContext(ScriptContext context) {
		engine.setContext(context);

	}

	@Override
	public ScriptEngineFactory getFactory() {
		return engine.getFactory();
	}
}
