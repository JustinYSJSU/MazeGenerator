package maze;

//Generates a graph
public class Graph
{
	private Cell vertexList[]; //array of all cell vertices
	private int totalVertNumber; //total number of vertices 	
	private int currentVertNumber;
	private Cell[][] m;
	
	/*
	 * Constructor for a Graph
	 * 
	 * @param maze The generated maze
	 * 
	 */
	public Graph(Cell[][] maze)
	{
		m = maze;
		totalVertNumber = maze.length * maze[0].length;
		currentVertNumber = 0;
		vertexList = new Cell[totalVertNumber];
		for(int x = 0; x < maze.length; x++)
		{
			for(int y = 0; y < maze[0].length; y++)
			{
				this.addVertix(maze[x][y]);
			}
		}
		
		for(int i = 0; i < vertexList.length; i++)
		{
			Cell c = vertexList[i];
			this.addEdge(c);
		}
	}
	
	/*
	 * Add a vertex to the maze 
	 * 
	 * @param Cell c the vertex to be added
	 * 
	 */
	public void addVertix(Cell c)
	{
		vertexList[currentVertNumber] = c;
		currentVertNumber++;
	}
	
	/*
	 * Add an edge between to cells
	 * 
	 * @param c The cell that has an edge
	 * 
	 */
	public void addEdge(Cell c)
	{
		c.getAdjacent(m);
		for(int x = 0; x < m.length; x++)
		{
			for(int y = 0; y < m[0].length; y++)
			{
				for(Cell adjacent : c.getAdjacencyHolder())
				{
					if(c.getAdjacencyList().contains(adjacent) == false) //add to one adjacency list
					{
						c.getAdjacencyList().add(adjacent);
					}
					
					if(adjacent.getAdjacencyList().contains(c) == false) //also add to the other
					{
						adjacent.getAdjacencyList().add(c);
					}
				}
			}
		}
	}
	
	

}
