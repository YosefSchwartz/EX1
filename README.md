# EX1
## Undricetional weighted graphs

**This exercise is in OOP course at Ariel University, we build some classes from given _interfaces._**
* ***WGraph_DS*** represent weighted graph with some functions to get data.    
                         
* ***WGraph_Algo*** represent some functions about weighted graph.  

#### Data Structure  
* _graph_ - My graph represent by `HashMap` that contain integer that present node key at key, and `node_info` at value.  
* _neighbors_ - present neighbors of each node, add all this data to double `HashMap` the first contain integer that present node key at key field, and at value field i use one more `HashMap` for each node to present his neighbors, the second `HashMap` contain `node_indo` at key field, and `double` at value.

#### Known algorithms
***dijkstra algorithm*** - On my project i use in this algorithm to get _shortestPath_ and later i use this function to get _shortestPathDist_.  
if the path is valid, this algorithm scan all nodes in graph that connected to source node until destintion node are marked.  
It use `Priority queue` that get first the node with lowest total weight, and for each node he touch he check if need to replace path or not (if total weight lower that what he have).

#### Tests
My tests uses `Junit 5`, can download from: [https://search.maven.org/search?q=g:org.junit.jupiter%20AND%20v:5.7.0]

###### important function  
* _WGraph_DS./connect(node1,node2)_ - Take two nodes and connect them, if this edge already exist, update his weight (if need).  
* _WGraph_DS./removeNode(key)_ - Remove this node from the graph, initally remove all edges are connect to this node, and finally remove this node from graph.  
* _WGraph_Algo./Copy()_ - Return a deep copy of this graph. (Note: According to our trainers, `ModeCounter` souldn't be the same! if in the previous graph performed some changes like add and remove nodes or edges, in the copy should be `ModeCounter` that present his buliding).  
* _WGraph_Algo./isConnected()_ - Boolean function, check if can go from some node to every else node in graph, use dijkstra algorithm.  
* _WGraph_Algo./shortestPath(src,dest)_ - Return `List` that present the way from source node to destination node, use dijkstra algorithm, and i store the way in `HashMap` that present node in the key field, and the node that is better to get from in value field, considering the path we search for. after get the data, i restore the way from this `HashMap` and insert the path to `Stack` (because it the opposite way, and to restore succesfully we need LIFO structre), and then insert the path at correct side to `List`.  
* _WGraph_Algo./save(file)_ * - Use `StringBulider` to insert the data to text file at Desktop.
* _WGraph_Algo./load(file)_ * - If path and text are valid, this function also init the graph to graph that loaded from text file.
