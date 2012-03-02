import org.cpntools.accesscpn.engine.highlevel.instance.adapter.PetriNetDataAdapter;

public class TestAdapter extends PetriNetDataAdapter {
	private String data;

	public void setData(final String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}
}
