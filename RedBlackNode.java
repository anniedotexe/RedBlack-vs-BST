/**
 * File:		RedBlackNode.java
 * Author:		Annie Wu
 * Class:		CS 241 - Data Structures and Algorithms II
 * 
 * Assignment:	Program 3
 * Date:		28 February 2018
 * 
 * Purpose:		This class contains the nodes in the red-black tree and 
 * 				any functions regarding the nodes.
 */

public class RedBlackNode extends BinaryNode{
	
	RedBlackNode parent = null;
	RedBlackNode left = null;
	RedBlackNode right = null;
	int value;
	int height;
	boolean color; //black false, red true
		
    /**
     * This is a constructor.
     * It creates a new node.
     */
	public RedBlackNode(int value) {
		this(value, null, null, null);
	}
	
	/**
	 * This is a constructor.
	 * It creates a new node with more parameters.
	 */
	public RedBlackNode(int value, RedBlackNode left, RedBlackNode right, RedBlackNode parent) {
		this.value = value;
		this.color = false; //false is BLACK
		this.left = left;
		this.right = right;
		this.parent = parent;
	}
 	
	/**
	 * This is the function sibling.
	 * The purpose is to get the node's sibling if one exists.
	 */
	public RedBlackNode sibling() {
		if (this.parent != null) {
			if (this == parent.left)
				return parent.right;
			else
				return parent.left;
		}
		else return null;
	}
	
	/**
	 * This is the function grandparent.
	 * The purpose is to get the node's grandparent if one exists. 
	 */
	public RedBlackNode grandparent() {
		if (this.parent.parent != null)
			return this.parent.parent;
		else
			return null;
	}
	
	/**
	 * This is the function uncle.
	 * The purpose is to get the node's uncle if one exists.
	 */
	public RedBlackNode uncle() {
		if (this.parent.sibling() != null)
			return this.parent .sibling();
		else
			return null;
	}
}
