package com.programs.problemstbc;

public class TwoCitiesScheduling {
    //https://leetcode.com/problems/two-city-scheduling/description/

    //algo:
    // work with paired values for each person (V1, V2) where V1 is distance to city 1 and V2 is distance to city 2
    // sort on both city distances in 3 buckets C1: persons closest to city1, C2: persons closest to city2
    // keep persons with equal distance in separate bucket C12

    // if C1 and C2 are equal in size, distribute C12 equally in B1 and B2
    // otherwise pick bucket of lesser size (either C1 or C2).
    // let's say C1 > C2 thus countExcess = C1-C2
    // add C12 to C2, count excess again = C1-C2-C12
    // if countExcess > 0
    // C1 values are in 2 groups first N pairs, excess remaining pairs < N
    // build 2 min heaps on C1 #1 for all pairs on V2 and #2 just for excess pairs on V1
    // pick min value from heap #1 -- if it exists in #2 just use it
    // else it doesn't in heap #2 => it is one of first N values for bucket C1 :
    // compute cost of replacing it: pick min value from heap #2  and compute delta add (Vx1,Vx2)#1 & (Vy1, Vy2)#2 as Vy1-Vx1+Vx2 vs Vy2
    // if Vy1-Vx1+Vx2 is smaller replace it, otherwise not

}
