package test.java;
import main.java.*;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import process.Dispatcher;

public class TestClient {
	
	Klient klient;

	@Before
	public void setUp() throws Exception {
		VisualFrame vf = new VisualFrame();
		klient = new Klient("Test", vf,new Model(new Dispatcher(),vf ));
	}
	
	@Test
	public void testObsluzhen() {
		klient.setObsluzhen(true);
		assertTrue(klient.isObsluzhen());
	}

}
