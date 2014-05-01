package test.java;

import static org.junit.Assert.assertEquals;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.java.Factory;
import main.java.Kassir;
import main.java.Model;
import main.java.VisualFrame;

import org.junit.Before;
import org.junit.Test;

import process.Dispatcher;


public class TestModelWorking {
	private VisualFrame gui;
	private  Dispatcher dispatcher;
	private Model model;
	
	@Before
	public void setUp() throws Exception {
	    SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gui = new VisualFrame();
				
				gui.setVisible(true);
				 dispatcher = new Dispatcher();
					Factory factory = new Factory(gui);
					model = (Model) factory.createModel(dispatcher);	
					
				 	gui.startTestForTest(model, dispatcher);	
			}
		});
	   
	}
	
	@Test
	public void testDispetcherFinish(){
		while (true) {
			if ( dispatcher != null && dispatcher.getWaitingActorQueue()!= null ) {
				int size = dispatcher.getWaitingActorQueue().size();
				assertEquals(0, size,0);
				break;
			    
			}	
		}
		
		gui.dispose();
		
	}

}
