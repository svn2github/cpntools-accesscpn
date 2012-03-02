import java.io.File;

import javax.swing.JFileChooser;

import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.impl.PetriNetImpl;
import org.cpntools.accesscpn.model.importer.DOMParser;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class CloneTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		final JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			load(chooser.getSelectedFile());
		}

	}

	private static void load(final File selectedFile) throws Exception {
		final PetriNetImpl petriNet = (PetriNetImpl) DOMParser.parse(selectedFile.toURI().toURL());
		final TestAdapter adapt = (TestAdapter) TestAdapterFactory.getInstance().adapt(petriNet, TestAdapter.class);
		adapt.setData("Hello World");
		System.out.println(adapt);
		System.out.println(((TestAdapter) TestAdapterFactory.getInstance().adapt(petriNet, TestAdapter.class))
		        .getData());
		System.out.println(TestAdapterFactory.getInstance().adapt(petriNet, TestAdapter.class));
		System.out.println(petriNet.getPage().size());
		final PetriNet copy = EcoreUtil.copy(petriNet);
		System.out.println(((TestAdapter) TestAdapterFactory.getInstance().adapt(copy, TestAdapter.class)).getData());
		System.out.println(TestAdapterFactory.getInstance().adapt(copy, TestAdapter.class));
		System.out.println(copy.getPage().size());
	}
}
