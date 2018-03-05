package com.programs;

public class NTree {
/*

//
// {(2, 0), (1, 0), (3, 0), (0,0)}
// NTree - 0x100
// 2 -> 0x100
// 3 -> 0x200
//
// {(2, 0), (1,0), (4, 3)}
//

// {(2, 0), (1, 0), (3, 0), (0,0)}
// {0, 2}, (0, 1), (0, 3), (0,0) }
// {0, 0}, (0, 1), (0, 2), (0,3), (1, 4), (2, 5), (3, 6), (7, 10) }

NTree

List<NTree>

T1->T2->T3....

T3 nodes within T3
child node from T3, connecting to parent in T2

parent is lower id.

child node parent value lower then root of tree --> skip tree

// for search:

HashMap


id1 --> set of child ids
id2,  --> set of child ids

0 - (1, 2, 3, 4, 5, 6) = min, max HashSet<int>
x - (1, ....)
y - ()
6 -  7

struct Record {
    size_t id;
    size_t parent;
};

struct NTree {
    int id;
    vector<NTree*> children;
};

NTree* Deserialize(const vector<Record>& records) {

}

class HelloWorld {
    public static void main(String args[]) {
        System.out.println("Hello world - Java!");
    }

    public class Record {
        Integer p;
        Integer c;
    }

    public NTree {
        Integer parent;
        List<NTree> children;
    }

    public NTree deserialize(List<Records> records) {

        // sort the records by parent
        List<Records> sortedByParent = Collections.sort(records, new RecorComparator())
        Map<Integer, Set<Integer> connectedComponents = new HashMap();
        // scan if we can build a tree
        for (Record r: sortedByParent) {
            Set<Integer> currentSet = connectedComponents.get(r.p);
            if (currentSet == null) {
                currentSet = new HashSet();
                connectedComponents.put()
            }
        }
        // build a tree if we can


    }

    int RecordComparator(Record r1, Record r2) implements Comparator{
        if (r1.p > r2.p) return 1;
        if (r1.p < r2.p) return -1;
        return 0;
    }

}

 */
}
