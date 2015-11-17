import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by sagupta on 11/3/15.
 */
public class AllPasswordsOnKeypad {

    Map<Integer,GNode> mapNodes = new HashMap();
    Integer totalNodes = 0;

    public class GNode {
        Integer value;
        Coordinate coordinate;
        ArrayList<GNode> nodeList = new ArrayList<>();
        HashSet<ArrayList<Integer>> allPathsEndingAtNode = new HashSet<>();
        HashSet<ArrayList<Integer>> newPathsEndingAtNode = new HashSet<>();

        public GNode(Integer value, Coordinate coordinate) {
            this.value = value;
            this.coordinate = coordinate;
            ArrayList<Integer> pathToSelf = new ArrayList<>();
            pathToSelf.add(value);
            allPathsEndingAtNode.add(pathToSelf);
            newPathsEndingAtNode.add(pathToSelf);
        }
    }

    public class Coordinate {
        public Coordinate(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }
        Integer x;
        Integer y;

        boolean isAdjacent(Coordinate otherCo) {
            if ((otherCo.x == x && Math.abs(otherCo.y - y) == 1) || (otherCo.y == y && Math.abs(otherCo.x - x) == 1) || (Math.abs(otherCo.x - x) == 1 &&  Math.abs(otherCo.y - y) == 1)) {
                return true;
            }
            return false;
        }
    }

    public void solution(int[][] keyPadMatrix) {
        if (keyPadMatrix == null) {
            keyPadMatrix = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        }
        totalNodes = keyPadMatrix.length * keyPadMatrix[0].length;
        for (int i=0;i<keyPadMatrix.length && keyPadMatrix[i] != null;i++) {
            for (int j=0;j<keyPadMatrix[i].length;j++) {
                int value = keyPadMatrix[i][j];

                // build graph node by node, and compute all paths/passwords incrementally after each node addition
                GNode newNode = new GNode(value,new Coordinate(i,j));
                mapNodes.put(value,newNode);
                if (mapNodes.size() == 1) {
                    // first node
                    continue;
                }

                for (GNode node: mapNodes.values()) {
                    node.newPathsEndingAtNode.clear();
                }
                // compute all adjacent nodes...(2-4 nodes)
                ArrayList<GNode> adjacentNodes = getAdjacentNodes(newNode);
                newNode.nodeList.addAll(adjacentNodes);
                for (GNode adjNode : adjacentNodes) {
                    // extend paths ending at each adjacent node to end at this new node
                    extendPathsFromAdjNodeToNewNode(adjNode, newNode);
                    adjNode.nodeList.add(newNode);
                }
                // add all new reverse paths that are now possible starting with this new node to all other nodes.
                addReversePaths(newNode);

                // add new paths recursively (DFS on Graph) that extend through this new node from old nodes.
                // use an explorePath path to prevent exploring the same edge again in the Graph and thus prevent infinite recursion
                ArrayList<Integer> explorePath = new ArrayList<>();
                for (GNode adjNode : adjacentNodes) {
                    explorePath.clear();
                    explorePath.add(newNode.value);
                    addExtendablePathsThroughNode(adjNode, newNode, explorePath);
                }
            }
        }

        Integer totalPasswords = 0;
        for(GNode node : mapNodes.values()) {
            totalPasswords += node.allPathsEndingAtNode.size();
            System.out.println("Node=" + node.value + ": count passwords ending at this node = " + node.allPathsEndingAtNode.size() + " Passwords:" + node.allPathsEndingAtNode.toString());
        }
        System.out.println("Total="+totalPasswords);
    }

    void extendPathsFromAdjNodeToNewNode(GNode existingNode, GNode newNode) {
        for (ArrayList<Integer> path : existingNode.allPathsEndingAtNode) {
            ArrayList<Integer> newPath = new ArrayList<>(path.size()+1);
            newPath.addAll(path);
            newPath.add(newNode.value);
            newNode.allPathsEndingAtNode.add(newPath);
            newNode.newPathsEndingAtNode.add(newPath);
        }
    }

    ArrayList<GNode> getAdjacentNodes(GNode giveNode) {
        ArrayList<GNode> adjacentNodes = new ArrayList<>(4);
        for(GNode node : mapNodes.values()) {
            if (node.coordinate.isAdjacent(giveNode.coordinate)) {
                adjacentNodes.add(node);
            }
        }
        return  adjacentNodes;
    }

    ArrayList<Integer> reversePath(ArrayList<Integer> path) {
        ArrayList<Integer> revPath = new ArrayList<>(path.size());
        for (int i=path.size()-1;i>=0;i--) {
            revPath.add(path.get(i));
        }
        return revPath;
    }

    void addReversePaths(GNode node) {
        for (ArrayList<Integer> path : node.allPathsEndingAtNode) {
            GNode endNodeOfReversePath = mapNodes.get(path.get(0));
            if (path.size() > 1 && endNodeOfReversePath != node) {
                ArrayList<Integer> revPath = reversePath(path);
                endNodeOfReversePath.allPathsEndingAtNode.add(revPath);
                endNodeOfReversePath.newPathsEndingAtNode.add(revPath);
            }
        }
    }

    boolean edgeAlreadyPresentInPath(ArrayList<Integer> path, Integer v1, Integer v2) {
        for(int i=0;i<path.size();i++) {
            if (path.get(i).equals(v1) && i+1<path.size() && path.get(i+1).equals(v2)) {
                return true;
            }
            if (path.get(i).equals(v2) && i+1<path.size() && path.get(i+1).equals(v1)) {
                return true;
            }
        }
        return false;
    }

    void addExtendablePathsThroughNode(GNode targetNode, GNode sourceNode, ArrayList<Integer> explorePath) {
        explorePath.add(targetNode.value);
        Integer numPathsAddedInThisCall = 0;
        for(ArrayList<Integer> path: sourceNode.newPathsEndingAtNode) {
            if (path.size() == 1 || (path.size() > 1 && !path.contains(targetNode.value))) {
                ArrayList<Integer> newPath = new ArrayList<>(path.size()+1);
                newPath.addAll(path);
                newPath.add(targetNode.value);
                if (!targetNode.allPathsEndingAtNode.contains(newPath)) {
                    targetNode.allPathsEndingAtNode.add(newPath);
                    targetNode.newPathsEndingAtNode.add(newPath);
                }
                numPathsAddedInThisCall++;
            }
        }

        // break the recursion if no new paths can be extended to this existing node.
        if (numPathsAddedInThisCall.equals(0)) {
            explorePath.remove(explorePath.size()-1);
            return;
        }

        for (GNode newTargetNode: targetNode.nodeList) {
            if (!edgeAlreadyPresentInPath(explorePath,targetNode.value,newTargetNode.value)) {
                addExtendablePathsThroughNode(newTargetNode, targetNode, explorePath);
            }
        }
        explorePath.remove(explorePath.size()-1);
        return;
    }
}
