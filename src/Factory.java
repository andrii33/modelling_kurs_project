import process.Dispatcher;
import qusystem.IModelFactory;

public class Factory implements IModelFactory {
	private VisualFrame gui;
	
	public Factory(VisualFrame gui) {
		this.gui = gui;
	}
	
	public Object createModel(Dispatcher dispatcher) {
		return new Model(dispatcher, gui);
	}
}
