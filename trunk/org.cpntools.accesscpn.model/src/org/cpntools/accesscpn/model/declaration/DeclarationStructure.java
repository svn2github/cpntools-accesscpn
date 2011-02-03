package org.cpntools.accesscpn.model.declaration;


/**
 * @model abstract="true"
 * @author michael
 */
public interface DeclarationStructure {
	/**
	 * @return string rep of decl structure
	 */
	public String asString();

	/**
	 * @return short string rep of decl structure
	 */
	public String asShortString();
}
