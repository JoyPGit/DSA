# Graph Algorithms

## Table of Contents
- [Check conditions](#check-conditions) 
- [Directed](#directed-1)
- [Undirected](#undirected)
- [Grid](#grid)

### Check conditions 

`Grid`
> no union find
> dfs (w/ visited)
> bfs
<br>


1. with edge weights (adj matrix), else adj list <br>
2. shortest time/path (BFS w/ pruning) <br>
3. order/loop (Topo, Dfs w/ coloring / Kahn's) <br>
4. traversal/count (Dfs) <br>
5. If no edge weights, prefer dfs <br>


<br>

`Undirected1`
> loop/components (union find) <br>
> bipartite/ `shortest time/dist` (bfs) <br>
> count (dfs) <br>

<br>

**IMP** in bfs, 
> 1 `size` needs to be used for grid <br>
> 
> 2 `visited` always mark visited when adding to q, not when processing the node, why? <br> 
> `visited` is for restricting the addition of nodes to q, <br> 
> **if node hasn't been added twice in the first place, it 
> won't be processed twice**


<br>

---
### **Alogrithms to be used**

> Dfs (use visited) <br>
> Dfs with backtracking <br>
> Bfs (use visited) <br>
> Bfs with pruning (uses dist arr but not visited) <br>
> Topo sort (dfs with color 0, 1, 2) <br>
> Union find <br>

---
<br>
### `Directed`

> [traversal/find_count]

Evaluate Division
Keys and Rooms
Clone Graph
Find All Possible Recipes from Given Supplies

> [path]

All Paths From Source to Target (dfs w/ backtracking)

> [order]

1. Course schedule 1, 2
1. Find Eventual Safe States (dfs with safe and unsafe sets/reverse Kahn)
1. Reconstruct Itinerary
1. Alien Dictionary
1. All Ancestors of a Node in a Directed Acyclic Graph (reverse graph)

<br>
> [time/path]

1. Network Delay Time
1. Cheapest Flights Within K Stops
1. Time Needed to Inform All Employees

---

### **Undirected**

> [component] [Union find]

1. Number of Provinces
1. Number of Connected Components in an Undirected Graph
1. Number of Complete Components
1. Redundant Connection
1. Graph Valid Tree
1. Number of Operations to Make Network Connected

<br>

> [time]


> [order/traversal]

<br>

**exceptions/tricky**
1. Is Graph Bipartite? (bfs)
1. Minimum Cost to Reach City With Discounts (bfs with pruning with dp)
1. Critical Connections in a Network (tarjan or remove edge with dfs)
1. Tree Diameter (dfs)

<br>

---
### **Grid**
<br>

> [order/traversal]
1. All paths from source to target
1. Pacific Atlantic water flow

<br>

> **time/path**
Rotting oranges

<br>

---

**Tricky/Overlapping**

1. Minimize Malware Spread
1. Find Minimum Time to Reach Last Room II
1. Detonate the Maximum Bombs

---
*_`BFS points`_*
<br>
1. why queue size is maintained in matrix bfs but not edge bfs?

[Graph BFS] (general edges):
> distance is tracked via dist[] arr
> If only reachability/traversal needed → no queue size tracking.
> If shortest path needed → use dist[] array, not always queue size.

[Grid BFS]
> focus is on levels
> Much more common to ask for minimum steps from source to target.
> Queue size is the easiest way to separate levels → naturally counts steps.