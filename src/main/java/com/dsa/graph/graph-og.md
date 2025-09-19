
**Check conditions**

Grid
-> no union find
dfs (w/ visited)
bfs

-> with edge weights (adj matrix), else adj list
-> shortest time/path (BFS w/ pruning)
-> order/loop (Topo, Dfs w/ coloring / Kahn's)
-> traversal/count (Dfs)
-> If no edge weights, prefer dfs

Undirected
-> loop/components (union find)
-> bipartite (bfs/union find)
-> count (dfs)

**IMP** in bfs, [size] needs to be used for grid

**Alogrithms to be used**
> Dfs (use visited) <br>
> Dfs with backtracking <br>
> Bfs (use visited) <br>
> Bfs with pruning (uses dist arr but not visited) <br>
> Topo sort (dfs with color 0, 1, 2) <br>
> Union find <br>

**Directed**

-- [traversal/find_count]
Evaluate Division
Keys and Rooms
Clone Graph
Find All Possible Recipes from Given Supplies

-- [path]
All Paths From Source to Target (dfs w/ backtracking)

-- [order]
Course schedule 1, 2
Find Eventual Safe States (dfs with safe and unsafe sets/reverse Kahn)
Reconstruct Itinerary
Alien Dictionary
All Ancestors of a Node in a Directed Acyclic Graph (reverse graph)


-- [time/path]
Network Delay Time
Cheapest Flights Within K Stops
Time Needed to Inform All Employees

+++++++++++++++++++++++++++++++++++++++++

**Undirected**

-- [component]
Number of Provinces
Number of Connected Components in an Undirected Graph
Number of Complete Components
Redundant Connection
Graph Valid Tree
Number of Operations to Make Network Connected

-- [time]


-- [order/traversal]

**exceptions/tricky**
Is Graph Bipartite? (bfs)
Minimum Cost to Reach City With Discounts (bfs with pruning with dp)
Critical Connections in a Network (tarjan or remove edge with dfs)
Tree Diameter (dfs)

**Grid**

-- [order/traversal]
All paths from source to target

-- **time/path**
Rotting oranges

__________________________________________________
**Tricky/Overlapping**

Minimize Malware Spread
Find Minimum Time to Reach Last Room II
Detonate the Maximum Bombs

---------------------------------------------------
BFS points
why queue size is maintained in matrix bfs but not edge bfs?

[Graph BFS] (general edges):
> distance is tracked via dist[] arr
> If only reachability/traversal needed → no queue size tracking.
> If shortest path needed → use dist[] array, not always queue size.

[Grid BFS]
> focus is on levels
> Much more common to ask for minimum steps from source to target.
> Queue size is the easiest way to separate levels → naturally counts steps.