/************************************************************************/
/* Access/CPN */
/* Copyright 2010-2011 AIS Group, Eindhoven University of Technology */
/*                                                                      */
/* This library is free software; you can redistribute it and/or */
/* modify it under the terms of the GNU Lesser General Public */
/* License as published by the Free Software Foundation; either */
/* version 2.1 of the License, or (at your option) any later version. */
/*                                                                      */
/* This library is distributed in the hope that it will be useful, */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU */
/* Lesser General Public License for more details. */
/*                                                                      */
/* You should have received a copy of the GNU Lesser General Public */
/* License along with this library; if not, write to the Free Software */
/* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, */
/* MA 02110-1301 USA */
/************************************************************************/
package org.cpntools.accesscpn.model.importer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.cpntools.accesscpn.engine.highlevel.instance.InstanceFactory;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelDataAdapterFactory;
import org.cpntools.accesscpn.model.Arc;
import org.cpntools.accesscpn.model.Code;
import org.cpntools.accesscpn.model.Condition;
import org.cpntools.accesscpn.model.FusionGroup;
import org.cpntools.accesscpn.model.HLAnnotation;
import org.cpntools.accesscpn.model.HLArcType;
import org.cpntools.accesscpn.model.HLDeclaration;
import org.cpntools.accesscpn.model.HLMarking;
import org.cpntools.accesscpn.model.Instance;
import org.cpntools.accesscpn.model.ModelFactory;
import org.cpntools.accesscpn.model.Name;
import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.ParameterAssignment;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.Place;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.Priority;
import org.cpntools.accesscpn.model.RefPlace;
import org.cpntools.accesscpn.model.Sort;
import org.cpntools.accesscpn.model.Time;
import org.cpntools.accesscpn.model.Transition;
import org.cpntools.accesscpn.model.declaration.DeclarationStructure;
import org.cpntools.accesscpn.model.declaration.MLDeclaration;
import org.cpntools.accesscpn.model.monitors.Monitor;
import org.cpntools.accesscpn.model.monitors.MonitorType;
import org.cpntools.accesscpn.model.monitors.MonitorsFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author mw
 */
public class DOMParser {

	private static final String TYPE = "type";
	private static final String NAME = "name";
	private static final String IDREF = "idref";
	private static final String ID = "id";
	/**
	 * 
	 */
	public static final String annotNode = "annot";
	/**
	 * 
	 */
	public static final String arcNode = "arc";
	/**
	 * 
	 */
	public static final String codeNode = "code";
	/**
	 * 
	 */
	public static final String priorityNode = "priority";
	/**
	 * 
	 */
	public static final String condNode = "cond";

	/**
	 * 
	 */
	public static final String cpnetNode = "cpnet";
	private static final ModelFactory factory = ModelFactory.INSTANCE;
	/**
	 * 
	 */
	public static final String fusioninfoNode = "fusioninfo";
	/**
	 * 
	 */
	public static final String fusionNode = "fusion";
	/**
	 * 
	 */
	public static final String generatorNode = "generator";
	/**
	 * 
	 */
	public static final String globboxNode = "globbox";
	/**
	 * 
	 */
	public static final String initmarkNode = "initmark";
	/**
	 * 
	 */
	public static final String pageattrNode = "pageattr";
	/**
	 * 
	 */
	public static final String pageNode = "page";
	/**
	 * 
	 */
	public static final String placeendNode = "placeend";
	/**
	 * 
	 */
	public static final String placeNode = "place";
	/**
	 * 
	 */
	public static final String portNode = "port";
	/**
	 * 
	 */
	public static final String subpageinfoNode = "subpageinfo";
	/**
	 * 
	 */
	public static final String substNode = "subst";
	/**
	 * 
	 */
	public static final String textNode = "text";
	/**
	 * 
	 */
	public static final String timeNode = "time";
	/**
	 * 
	 */
	public static final String transendNode = "transend";
	/**
	 * 
	 */
	public static final String transNode = "trans";
	/**
	 * 
	 */
	public static final String typeNode = TYPE;
	/**
	 * 
	 */
	public static final String workspaceElementsNode = "workspaceElements";
	/**
	 * 
	 */
	public static final String monitorNode = "monitor";
	/**
	 * 
	 */
	public static final String monitorBlockNode = "monitorblock";
	/**
	 * 
	 */
	public static final String instancesNode = "instances";
	/**
	 * 
	 */
	public static final String instanceNode = "instance";
	private static final String declarationNode = "declaration";

	/**
	 * @return a document builder
	 * @throws ParserConfigurationException
	 *             if the parserconfiguration is invalid
	 */
	public static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		final DocumentBuilderFactory docfactory = DocumentBuilderFactory.newInstance();
		docfactory.setValidating(false);

		final DocumentBuilder documentBuilder = docfactory.newDocumentBuilder();
		documentBuilder.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(final String publicId, final String systemId) {
				final String[] entityPathList = new String[] { "http://cpntools.org/DTD",
				        "http://www.daimi.au.dk/~cpntools/bin/DTD" };
				for (final String entityPath : entityPathList) {
					if (systemId.startsWith(entityPath)) {
						InputStream resourceAsStream;
						try {
							final URL resURL = DOMParser.class.getResource("/resources"
							        + systemId.substring(entityPath.length()));
							resourceAsStream = new BufferedInputStream(resURL.openStream());
						} catch (final Exception e) {
							return null;
						}
						return new InputSource(resourceAsStream);
					}
				}
				return null;
			}
		});

		return documentBuilder;
	}

	/**
	 * @param ds
	 *            structure
	 * @param n
	 *            node
	 * @return declaration
	 */
	public static HLDeclaration getHLDeclaration(final DeclarationStructure ds, final Node n) {
		final HLDeclaration decl = DOMParser.factory.createHLDeclaration();
		decl.setStructure(ds);
		decl.setId(ParserUtil.getAttr(n, ID));

		return decl;
	}

	/**
	 * @param inputStream
	 *            input
	 * @param modelName
	 * @return parsed net
	 * @throws NetCheckException
	 *             net is invalid
	 * @throws SAXException
	 *             XML is not correct
	 * @throws IOException
	 *             IO error occurred
	 * @throws ParserConfigurationException
	 *             parser is invalid
	 */
	public static PetriNet parse(final InputStream inputStream, final String modelName) throws NetCheckException,
	        SAXException, IOException, ParserConfigurationException {
		final DOMParser parser = new DOMParser();

		final Document doc = DOMParser.getDocumentBuilder().parse(inputStream);
		final PetriNet petriNet = parser.processingPertiNet(doc);
		final Name name = factory.createName();
		name.setText(modelName);
		petriNet.setName(name);

		return petriNet;
	}

	/**
	 * @param uri
	 *            location of model
	 * @return parsed net
	 * @throws NetCheckException
	 *             net is invalid
	 * @throws SAXException
	 *             XML is not correct
	 * @throws IOException
	 *             IO error occurred
	 * @throws ParserConfigurationException
	 *             parser is invalid
	 */
	public static PetriNet parse(final URL uri) throws NetCheckException, SAXException, IOException,
	        ParserConfigurationException {
		return DOMParser.parse(uri.openStream(), uri.getPath().replaceFirst(".*/", ""));
	}

	private final HashMap<String, PlaceNode> idToNodePlaceMap;

	/*
	 * public void processGenerator(Node n) { }
	 */

	private final HashMap<String, org.cpntools.accesscpn.model.Node> idToTransitionMap;

	private final HashMap<String, FusionGroup> nameToFusionGroupMap;

	private DOMParser() {
		idToNodePlaceMap = new HashMap<String, PlaceNode>();
		nameToFusionGroupMap = new HashMap<String, FusionGroup>();
		idToTransitionMap = new HashMap<String, org.cpntools.accesscpn.model.Node>();
	}

	/**
	 * @param name
	 *            name as string
	 * @param nameObject
	 * @return structured name
	 */
	public Name setName(final String name, final Name nameObject) {
		nameObject.setText(name);
		return nameObject;
	}

	/**
	 * @param n
	 *            node
	 * @return annotation
	 */
	public HLAnnotation processAnnot(final Node n) {
		final HLAnnotation annot = DOMParser.factory.createHLAnnotation();
		annot.setText(ParserUtil.getTextFromChild(n, DOMParser.textNode));

		return annot;
	}

	/**
	 * @param n
	 *            node
	 * @return arc
	 * @throws NetCheckException
	 *             error occurred
	 */
	public Arc processArc(final Node n) throws NetCheckException {
		final Arc arc = DOMParser.factory.createArc();
		arc.setId(ParserUtil.getAttr(n, ID));
		final String orientation = ParserUtil.getAttr(n, "orientation");
		String transIdref = "";
		String placeIdref = "";

		final NodeList nl = n.getChildNodes();
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.transendNode)) {
				transIdref = ParserUtil.getAttr(currentNode, IDREF);
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.placeendNode)) {
				placeIdref = ParserUtil.getAttr(currentNode, IDREF);
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.annotNode)) {
				final HLAnnotation annot = processAnnot(currentNode);
				arc.setHlinscription(annot);
			}
		}

		if (orientation.equalsIgnoreCase("bothdir")) {
			arc.setSource(idToNodePlaceMap.get(placeIdref));
			arc.setTarget(idToTransitionMap.get(transIdref));
			arc.setKind(HLArcType.TEST);
		} else if (orientation.equalsIgnoreCase("ptot")) {
			arc.setSource(idToNodePlaceMap.get(placeIdref));
			arc.setTarget(idToTransitionMap.get(transIdref));
			arc.setKind(HLArcType.NORMAL);
		} else if (orientation.equalsIgnoreCase("ttop")) {
			arc.setSource(idToTransitionMap.get(transIdref));
			arc.setTarget(idToNodePlaceMap.get(placeIdref));
			arc.setKind(HLArcType.NORMAL);
		} else {
			throw new NetStructureException("Unknown arc orientation", n);
		}

		return arc;
	}

	/**
	 * @param n
	 *            node
	 * @param code
	 * @return code
	 */
	public Code processCode(final Node n, final Code code) {
		code.setText(ParserUtil.getTextFromChild(n, DOMParser.textNode));

		return code;
	}

	/**
	 * @param n
	 *            node
	 * @param prio
	 * @return code
	 */
	public Priority processPriority(final Node n, final Priority prio) {
		prio.setText(ParserUtil.getTextFromChild(n, DOMParser.textNode));

		return prio;
	}

	/**
	 * @param n
	 *            node
	 * @param cond
	 * @return guard
	 */
	public Condition processCond(final Node n, final Condition cond) {
		cond.setText(ParserUtil.getTextFromChild(n, DOMParser.textNode));

		return cond;
	}

	/**
	 * @param n
	 *            node
	 * @return net
	 * @throws NetCheckException
	 *             error occurred
	 */
	public PetriNet processCpnet(final Node n) throws NetCheckException {
		final PetriNet petriNet = DOMParser.factory.createPetriNet();

		final NodeList nl = n.getChildNodes();

		// 1st we parse the declarations
		ArrayList<HLDeclaration> labels = new ArrayList<HLDeclaration>();
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.globboxNode)) {
				labels = processGlobbox(currentNode);
			}
		}
		for (final HLDeclaration label : labels) {
			label.setParent(petriNet);
		}

		// 2nd we parse the fusion groups
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.fusionNode)) {
				final FusionGroup fusionGroup = processFusion(currentNode);
				fusionGroup.setPetriNet(petriNet);
			}
		}

		// 3th we parse the pages
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.pageNode)) {
				final Page page = processPage(currentNode);
				page.setPetriNet(petriNet);
			}
		}

		// Fortified we parse the instances. Because the stupid monitors use them. Much hate!
		final ModelData modelData = (ModelData) ModelDataAdapterFactory.getInstance().adapt(petriNet, ModelData.class);
		Map<String, Integer> instances = null;
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.instancesNode)) {
				assert instances == null;
				instances = processInstances(modelData, currentNode);
			}
		}

		// 5th we parse the monitors
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.monitorBlockNode)) {
				processMonitorBlock(modelData, instances, currentNode);
			}
		}

		return petriNet;
	}

	/**
	 * @param modelData
	 * @param n
	 * @return
	 */
	public Map<String, Integer> processInstances(final ModelData modelData, final Node n) {
		final Map<String, Integer> result = new HashMap<String, Integer>();
		final Map<String, Integer> current = new HashMap<String, Integer>();
		final NodeList nl = n.getChildNodes();
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.instanceNode)) {
				processInstance(modelData, result, current, currentNode);
			}
		}
		return result;
	}

	private void processInstance(final ModelData modelData, final Map<String, Integer> result,
	        final Map<String, Integer> current, final Node n) {
		final String id = ParserUtil.getAttr(n, ID);
		String pageId = ParserUtil.getAttr(n, "page");
		if (pageId == null || "".equals(pageId)) {
			final String transition = ParserUtil.getAttr(n, "trans");
			final Instance instance = modelData.getInstance(transition);
			assert instance != null;
			pageId = instance.getSubPageID();
		}
		Integer number = current.get(pageId);
		if (number == null) {
			number = 1;
		} else {
			number = number + 1;
		}
		result.put(id, number);
		current.put(pageId, number);
		final NodeList nl = n.getChildNodes();
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.instanceNode)) {
				processInstance(modelData, result, current, currentNode);
			}
		}
	}

	public void processMonitorBlock(final ModelData modelData, final Map<String, Integer> instances, final Node n) {
		final NodeList nl = n.getChildNodes();
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.monitorBlockNode)) {
				processMonitorBlock(modelData, instances, currentNode);
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.monitorNode)) {
				final Monitor monitor = processMonitor(modelData, instances, currentNode);
				monitor.setPetriNet(modelData.getPetriNet());
			}
		}
	}

	/**
	 * @param modelData
	 * @param instances
	 * @param n
	 * @return
	 */
	public Monitor processMonitor(final ModelData modelData, final Map<String, Integer> instances, final Node n) {
		final Monitor result = MonitorsFactory.INSTANCE.createMonitor();
		result.setId(ParserUtil.getAttr(n, ID));
		final String name = ParserUtil.getAttr(n, NAME);
		result.setName(setName(name == null ? "" : name, factory.createName()));
		try {
			final int type = Integer.parseInt(ParserUtil.getAttr(n, TYPE));
			result.setKind(MonitorType.get(type));
		} catch (final Exception _) {
			// Ignore -- though this is not good, we cannot really recover and hiding the error makes debugging more fun
		}
		result.setDisabled(Boolean.parseBoolean(ParserUtil.getAttr(n, "disabled")));

		final NodeList nl = n.getChildNodes();
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.declarationNode)) {
				final String nme = ParserUtil.getAttr(currentNode, NAME).trim();
				MLDeclaration decl = null;
				try {
					final ArrayList<HLDeclaration> decls = DeclarationParser.processDecls(currentNode);
					if (decls.size() == 1) {
						final HLDeclaration declaration = decls.get(0);
						final DeclarationStructure structure = declaration.getStructure();
						if (structure instanceof MLDeclaration) {
							decl = (MLDeclaration) structure;
						}

					}
				} catch (final NetDeclarationException e) {
					// Ignore; caught by assertion below
				}
				assert decl != null;
				if ("Init function".equals(nme)) {
					result.setInit(decl);
				} else if ("Stop".equals(nme)) {
					result.setStop(decl);
				} else if ("Predicate".equals(nme)) {
					result.setPredicate(decl);
				} else if ("Observer".equals(nme)) {
					result.setObserver(decl);
				} else {
					assert false;
				}
			} else if (ParserUtil.isElementNodeOfType(currentNode, "node")) {
				final String id = ParserUtil.getAttr(currentNode, IDREF);
				final Integer instance = instances.get(ParserUtil.getAttr(currentNode, "pageinstanceidref"));
				result.getNodes().add(InstanceFactory.INSTANCE.createInstance(modelData.getNode(id), instance));
			} else if (ParserUtil.isElementNodeOfType(currentNode, "option")) {
				final String nme = ParserUtil.getAttr(currentNode, NAME);
				final String value = ParserUtil.getAttr(currentNode, "value");
				if ("Timed".equals(nme)) {
					result.setTimed(Boolean.parseBoolean(value));
				} else if ("Logging".equals(nme)) {
					result.setLogging(Boolean.parseBoolean(value));
				} else if ("If is empty".equals(nme)) {
					result.setEmpty(Boolean.parseBoolean(value));
				} else if ("File extension".equals(nme)) {
					result.setExtension(value);
				}
			}
		}

		return result;
	}

	public FusionGroup processFusion(final Node n) {
		final FusionGroup fusionGroup = DOMParser.factory.createFusionGroup();
		fusionGroup.setId(ParserUtil.getAttr(n, ID));
		final String name = ParserUtil.getAttr(n, NAME);
		final Name nme = factory.createName();
		fusionGroup.setName(nme);
		nme.setText("");
		setName(name, fusionGroup.getName());
		nameToFusionGroupMap.put(name, fusionGroup);

		return fusionGroup;
	}

	/**
	 * @param n
	 *            node
	 * @return list of decls
	 * @throws NetDeclarationException
	 *             error occurred
	 */
	public ArrayList<HLDeclaration> processGlobbox(final Node n) throws NetDeclarationException {
		return DeclarationParser.processDecls(n);
	}

	/**
	 * @param n
	 *            node
	 * @return net
	 * @throws NetCheckException
	 *             error occurred
	 */
	public PetriNet processingPertiNet(final Node n) throws NetCheckException {
		PetriNet petriNet = null;

		final NodeList nl = n.getChildNodes();
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.workspaceElementsNode)) {
				petriNet = processWorkspaceElements(currentNode);
			}
		}

		return petriNet;
	}

	/**
	 * @param n
	 *            node
	 * @param initmark
	 * @return initmark
	 */
	public HLMarking processInitmark(final Node n, final HLMarking initmark) {
		initmark.setText(ParserUtil.getTextFromChild(n, DOMParser.textNode));

		return initmark;
	}

	/**
	 * @param n
	 *            node
	 * @return page
	 * @throws NetCheckException
	 *             error occurred
	 */
	public Page processPage(final Node n) throws NetCheckException {
		final Page page = DOMParser.factory.createPage();
		page.setId(ParserUtil.getAttr(n, ID));

		final Name name = factory.createName();
		page.setName(name);
		name.setText("");

		final NodeList nl = n.getChildNodes();

		// 1st we parse the page name, places and transitions
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.pageattrNode)) {
				setName(ParserUtil.getAttr(currentNode, NAME), page.getName());
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.placeNode)) {
				final PlaceNode place = processPlace(currentNode);
				place.setPage(page);
				/*
				 * if (placeNode instanceof RefPlace && ((RefPlace) placeNode).isPort()) {
				 * page.addImportPlace((RefPlace) placeNode); }
				 */
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.transNode)) {
				if (ParserUtil.containsChild(currentNode, DOMParser.substNode)) {
					final Instance instance = processSubst(currentNode);
					instance.setPage(page);
				} else {
					final Transition transition = processTrans(currentNode);
					transition.setPage(page);
				}
			}
		}

		// 2nd we parse the arcs
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.arcNode)) {
				final Arc arc = processArc(currentNode);
				arc.setPage(page);
			}
		}

		return page;
	}

	/**
	 * @param n
	 *            node
	 * @return place
	 */
	public PlaceNode processPlace(final Node n) {
		final Name name = factory.createName();
		final Sort type = factory.createSort();
		final HLMarking initmark = factory.createHLMarking();
		boolean isFusionPlace = false;
		String fusionGroupName = null;
		boolean isPort = false;

		final NodeList nl = n.getChildNodes();
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.textNode)) {
				setName(ParserUtil.getTextFromElement(currentNode), name);
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.typeNode)) {
				processType(currentNode, type);
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.initmarkNode)) {
				processInitmark(currentNode, initmark);
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.fusioninfoNode)) {
				isFusionPlace = true;
				fusionGroupName = ParserUtil.getAttr(currentNode, NAME);
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.portNode)) {
				isPort = true;
			}
		}

		if (isFusionPlace || isPort) {
			final RefPlace refPlace = DOMParser.factory.createRefPlace();
			final String id = ParserUtil.getAttr(n, ID);
			refPlace.setId(id);
			refPlace.setName(name);
			refPlace.setSort(type);
			refPlace.setInitialMarking(initmark);

			if (isPort) {
				refPlace.setRef(null);
			} else {
				refPlace.setRef(nameToFusionGroupMap.get(fusionGroupName));
			}
			idToNodePlaceMap.put(id, refPlace);

			return refPlace;
		} else {
			final Place place = DOMParser.factory.createPlace();
			final String id = ParserUtil.getAttr(n, ID);
			place.setId(id);
			place.setName(name);
			place.setSort(type);
			place.setInitialMarking(initmark);
			idToNodePlaceMap.put(id, place);

			return place;
		}
	}

	/**
	 * @param n
	 *            node
	 * @return instance
	 */
	public Instance processSubst(final Node n) {
		final Instance instance = DOMParser.factory.createInstance();
		final String id = ParserUtil.getAttr(n, ID);
		instance.setId(id);

		final Name name = factory.createName();
		instance.setName(name);
		name.setText("");

		final NodeList nl = n.getChildNodes();
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.textNode)) {
				setName(ParserUtil.getTextFromElement(currentNode), instance.getName());
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.substNode)) {
				instance.setSubPageID(ParserUtil.getAttr(currentNode, "subpage"));

				final String portsock = ParserUtil.getAttr(currentNode, "portsock");
				final String[] pairs = portsock.split("\\(");
				/*
				 * The first element in the pair is the id of RefPlace (port) The second element in the pair is the id
				 * of Place parameter (socket)
				 */
				String[] elements;
				for (int j = 1; j < pairs.length; j++) {
					elements = pairs[j].split("[,]");

					final ParameterAssignment pa = DOMParser.factory.createParameterAssignment();
					pa.setValue(elements[0]);
					pa.setParameter(elements[1].replaceAll("\\)", ""));
					pa.setInstance(instance);
				}
			}
		}
		idToTransitionMap.put(id, instance);

		return instance;
	}

	/**
	 * @param n
	 *            node
	 * @param time
	 * @return time region
	 */
	public Time processTime(final Node n, final Time time) {
		time.setText(ParserUtil.getTextFromChild(n, DOMParser.textNode));
		return time;
	}

	/**
	 * @param n
	 *            node
	 * @return transition
	 */
	public Transition processTrans(final Node n) {
		final Transition transition = DOMParser.factory.createTransition();
		final String id = ParserUtil.getAttr(n, ID);
		transition.setId(id);

		final Name name = factory.createName();
		transition.setName(name);
		name.setText("");
		final Condition cond = DOMParser.factory.createCondition();
		transition.setCondition(cond);
		cond.setText("");
		final Time time = factory.createTime();
		transition.setTime(time);
		time.setText("");
		final Code code = factory.createCode();
		transition.setCode(code);
		code.setText("");
		final Priority priority = factory.createPriority();
		transition.setPriority(priority);
		priority.setText("");

		final NodeList nl = n.getChildNodes();
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.textNode)) {
				setName(ParserUtil.getTextFromElement(currentNode), transition.getName());
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.condNode)) {
				processCond(currentNode, transition.getCondition());
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.timeNode)) {
				processTime(currentNode, transition.getTime());
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.codeNode)) {
				processCode(currentNode, transition.getCode());
			} else if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.priorityNode)) {
				processPriority(currentNode, transition.getPriority());
			}
		}

		idToTransitionMap.put(id, transition);

		return transition;
	}

	/**
	 * @param n
	 *            node
	 * @param type
	 * @return type
	 */
	public Sort processType(final Node n, final Sort type) {
		type.setText(ParserUtil.getTextFromChild(n, DOMParser.textNode));

		return type;
	}

	/**
	 * @param n
	 *            node
	 * @return net
	 * @throws NetCheckException
	 *             error occurred
	 */
	public PetriNet processWorkspaceElements(final Node n) throws NetCheckException {
		PetriNet petriNet = null;

		final NodeList nl = n.getChildNodes();
		for (int i = 0, cnt = nl.getLength(); i < cnt; i++) {
			final Node currentNode = nl.item(i);
			if (ParserUtil.isElementNodeOfType(currentNode, DOMParser.cpnetNode)) {
				petriNet = processCpnet(currentNode);
				// else if (isElementNodeOfType(currentNode, generatorNode))
				// processGenerator(currentNode);
			}
		}

		return petriNet;
	}

}
