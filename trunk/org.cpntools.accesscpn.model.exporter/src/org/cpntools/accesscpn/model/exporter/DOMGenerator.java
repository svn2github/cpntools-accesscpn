/************************************************************************/
/* Access/CPN                                                           */
/* Copyright 2010-2011 AIS Group, Eindhoven University of Technology    */
/*                                                                      */
/* This library is free software; you can redistribute it and/or        */
/* modify it under the terms of the GNU Lesser General Public           */
/* License as published by the Free Software Foundation; either         */
/* version 2.1 of the License, or (at your option) any later version.   */
/*                                                                      */
/* This library is distributed in the hope that it will be useful,      */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of       */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU    */
/* Lesser General Public License for more details.                      */
/*                                                                      */
/* You should have received a copy of the GNU Lesser General Public     */
/* License along with this library; if not, write to the Free Software  */
/* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,           */
/* MA  02110-1301  USA                                                  */
/************************************************************************/
package org.cpntools.accesscpn.model.exporter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.cpntools.accesscpn.model.Annotation;
import org.cpntools.accesscpn.model.Arc;
import org.cpntools.accesscpn.model.FusionGroup;
import org.cpntools.accesscpn.model.HLArcType;
import org.cpntools.accesscpn.model.HLDeclaration;
import org.cpntools.accesscpn.model.HasName;
import org.cpntools.accesscpn.model.Instance;
import org.cpntools.accesscpn.model.Object;
import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.Place;
import org.cpntools.accesscpn.model.RefPlace;
import org.cpntools.accesscpn.model.RefTrans;
import org.cpntools.accesscpn.model.Transition;
import org.cpntools.accesscpn.model.cpntypes.CPNAlias;
import org.cpntools.accesscpn.model.cpntypes.CPNBool;
import org.cpntools.accesscpn.model.cpntypes.CPNEnum;
import org.cpntools.accesscpn.model.cpntypes.CPNIndex;
import org.cpntools.accesscpn.model.cpntypes.CPNInt;
import org.cpntools.accesscpn.model.cpntypes.CPNList;
import org.cpntools.accesscpn.model.cpntypes.CPNProduct;
import org.cpntools.accesscpn.model.cpntypes.CPNReal;
import org.cpntools.accesscpn.model.cpntypes.CPNRecord;
import org.cpntools.accesscpn.model.cpntypes.CPNString;
import org.cpntools.accesscpn.model.cpntypes.CPNSubset;
import org.cpntools.accesscpn.model.cpntypes.CPNType;
import org.cpntools.accesscpn.model.cpntypes.CPNUnion;
import org.cpntools.accesscpn.model.cpntypes.CPNUnit;
import org.cpntools.accesscpn.model.declaration.DeclarationStructure;
import org.cpntools.accesscpn.model.declaration.GlobalReferenceDeclaration;
import org.cpntools.accesscpn.model.declaration.MLDeclaration;
import org.cpntools.accesscpn.model.declaration.TypeDeclaration;
import org.cpntools.accesscpn.model.declaration.UseDeclaration;
import org.cpntools.accesscpn.model.declaration.VariableDeclaration;
import org.cpntools.accesscpn.model.importer.DOMParser;
import org.cpntools.accesscpn.model.importer.DeclarationParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * @author michael
 */
public class DOMGenerator {
	/**
	 * @param petriNet
	 * @return
	 * @throws ParserConfigurationException
	 * @throws OperationNotSupportedException
	 */
	public static Document export(final PetriNet petriNet) throws ParserConfigurationException,
	        OperationNotSupportedException {
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(false);
		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		final Document document = documentBuilder.newDocument();
		final Element rootTreeNode = document.createElement(DOMParser.workspaceElementsNode);
		document.appendChild(rootTreeNode);
		final Element generatorNode = document.createElement(DOMParser.generatorNode);
		generatorNode.setAttribute("tool", "Access/CPN");
		generatorNode.setAttribute("version", "1.0.0");
		generatorNode.setAttribute("format", "6");
		rootTreeNode.appendChild(generatorNode);
		final Element cpnet = document.createElement(DOMParser.cpnetNode);
		rootTreeNode.appendChild(cpnet);
		exportDeclarations(document, cpnet, petriNet);
		exportPages(document, cpnet, petriNet);
		return document;
	}

	/**
	 * @param petriNet
	 * @param output
	 * @return
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws OperationNotSupportedException
	 */
	public static Document export(final PetriNet petriNet, final OutputStream output) throws TransformerException,
	        ParserConfigurationException, OperationNotSupportedException {
		final Document result = export(petriNet);
		final PrintWriter out = new PrintWriter(output);
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		final Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "iso-8859-1");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		final StreamResult streamResult = new StreamResult(out);

		transformer.transform(new DOMSource(result), streamResult);
		out.flush();
		return result;
	}

	/**
	 * @param petriNet
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws OperationNotSupportedException
	 */
	public static Document export(final PetriNet petriNet, final String fileName) throws FileNotFoundException,
	        TransformerException, ParserConfigurationException, OperationNotSupportedException {
		return export(petriNet, new FileOutputStream(fileName, false));
	}
	
	private static class Position {
		private final int a;
		private final int b;

		Position(int a, int b) {
			this.a = a;
			this.b = b;
		}

		public int getB() {
			return b;
		}

		public int getA() {
			return a;
		}
	}

	private static void exportArc(final Document document, final Element pageNode, final Arc a,
	        final Map<Object, Position> positions) {
		final Element arc = document.createElement(DOMParser.arcNode);
		arc.setAttribute("id", a.getId());
		if (a.getKind() == HLArcType.TEST) {
			arc.setAttribute("orientation", "Both");
		} else if (a.getSource() == a.getTransition()) {
			arc.setAttribute("orientation", "TtoP");
		} else {
			arc.setAttribute("orientation", "PtoT");
		}
		arc.setAttribute("order", "1");
		pageNode.appendChild(arc);
		final Element transend = document.createElement(DOMParser.transendNode);
		transend.setAttribute("idref", a.getTransition().getId());
		arc.appendChild(transend);
		final Element placeend = document.createElement(DOMParser.placeendNode);
		placeend.setAttribute("idref", a.getPlaceNode().getId());
		arc.appendChild(placeend);
		final Position source = positions.get(a.getSource());
		final Position destination = positions.get(a.getTarget());
		exportLabel(document, arc, a.getHlinscription(), DOMParser.annotNode, a.getId() + "a", (int) ((source
		        .getA() + destination.getA()) / 2.0),
		        (int) ((source.getB() + destination.getB()) / 2.0));
	}

	private static void exportCPNType(final Document document, final Element color, final CPNAlias type)
	        throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportCPNType(final Document document, final Element color, final CPNBool type)
	        throws OperationNotSupportedException {
		final Element bool = document.createElement(DeclarationParser.boolNode);
		color.appendChild(bool);
		if (type.getTrueValue() != null || type.getFalseValue() != null) { throw new OperationNotSupportedException(); }
	}

	private static void exportCPNType(final Document document, final Element color, final CPNEnum type)
	        throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportCPNType(final Document document, final Element color, final CPNIndex type)
	        throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportCPNType(final Document document, final Element color, final CPNInt type)
	        throws OperationNotSupportedException {
		final Element intNode = document.createElement(DeclarationParser.intNode);
		color.appendChild(intNode);
		if (type.getHigh() != null || type.getLow() != null) { throw new OperationNotSupportedException(); }
	}

	private static void exportCPNType(final Document document, final Element color, final CPNList type)
	        throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportCPNType(final Document document, final Element color, final CPNProduct type) {
		final Element productNode = document.createElement(DeclarationParser.productNode);
		for (final String t : type.getTypes()) {
			final Element id = document.createElement(DeclarationParser.idNode);
			id.setTextContent(t);
			productNode.appendChild(id);
		}
		color.appendChild(productNode);
	}

	private static void exportCPNType(final Document document, final Element color, final CPNReal type)
	        throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportCPNType(final Document document, final Element color, final CPNRecord type)
	        throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportCPNType(final Document document, final Element color, final CPNString type)
	        throws OperationNotSupportedException {
		final Element string = document.createElement(DeclarationParser.stringNode);
		color.appendChild(string);
		if (type.getLengthHigh() != null || type.getLengthLow() != null || type.getRangeHigh() != null
		        || type.getRangeLow() != null) { throw new OperationNotSupportedException(); }
	}

	private static void exportCPNType(final Document document, final Element color, final CPNSubset type)
	        throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportCPNType(final Document document, final Element color, final CPNType type)
	        throws OperationNotSupportedException {
		if (type.getTimed()) { throw new OperationNotSupportedException(); }
		if (!type.getDeclares().isEmpty()) { throw new OperationNotSupportedException(); }
		if (type instanceof CPNAlias) {
			exportCPNType(document, color, (CPNAlias) type);
		} else if (type instanceof CPNBool) {
			exportCPNType(document, color, (CPNBool) type);
		} else if (type instanceof CPNEnum) {
			exportCPNType(document, color, (CPNEnum) type);
		} else if (type instanceof CPNIndex) {
			exportCPNType(document, color, (CPNIndex) type);
		} else if (type instanceof CPNInt) {
			exportCPNType(document, color, (CPNInt) type);
		} else if (type instanceof CPNList) {
			exportCPNType(document, color, (CPNList) type);
		} else if (type instanceof CPNProduct) {
			exportCPNType(document, color, (CPNProduct) type);
		} else if (type instanceof CPNReal) {
			exportCPNType(document, color, (CPNReal) type);
		} else if (type instanceof CPNRecord) {
			exportCPNType(document, color, (CPNRecord) type);
		} else if (type instanceof CPNString) {
			exportCPNType(document, color, (CPNString) type);
		} else if (type instanceof CPNSubset) {
			exportCPNType(document, color, (CPNSubset) type);
		} else if (type instanceof CPNUnion) {
			exportCPNType(document, color, (CPNUnion) type);
		} else if (type instanceof CPNUnit) {
			exportCPNType(document, color, (CPNUnit) type);
		}

	}

	private static void exportCPNType(final Document document, final Element color, final CPNUnion type)
	        throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportCPNType(final Document document, final Element color, final CPNUnit type)
	        throws OperationNotSupportedException {
		final Element unit = document.createElement(DeclarationParser.unitNode);
		color.appendChild(unit);
		if (type.getId() != null) { throw new OperationNotSupportedException(); }
	}

	private static void exportDeclaration(final Document document, final Element globbox,
	        final DeclarationStructure structure, final HLDeclaration decl) throws OperationNotSupportedException {
		if (structure instanceof GlobalReferenceDeclaration) {
			exportDeclaration(document, globbox, (GlobalReferenceDeclaration) structure, decl);
		} else if (structure instanceof MLDeclaration) {
			exportDeclaration(document, globbox, (MLDeclaration) structure, decl);
		} else if (structure instanceof TypeDeclaration) {
			exportDeclaration(document, globbox, (TypeDeclaration) structure, decl);
		} else if (structure instanceof UseDeclaration) {
			exportDeclaration(document, globbox, (UseDeclaration) structure, decl);
		} else if (structure instanceof VariableDeclaration) {
			exportDeclaration(document, globbox, (VariableDeclaration) structure, decl);
		}
	}

	private static void exportDeclaration(final Document document, final Element globbox,
	        final GlobalReferenceDeclaration structure, final HLDeclaration decl) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportDeclaration(final Document document, final Element globbox, final HLDeclaration decl)
	        throws OperationNotSupportedException {
		exportDeclaration(document, globbox, decl.getStructure(), decl);
	}

	private static void exportDeclaration(final Document document, final Element globbox,
	        final MLDeclaration structure, final HLDeclaration decl) {
		final Element ml = document.createElement(DeclarationParser.mlNode);
		ml.setAttribute("id", decl.getId());
		globbox.appendChild(ml);
		ml.setTextContent(structure.getCode());
	}

	private static void exportDeclaration(final Document document, final Element globbox,
	        final TypeDeclaration structure, final HLDeclaration decl) throws OperationNotSupportedException {
		final Element color = document.createElement(DeclarationParser.colorNode);
		color.setAttribute("id", decl.getId());
		globbox.appendChild(color);
		final Element id = document.createElement(DeclarationParser.idNode);
		id.setTextContent(structure.getTypeName());
		color.appendChild(id);
		exportCPNType(document, color, structure.getSort());
	}

	private static void exportDeclaration(final Document document, final Element globbox,
	        final UseDeclaration structure, final HLDeclaration decl) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportDeclaration(final Document document, final Element globbox,
	        final VariableDeclaration structure, final HLDeclaration decl) {
		final Element var = document.createElement(DeclarationParser.varNode);
		var.setAttribute("id", decl.getId());
		globbox.appendChild(var);
		final Element type = document.createElement(DeclarationParser.typeNode);
		var.appendChild(type);
		final Element id = document.createElement(DeclarationParser.idNode);
		id.setTextContent(structure.getTypeName());
		type.appendChild(id);
		for (final String name : structure.getVariables()) {
			final Element varId = document.createElement(DeclarationParser.idNode);
			varId.setTextContent(name);
			var.appendChild(varId);
		}
	}

	private static void exportDeclarations(final Document document, final Element rootTreeNode, final PetriNet petriNet)
	        throws OperationNotSupportedException {
		final Element globbox = document.createElement(DOMParser.globboxNode);
		rootTreeNode.appendChild(globbox);
		for (final HLDeclaration decl : petriNet.declaration()) {
			exportDeclaration(document, globbox, decl);
		}
	}

	private static void exportLabel(final Document document, final Element place, final Annotation label,
	        final String typenode, final String id, final float x, final float y) {
		final Element node = document.createElement(typenode);
		node.setAttribute("id", id);
		place.appendChild(node);
		final Element posattr = document.createElement("posattr");
		posattr.setAttribute("x", "" + x);
		posattr.setAttribute("y", "" + y);
		node.appendChild(posattr);
		final Element fillattr = document.createElement("fillattr");
		fillattr.setAttribute("colour", "White");
		fillattr.setAttribute("pattern", "Solid");
		fillattr.setAttribute("filled", "false");
		node.appendChild(fillattr);
		final Element lineattr = document.createElement("lineattr");
		lineattr.setAttribute("colour", "Black");
		lineattr.setAttribute("thick", "0");
		lineattr.setAttribute("type", "Solid");
		node.appendChild(lineattr);
		final Element textattr = document.createElement("textattr");
		textattr.setAttribute("colour", "Black");
		textattr.setAttribute("bold", "false");
		node.appendChild(textattr);
		final Element text = document.createElement(DOMParser.textNode);
		text.setTextContent(label.getText());
		node.appendChild(text);
	}

	private static void exportObject(final Document document, final Element pageNode,
	        final org.cpntools.accesscpn.model.Object o, final Map<Object, Position> positions)
	        throws OperationNotSupportedException {
		if (o instanceof Instance) {
			exportObject(document, pageNode, (Instance) o, positions);
		} else if (o instanceof FusionGroup) {
			exportObject(document, pageNode, (FusionGroup) o, positions);
		} else if (o instanceof Place) {
			exportObject(document, pageNode, (Place) o, positions);
		} else if (o instanceof RefPlace) {
			exportObject(document, pageNode, (RefPlace) o, positions);
		} else if (o instanceof Transition) {
			exportObject(document, pageNode, (Transition) o, positions);
		} else if (o instanceof RefTrans) {
			exportObject(document, pageNode, (RefTrans) o, positions);
		} else {
			throw new OperationNotSupportedException();
		}
	}

	private static void exportObject(final Document document, final Element pageNode, final FusionGroup o,
	        final Map<Object, Position> positions) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportObject(final Document document, final Element pageNode, final Instance o,
	        final Map<Object, Position> positions) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportObject(final Document document, final Element pageNode, final Place o,
	        final Map<Object, Position> positions) {
		final Element place = document.createElement(DOMParser.placeNode);
		place.setAttribute("id", o.getId());
		pageNode.appendChild(place);

		// Assign a random/hard-coded size
		final Element ellipse = document.createElement("ellipse");
		ellipse.setAttribute("w", "60.0");
		ellipse.setAttribute("h", "40.0");
		place.appendChild(ellipse);

		// Assign a random position
		final int x = (int) (Math.random() * 1000);
		final int y = (int) (Math.random() * 800);
		positions.put(o, new Position(x, y));
		final Element posattr = document.createElement("posattr");
		posattr.setAttribute("x", "" + x);
		posattr.setAttribute("y", "" + y);
		place.appendChild(posattr);

		exportText(document, place, o);
		exportLabel(document, place, o.getSort(), DOMParser.typeNode, o.getId() + "a", x + 36, y - 24);
		exportLabel(document, place, o.getInitialMarking(), DOMParser.initmarkNode, o.getId() + "b", x + 36, y + 24);
	}

	private static void exportObject(final Document document, final Element pageNode, final RefPlace o,
	        final Map<Object, Position> positions) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportObject(final Document document, final Element pageNode, final RefTrans o,
	        final Map<Object, Position> positions) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	private static void exportObject(final Document document, final Element pageNode, final Transition o,
	        final Map<Object, Position> positions) {
		final Element trans = document.createElement(DOMParser.transNode);
		trans.setAttribute("id", o.getId());
		pageNode.appendChild(trans);

		// Assign a random/hard-coded position
		final Element box = document.createElement("box");
		box.setAttribute("w", "60.0");
		box.setAttribute("h", "40.0");
		trans.appendChild(box);

		// Assign a random position
		final int x = (int) (Math.random() * 1000);
		final int y = (int) (Math.random() * 800);
		positions.put(o, new Position(x, y));
		final Element posattr = document.createElement("posattr");
		posattr.setAttribute("x", "" + x);
		posattr.setAttribute("y", "" + y);
		trans.appendChild(posattr);

		exportText(document, trans, o);
		exportLabel(document, trans, o.getCondition(), DOMParser.condNode, o.getId() + "a", x - 36, y - 24);
		exportLabel(document, trans, o.getTime(), DOMParser.timeNode, o.getId() + "b", x + 36, y + 24);
		exportLabel(document, trans, o.getCode(), DOMParser.codeNode, o.getId() + "c", x + 36, y);
	}

	private static void exportPage(final Document document, final Element rootTreeNode, final Page p)
	        throws OperationNotSupportedException {
		final Element pageNode = document.createElement(DOMParser.pageNode);
		pageNode.setAttribute("id", p.getId());
		rootTreeNode.appendChild(pageNode);
		final Element pageAttr = document.createElement(DOMParser.pageattrNode);
		pageAttr.setAttribute("name", p.getName().getText());
		pageNode.appendChild(pageAttr);
		final Map<org.cpntools.accesscpn.model.Object, Position> positions = new HashMap<org.cpntools.accesscpn.model.Object, Position>();
		for (final org.cpntools.accesscpn.model.Object o : p.getObject()) {
			exportObject(document, pageNode, o, positions);
		}
		for (final Arc a : p.getArc()) {
			exportArc(document, pageNode, a, positions);
		}
	}

	private static void exportPages(final Document document, final Element rootTreeNode, final PetriNet petriNet)
	        throws OperationNotSupportedException {
		for (final Page p : petriNet.getPage()) {
			exportPage(document, rootTreeNode, p);
		}
	}

	private static void exportText(final Document document, final Element node, final HasName label) {
		final Element text = document.createElement(DOMParser.textNode);
		text.setTextContent(label.getName().getText());
		node.appendChild(text);
	}
}
