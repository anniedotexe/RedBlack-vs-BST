/**
 * File:		BinarySearchTree.java
 * Author:		Annie Wu
 * Class:		CS 241 - Data Structures and Algorithms II
 * 
 * Assignment:	Program 3
 * Date:		25 February 2018
 * 
 * Purpose:		This class contains the Binary Search Tree (BST)
 * 				and any functions regarding the BST. 
 */

public class BinarySearchTree {
	
	private BinaryNode root;
	
	private String alreadyInTree = " is already in the tree!";
	private String emptyTree = "The tree is empty!";
	private String doesNotExist = " does not exist in the tree.";
	private String deleted = " has been deleted from the tree.";
	private String leafNumber = "\nNumber of leaves in BST: ";
	private String theHeight = "\nThe height of the BST is: ";
	
	/**
	 * This is the default constructor for the BinarySearchTree.
	 * The purpose is to set the root to null.
	 */
	public BinarySearchTree() {
		root = null;
	}	
	
	/**
	 * This is the constructor when the user inputs a value.
	 * The purpose is to create a new node with that value and 
	 * set it as the root node.
	 */
	public BinarySearchTree(int rootEntry) {
		root = new BinaryNode(rootEntry);
	}
	
	/**
	 * This is the function insert.
	 * The purpose is to take an integer to insert into the BinarySearchTree
	 * and call the recursive insert function.
	 * 
	 */
	public void insert(int value) {
		this.root = insertHelper(this.root, value);
	}
	
	/**
	 * This is the function insertHelper. 
	 * The purpose is to recursively insert a node and its value into the BinarySearchTree.
	 */
	 public BinaryNode insertHelper(BinaryNode root, int value) {
	        //if the BinarySearchTree is empty, it will add a new node
	        if (root == null) {
	            root = new BinaryNode(value);
	            return root;
	        }
	        //recursively call insert to add the node into the BinarySearchTree
	        else if (value < root.getValue())
	            root.left = insertHelper(root.getLeft(), value);
	        else if (value > root.getValue())
	            root.right = insertHelper(root.getRight(), value);
	        else //if the value is already in the BinarySearchTree
	        	System.out.println(value + alreadyInTree);
	        return root;
	    }
			
	/**
	 * This is the function delete.
	 * The purpose is to delete a value from the user and 
	 * call the recursive delete function.
	 */
	public void delete(int value) {
		if (isEmpty()) 
			System.out.println(emptyTree);
		else if (search(value)){
			root = deleteHelper(root, value);
			System.out.println(value + deleted);
		}
		else
			System.out.println(value + doesNotExist);
	}
	
	/**
	 * This is the function deleteHelper.
	 * The purpose is to recursively delete a now and its value in the BinarySearchTree.
	 */
	public BinaryNode deleteHelper(BinaryNode root, int value) {
		if (root != null) {
			//if the value is less than the root value
			if (value < root.getValue())
				root.left = deleteHelper(root.getLeft(), value);
			//if the value is greater than the root value
			else if (value > root.getValue()) {
				root.right = deleteHelper(root.getRight(), value);
			}
			//if it is the root
			else {
				//if it only has left child
				if (root.getRight() == null)
					return root.getLeft();
				//if it only has right child
				if (root.getLeft() == null)
					return root.getRight();
				//if it has no children
				if (root.getLeft() == null && root.getRight() == null) 
					root = null;
				
				//create a temporary root node.
				//the root will be swapped with the minimum value 
				//of the right subtree to delete the root node
				BinaryNode temp = root;
				root = getLeftmost(temp.getRight());
				root.right = deleteMin(temp.getRight());
				root.left = temp.getLeft();
			}
		}
		return root;
	}
	
	/**
	 * This is the function delete20.
	 * The purpose is to delele the first 20 nodes by preorder
	 * and return the height and number of leaves in the tree.
	 */
	public void delete20() {

		getHeight();
		getNumberOfLeaves();
	}
	
	/**
	 * This is the function deleteMin.
	 * The purpose is to delete the minimum value in the BinarySearchTree.
	 */
	public BinaryNode deleteMin(BinaryNode root) {
	    if(root.getLeft() == null) {
	        return root.getRight();
	    }
	    root.left = deleteMin(root.getLeft());
	    return root;
	}
	
	/**
	 * This if the function getLeftmost.
	 * The purpose is to recursively get the leftmost node.
	 */
	public BinaryNode getLeftmost(BinaryNode root) {
		if (!root.hasLeft())
			return root;
		else
			return getLeftmost(root.getLeft());
	}
	
	/**
	 * This is the function getRightmost.
	 * The purpose is to recursively get the rightmost node.
	 */
	public BinaryNode getRightmost(BinaryNode root) {
		if (!root.hasRight())
			return root;
		else
			return getRightmost(root.getRight());
	}
	
	/**
	 * This is the function search.
	 * The purpose is to search for a value in the BinarySearchTree.
	 * It will return true if found or false if it does not exist.
	 */
	public boolean search(int value) {
        return search(root, value);
    }

	/**
	 * This is the function search.
	 * The purpose is to recursively search for a given node and value in the BinarySearchTree.
	 * It will return true if found or false if it does not exist.
	 */
    public boolean search(BinaryNode node, int value) {
    	//start with the found node as false
    	boolean found = false;
    	
    	if (node == null) {
        	found = false;
        }
        while ((node != null)) {
        	found = false;
        	//get the value for the node
            int nodeValue = node.getValue();
            
            //if the input value is less than the value of the node,
            //get the left node
            if (value < nodeValue)
                node = node.getLeft();
            
            //if the input value is greater than the value of the node,
            //get the right node
            else if (value > nodeValue)
                node = node.getRight();
            
            //stop the function when the value is found
            else {
                found = true;
                break;
            }
            found = search(node, value);
        }
        return found;
    }    	
    
    /**
     * This is the function searchForNode.
     * The purpose is to call the recursive function searchForNode
     * with the given value.
     */
    public BinaryNode searchForNode(int value) {
    	return searchForNode(root, value);
    }
    
    /**
     * This is the function searchForNode.
     * The purpose is to recursively search for the node of the entered value.
     */
    public BinaryNode searchForNode(BinaryNode node, int value) {
    	BinaryNode found = null;
    	while ((node != null)) {
        	//get the value for the node
            int nodeValue = node.getValue();
            
            //if the input value is less than the value of the node,
            //get the left node
            if (value < nodeValue)
                node = node.getLeft();
            
            //if the input value is greater than the value of the node,
            //get the right node
            else if (value > nodeValue)
                node = node.getRight();
            
            //stop the function when the value is found
            else {
                found = node;
                break;
            }
            found = searchForNode(node, value);
        }
        return found;
    }
       
	
	/**
	 * This is the function isEmpty.
	 * The purpose is to check to see if the tree is empty.
	 */
	public boolean isEmpty() {
		return root == null;
	}
    
    public void getNumberOfLeaves() {
    	System.out.print(leafNumber + getNumberOfLeaves(root));
    }
    
    /**
     * This is the function getNumberOfLeaves.
     * The purpose is to get the number of leaves in the tree.
     */
    public int getNumberOfLeaves(BinaryNode root) {
    	if (root == null)
            return 0;
        if (root.isLeaf())
            return 1;
        else {
        	return getNumberOfLeaves(root.left) + getNumberOfLeaves(root.right);
        }
    }
    
    /**
     * This is the function getHeight.
     * The purpose is to call the recursive function getHeight
     * to get the height of the tree.
     */
    public void getHeight() {
    	System.out.print(theHeight + getHeight(root));
    }
    
    /**
     * This is the function getHeight.
     * The purpose is to get the height of the tree.
     */
    public int getHeight(BinaryNode root) {
    	int height = 0;
    	if (root != null) {
    		height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    	}
    	return height;
    }
    
    /**
     * This is the function exists.
     * The purpose is to check to see if a value exists in the tree.
     */
	public boolean exists(int value) {
		if (root == null)
        	return false;
        else
        	return root.exists(value);
	}
	
    /**
     * This is the function printInRange.
     * The purpose is to call the recursive function printInRange
     * to print the values in the range of a and b.
     */
    public void printInRange(int a, int b) {
    	printInRange(root, a, b);
    }
    /**
     * This is the function printInRange.
     * The purpose is to print out all values in the range of
     * a and b from the user input relative to the root node.
     */
    public void printInRange(BinaryNode node, int a, int b) {
    	if (node == null)
    		return;
    	//get left subtree values
    	if (a < node.getValue())
    		printInRange(node.left, a, b);
    	//print values
    	if (a <= node.getValue() && b >= node.getValue())
    		System.out.print(node.getValue() + " ");
    	//get right subtree values
    	if (b > node.getValue())
    		printInRange(node.right, a, b);
    }
	
    public void preorder() {
        preorder(root);
    }

    /**
     * This is the recursive function for preorder traversal.
     * @param node
     */
    public void preorder(BinaryNode node) {
    	if (node != null) {
        	//first, process root
        	//then, process nodes in the left subtree recursively
        	//lastly, process nodes in the right subtree recursively
            System.out.print(node.getValue() + " ");
            preorder(node.left);         
            preorder(node.right);
        }
    }
}
