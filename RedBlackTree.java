/**
 * File:		RedBlackTree.java
 * Author:		Annie Wu
 * Class:		CS 241 - Data Structures and Algorithms II
 * 
 * Assignment:		Program 3
 * Date:		28 February 2018
 * 
 * Purpose:		This class contains the Red-Black tree
 * 			and any functions regarding the Red-Black tree. 
 */

public class RedBlackTree {
	
	
	private RedBlackNode root;
	private static RedBlackNode nullNode = new RedBlackNode(0);
	private static final boolean BLACK = false;
	private static final boolean RED = true;
	
	private String theHeight = "\nThe height of the red-black tree is: ";
	private String leafNumber = "\nNumber of leaves in red-black tree: ";


	
	/**
	 * This is the default constructor.
	 * The purpose is to create a new tree.
	 */
	public RedBlackTree() {
		root = new RedBlackNode(0);
		root.left = nullNode;
		root.right = nullNode;
		nullNode.parent = root;
	}
	
	/**
	 * This is the function add.
	 * The purpose is to call the recursive add function to 
	 * insert a node into the red black tree.
	 */
	public void add(int data) {
		if (this.root.isLeaf()) {
			RedBlackNode node = new RedBlackNode(data);
			node.color = BLACK;
			this.root = node;
		}
		else 
			add(this.root, data);
	}
	
	/**
	 * This is the function add.
	 * The purpose is to recursively add the node into the tree.
	 */
	public void add(RedBlackNode root, int data) {
		if (root.isLeaf()) {
			RedBlackNode newNode = new RedBlackNode(data);
			newNode.parent = root.parent;
			if (newNode.parent.left == root)
				newNode.parent.left = newNode;
			else
				newNode.parent.right = newNode;
			addHelper(newNode);
		}
		else if (data < root.getValue())
			add(root.left, data);
		else if (data > root.getValue())
			add(root.right, data);
		else
			return;
	}
	
	/**
	 * This is the function addHelper.
	 * The purpose is to fix the tree to keep it balanced with the correct colored nodes
	 * after a node has been added to the tree.
	 */
	public void addHelper(RedBlackNode node) {
		//insert a red note
		node.color = RED;
		//if the node and its parent is RED
		if (node != null & node != root & node.parent.color == RED) {
			//if the uncle is also RED
			if (node.uncle().color == RED) {
				node.parent.color = BLACK;
				node.uncle().color = BLACK;
				node.grandparent().color = RED;
				addHelper(node.grandparent());
			}
			//if the parent is the grandparent's left child
			else if (node.parent == node.grandparent().left) {
				//if the node is a right child
				//also do a left rotation
				if (!isLeftChild(node))
					rotateLeft(node.parent);
				node.parent.color = BLACK;
				node.grandparent().color = RED;
				rotateRight(node.grandparent());
			}
			//if the parent is the grandparent's right child
			else if (node.parent == node.grandparent().right) {
				//if the node is a left child
				//also do a right rotation
				if (isLeftChild(node))
					rotateRight(node.parent);
				node.parent.color = BLACK;
				node.grandparent().color = RED;
				rotateLeft(node.grandparent());
			}
		}
		root.color = BLACK;
	}
	
	/**
	 * This is the function remove.
	 * The purpose is to call the recursive removeHelper function to delete
	 * the node from the red-black tree.
	 */
	public void remove(int data) {
		RedBlackNode current = null;
		//if tree is empty
		if (isEmpty())
			return;
		//if the value is in the tree
		else if (exists(data)) {
			current = searchForNode(data);
			removeHelper(current, data);
		}
		else
			return;
	}
	
	/**
	 * This is the function remove.
	 * The purpose is to recursively remove the given node from
	 * the red-black tree.
	 */
	public void removeHelper(RedBlackNode node, int data) {
		RedBlackNode child = null;
		//if the node is a leaf with null children
		if (node.isLeaf() && !node.hasLeft() && !node.hasRight()) {
			if (node.parent != null) {
				//if it is a left child
				if(isLeftChild(node)) {
					node = new RedBlackNode(data);
					node.left = null;
					node.right = null;
					node.parent.left = null;
					removeHelper2(node);
				}
				//if it is a right child
				else {
					node = new RedBlackNode(data);
					node.left = null;
					node.right = null;
					node.parent.right = null;
					removeHelper2(node);
				}
			}
			else if (node == root) {
				node.left = null;
				node.right = null;
				node = null;
			}
		}
		//if the node has only one leaf child
		else if (node.left.isLeaf() || node.right.isLeaf()) {
			//if the left child is a leaf
			if (node.left.isLeaf())
				child = node.right;
			//if the right child is a leaf
			else
				child = node.left;
			if (child.color == RED)
				child.color = node.color;
			swapNode(node, child);
			//set value to 0 so it is out of the tree
			node.setValue(-1);;
		}
		//if the node has both children
		else if (!node.isLeaf()) {
			RedBlackNode pred = predecessor(node);
			node.value = pred.value;
			removeHelper(pred, pred.getValue());
		}
	}
	
	/**
	 * This is the function removeHelper2.
	 * The purpose is to remove a node and fix the tree to keep it fixTreed 
	 * with the correct colored nodes.
	 */
	public void removeHelper2(RedBlackNode node) {
		if (node.parent == null)
			return;
		//if it is the root node with one leaf
		if (node == root && node.left.isLeaf() || node.right.isLeaf())
			root.color = BLACK;
		//if it is the root node and its children are leaves
		else if (node == root && node.right.isLeaf() && node.left.isLeaf()) {
			node.right.color = BLACK;
			node.left.color = BLACK;
		}
		//if it is the root and its children are not leaves
		else if (node == root && !node.right.isLeaf() && !node.left.isLeaf())
			root.color = BLACK;
		
		//case 1: black root node
		else if (!node.hasLeft() && !node.hasRight())
			return;
		
		//case 2: the node has a RED sibling
		//single rotation
		//recolor parent and sibling
		else if (node.sibling().color == RED) {
			node.parent.color = RED;
			node.sibling().color = BLACK;
			//if it is a left child, rotate left
			if (isLeftChild(node))
				rotateLeft(node.parent);
			//if it is a right child, rotate right
			else
				rotateRight(node.parent);
			removeHelper2(node);
		}
		
		//case 3: the sibling and its children are black
		//with either RED or BLACK parent
		//set sibling to RED and parent to BLACK
		else if (node.sibling().color == BLACK && node.sibling().left.color == BLACK && node.sibling().right.color == BLACK) {
			if (node.parent.color == RED || node.parent.color == BLACK) {
				node.sibling().color = RED;
				node.parent.color = BLACK;
			}
			else
				removeHelper2(node.parent);
			
		}
		
		//case 4: the node has a BLACK sibling...
		else if (node.sibling().color = BLACK) {
			//...and the sibling has a RED inner child
			//double rotation
			//recolor nodes
			if (innerChild(node.sibling()).color == RED) {
				if (isLeftChild(node)) {
					node.parent.right.left.color = BLACK;
					node.parent.right.color = RED;
					rotateRight(node.parent.right);
				}
				else {
					node.parent.left.right.color = BLACK;
					node.parent.left.color = RED;
					rotateLeft(node.parent.left);
				}
				if (isLeftChild(node)) {
					boolean tempColor = node.parent.right.color;
					node.parent.right.color = node.parent.color;
					node.parent.color = tempColor;
					outerChild(node.sibling()).color = BLACK;
					rotateLeft(node.parent);
				}
				else {
					colorSwap(node.parent, node.sibling());
					outerChild(node.sibling()).color = BLACK;
					rotateRight(node.parent);
					colorSwap(node.parent, node.sibling());
				}
				removeHelper2(root);
			}
			//...and the sibling has a RED outer child
			//single rotation
			//recolor nodes
			else if (outerChild(node.sibling()).color == RED) {
				if (isLeftChild(node)) {
					boolean tempColor = node.parent.right.color;
					node.parent.right.color = node.parent.color;
					node.parent.color = tempColor;
					outerChild(node.sibling()).color = BLACK;
					rotateLeft(node.parent);
				}
				else {
					colorSwap(node.parent, node.sibling());
					outerChild(node.sibling()).color = BLACK;
					rotateRight(node.parent);
					colorSwap(node.parent, node.sibling());
				}
				removeHelper2(root);
			}
		}
	}
	
    
    /**
     * This is the function searchForNode.
     * The purpose is to call the recursive function searchForNode
     * with the given value.
     */
    public RedBlackNode searchForNode(int value) {
    	return searchForNode(root, value);
    }
    
    /**
     * This is the function searchForNode.
     * The purpose is to recursively search for the node of the entered value.
     */
    public RedBlackNode searchForNode(RedBlackNode node, int value) {
    	RedBlackNode found = null;
    	while ((node != null)) {
        	//get the value for the node
            int nodeValue = node.getValue();
            
            //if the input value is less than the value of the node,
            //get the left node
            if (value < nodeValue)
                node = node.left;
            
            //if the input value is greater than the value of the node,
            //get the right node
            else if (value > nodeValue)
                node = node.right;
            
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
	 * This is the function delete20.
	 * The purpose is to delete the first 20 values in preorder
	 * and return the height and number of leaves in the tree.
	 */
	public void delete20() {

		getHeight();
		getNumberOfLeaves();
	}
	
    /**
     * This is the function predecessor.
     * The purpose is to get the inorder predecessor of a node.
     */
    public RedBlackNode predecessor(RedBlackNode node) {
    	RedBlackNode pred = null;
    	if (isLeftChild(node)) {
    		pred = node.left;
    		if (pred != null) {
    			while (!pred.right.isLeaf()) {
    				pred = pred.right;
    			}
    		}
    	}
    	else {
    		pred = node.right;
    		if (pred != null) {
    			while (!pred.left.isLeaf()) {
    				pred = pred.left;
    			}
    		}
    	}
    	return pred;
    }
    
	/**
	 * This is the function isRedNode.
	 * The purpose is to check to see if a node is red.
	 * It will return true if it is RED.
	 */
	public boolean isRedNode(RedBlackNode node) {
		if (node == null)
			return false;
		return (node.color == RED);
	}
	
	
	/**
	 * This is the function colorSwap.
	 * The purpose is the swap the color of two nodes.
	 */
	public void colorSwap(RedBlackNode node1, RedBlackNode node2) {
		boolean temporary = node1.color;
		node1.color = node2.color;
		node2.color = temporary;
	}
	
	/**
	 * This is the function rotateLeft.
	 * The purpose is to rotate to the left (counter clockwise).
	 */
	public void rotateLeft(RedBlackNode current) {
		RedBlackNode oldRightChild = current.right;
		swapNode(current, oldRightChild);
		current.right = oldRightChild.left;
		if (oldRightChild.left != null)
			oldRightChild.left.parent = current;
		oldRightChild.left = current;
		current.parent = oldRightChild;
	}
	
	/**
	 * This is the function rotateRight.
	 * The purpose is to rotate to the right (clockwise).
	 */	
	public void rotateRight(RedBlackNode current) {
		RedBlackNode oldLeftChild = current.left;
		swapNode(current, oldLeftChild);
		current.left = oldLeftChild.right;
		if (oldLeftChild.right != null)
			oldLeftChild.right.parent = current;
		oldLeftChild.right = current;
		current.parent = oldLeftChild;
	}
		
	/**
	 * This is the function isLeftChild.
	 * The purpose is to check to see if a node is a left child.
	 */
	public boolean isLeftChild(RedBlackNode current) {
		boolean isLeft = true;
		if(current != root) {
			if (current.parent.left == current)
				isLeft = true;
			else
				isLeft = false;
		}
		return isLeft;
	}
	
	/**
	 * This is the function isOuterChild.
	 * The purpose is to check to see if a node is an outer node.
	 */
	public boolean isOuterChild(RedBlackNode current) {
		boolean outer = true;
		//if it is on the left side of the tree
		if (current.getValue() <= root.getValue()) {
			if (current.parent.left == current && current.grandparent().left == current.parent)
				outer = true;
			else
				outer = false;
		}
		//if it is on the right side of the tree
		else if (current.getValue() > root.getValue()) {
			if (current.parent.right == current && current.grandparent().left == current.parent)
				outer = true;
			else
				outer = false;
		}
		return outer;
	}
	
	/**
	 * This is the function outerChild.
	 * The purpose is to return the outer child of a given node.
	 */
	public RedBlackNode outerChild(RedBlackNode current) {
		if (isLeftChild(current))
			current = current.left;
		else 
			current = current.right;
		return current;
	}
	
	/**
	 * This is the function innerChild.
	 * The purpose is to return the inner child of a given node.
	 */
	public RedBlackNode innerChild(RedBlackNode current) {
		if (isLeftChild(current))
			current = current.right;
		else
			current = current.left;
		return current;
	}

	
	/**
	 * This is the function swap.
	 * The purpose is to swap two nodes.
	 */
	public void swapNode(RedBlackNode node, RedBlackNode oldNode) {
		if (node == root) {
			root = oldNode;
			root.color = BLACK;
		}
		else {
			if (node == node.parent.left)
				node.parent.left = oldNode;
			else
				node.parent.right = oldNode;
		}
		if (oldNode != null)
			oldNode.parent = node.parent;
		node.color = RED;
	}
		
	/**
	 * This is the function isEmpty.
	 * The purpose is to check to see if the tree is empty.
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	public void getHeight() {
		System.out.print(theHeight + getHeight(root));
	}
	
    /**
     * This is the function getHeight.
     * The purpose is to get the height of the tree.
     */
    public int getHeight(RedBlackNode root) {
		int height = 0;
		if(root == null)
			return 0;
		else if(root.color == RED) {
			height = Math.max(getHeight(root.left), getHeight(root.right));
			
		}
		else if(root.color == BLACK) {
			height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
		}
		return height;
    }
    
    public void getNumberOfLeaves() {
    	System.out.print(leafNumber + getNumberOfLeaves(root));
    }
    
    /**
     * This is the function getNumberOfLeaves.
     * The purpose is to get the number of leaves in the tree.
     */
    public int getNumberOfLeaves(RedBlackNode root) {
    	if (root == null)
            return 0;
        if (root.isLeaf())
            return 1;
        else {
        	return getNumberOfLeaves(root.left) + getNumberOfLeaves(root.right);
        }
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
	
    public void preorder() {
        preorder(this.root);
    }

    /**
     * This is the recursive function for preorder traversal.
     * @param node
     */
    public void preorder(RedBlackNode node) {
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
