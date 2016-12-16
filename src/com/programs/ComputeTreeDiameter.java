package com.programs;

/**
 * Created by sagupta on 11/3/15.
 */
public class ComputeTreeDiameter {

    Integer maxDiameter = -1;
    
    public void solution(Node root) {
        computeDiameter(root);
        System.out.println("Diamter="+maxDiameter);
    }
    
    public void computeDiameter(Node node) {
        
        if (node == null) return;

        node.maxNodesToLeafOnLST = 1;
        node.maxNodesToLeafOnRST = 1;
       
        if (node.lst != null) {
            computeDiameter(node.lst);
            node.maxNodesToLeafOnLST = Math.max(node.lst.maxNodesToLeafOnLST,node.lst.maxNodesToLeafOnRST) +1;
        }
        if (node.rst != null) {
            computeDiameter(node.rst);
            node.maxNodesToLeafOnRST = Math.max(node.rst.maxNodesToLeafOnLST,node.rst.maxNodesToLeafOnRST) +1;
        }
        
        if ((node.maxNodesToLeafOnLST + node.maxNodesToLeafOnRST -1) > maxDiameter) {
            maxDiameter = node.maxNodesToLeafOnLST + node.maxNodesToLeafOnRST -1;
        }
        
    }
        
}
