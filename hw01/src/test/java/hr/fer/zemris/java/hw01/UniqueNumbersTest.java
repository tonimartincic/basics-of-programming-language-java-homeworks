package hr.fer.zemris.java.hw01;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

public class UniqueNumbersTest {

	@Test
	public void testAddNodeOneNode() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 500);
		assertEquals(500, glava.value);
		assertEquals(null, glava.right);
		assertEquals(null, glava.left);
	}
	
	@Test
	public void testAddNodeAddTwoSameNodes() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 500);
		glava = UniqueNumbers.addNode(glava, 500);
		assertEquals(500, glava.value);
		assertEquals(null, glava.right);
		assertEquals(null, glava.left);
	}
	
	@Test
	public void testAddNodeFourDifferentNodes() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 35);
		glava = UniqueNumbers.addNode(glava, 76);
		assertEquals(42, glava.value);
		assertEquals(21, glava.left.value);
		assertEquals(35, glava.left.right.value);
		assertEquals(76, glava.right.value);
	}
	
	@Test
	public void testAddNodeFiveNodesTwoSame() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 35);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 35);
		assertEquals(42, glava.value);
		assertEquals(21, glava.left.value);
		assertEquals(35, glava.left.right.value);
		assertEquals(76, glava.right.value);
	}

	@Test
	public void testTreeSizeEmptyTree() {
		TreeNode glava = null;
		int size = UniqueNumbers.treeSize(glava);
		assertEquals(0, size);
	}
	
	@Test
	public void testTreeSizeTreeWithOneNode() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 500);
		int size = UniqueNumbers.treeSize(glava);
		assertEquals(1, size);
	}
	
	@Test
	public void testTreeSizeTreeWithFourNodes() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 35);
		glava = UniqueNumbers.addNode(glava, 76);
		int size = UniqueNumbers.treeSize(glava);
		assertEquals(4, size);
	}

	@Test
	public void testContainsValueEmptyTree() {
		TreeNode glava = null;
		boolean contains = UniqueNumbers.containsValue(glava, 10);
		assertFalse(contains);
	}
	
	@Test
	public void testContainsValueDoesContains() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 35);
		glava = UniqueNumbers.addNode(glava, 76);
		boolean contains = UniqueNumbers.containsValue(glava, 35);
		assertTrue(contains);
	}
	
	@Test
	public void testContainsValueDoesNotContains() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 35);
		glava = UniqueNumbers.addNode(glava, 76);
		boolean contains = UniqueNumbers.containsValue(glava, 10);
		assertFalse(contains);
	}

}
