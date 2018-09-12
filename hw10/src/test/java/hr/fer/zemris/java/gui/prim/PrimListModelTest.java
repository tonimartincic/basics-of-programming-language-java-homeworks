package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class PrimListModelTest {

	@Test
	public void testCreatePrimListModel() {
		PrimListModel<Integer> primListModel = new PrimListModel<>();
		
		assertEquals(1, primListModel.getSize());
	}
	
	@Test
	public void testGetElementAtIndex0() {
		PrimListModel<Integer> primListModel = new PrimListModel<>();
		
		assertEquals(1, (int) primListModel.getElementAt(0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetElementAtNegativeIndex() {
		PrimListModel<Integer> primListModel = new PrimListModel<>();
		
		primListModel.getElementAt(-1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetElementAtToBigIndex() {
		PrimListModel<Integer> primListModel = new PrimListModel<>();
		
		primListModel.getElementAt(1);
	}
	
	@Test
	public void testNext() {
		PrimListModel<Integer> primListModel = new PrimListModel<>();
		
		primListModel.next();
		primListModel.next();
		
		assertEquals(3, primListModel.getSize());
		
		assertEquals(1, (int) primListModel.getElementAt(0));
		assertEquals(2, (int) primListModel.getElementAt(1));
		assertEquals(3, (int) primListModel.getElementAt(2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNullAsListDataListener() {
		PrimListModel<Integer> primListModel = new PrimListModel<>();
		
		primListModel.addListDataListener(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddSameListDataListenerTwice() {
		PrimListModel<Integer> primListModel = new PrimListModel<>();
		
		ListDataListener l = new ListDataListener() {
			
			@Override
			public void intervalRemoved(ListDataEvent e) {}
			
			@Override
			public void intervalAdded(ListDataEvent e) {}
			
			@Override
			public void contentsChanged(ListDataEvent e) {}
		};
		
		primListModel.addListDataListener(l);
		primListModel.addListDataListener(l);
	} 
}
