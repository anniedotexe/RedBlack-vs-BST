/**
 * File:		BinaryNode.java
 * Author:		Annie Wu
 * Class:		CS 241 - Data Structures and Algorithms II
 * 
 * Assignment:	Program 3
 * Date:		25 February 2018
 * 
 * Purpose:		This class contains the nodes in the binary search tree and 
 * 				any functions regarding the nodes.
 */

public class BinaryNode {
	
    BinaryNode left;
    BinaryNode right;
    int value;

    /**
     * This is the default constructor.
     * The purpose is to call the constructor with the given integer, 0.
     */
    public BinaryNode() {
        this(0);
    }
    /**
     * This is the constructor.
     * The purpose is to call the constructor with the given data and null nodes.
     */
    public BinaryNode(int data) {
        this(data, null, null);
    }
    
    /**
     * This is the constructor.
     * The purpose is to set the value to the data, 
     * set the left child to a new left child,
     * and set the right child to a new right child.
     */
    public BinaryNode(int value, BinaryNode left, BinaryNode right) {
    	this.value = value;
    	this.left = left;
    	this.right = right;
    }
    
    /**
     * This is the function hasLeft.
     * The purpose is to check to see if a left child exists.
     */
    public boolean hasLeft() {
    	return left != null;
    }
    
    /**
     * This is the function setLeft.
     * The purpose is to set the left node.
     */
    public void setLeft(BinaryNode node) {
        left = node;
    }

    /**
     * This is the function getLeft.
     * The purpose is to return the left node. 
     */
    public BinaryNode getLeft() {
        return left;
    }
    
    /**
     * This is the function hasRight.
     * The purpose is to check to see if a right child exists.
     */
    public boolean hasRight() {
    	return right != null;
    }
    
    /**
     * This is the function setRight.
     * The purpose is to set the right node.
     */
    public void setRight(BinaryNode node) {
        right = node;
    }

    /**
     * This is the function getRight.
     * The purpose is to return the right node.
     */
    public BinaryNode getRight() {
        return right;
    }

    /**
     * This is the function setValue.
     * The purpose is to set value to a node.
     */
    public void setValue(int data) {
        value = data;
    }

    /**
     * This is the function getValue.
     * The purpose is to return the value of the node.
     */
    public int getValue() {
        return value;
    }
    
    /**
     * This is the function isLeaf.
     * The purpose is to check to see if the node is a leaf.
     */
    public boolean isLeaf() {
    	return (this.left == null) && (this.right == null);
    }
    
    /**
     * This is the function exists.
     * The purpose is to check to see if a value exists in the tree.
     */
    public boolean exists(int value) {
        if (value == this.value)
        	return true;
        else if (value < this.value) {
        	if (left == null)
        		return false;
        	else
        		return left.exists(value);
        } else if (value > this.value) {
        	if (right == null)
        		return false;
        	else
        		return right.exists(value);
        }
        return false;
    }
}


