package maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DFS_MazeSolver 
{
	private static final int NORTH = 0;
	private static final int EAST = 1;
	private static final int SOUTH = 2;
	private static final int WEST = 3;
	
	private ArrayList<Cell> dfsCellTracker;
	private ArrayList<Cell> dfsCellPathTracker;
	
	private int dfsPathTracker;
	private int visitedCells;
	
	/*
	 * Constructor for a DFS_MazeSovler object
	 * 
	 */
	public DFS_MazeSolver()
	{
		
		dfsCellTracker = new ArrayList<Cell>();
		dfsCellPathTracker = new ArrayList<Cell>();
		dfsPathTracker = -1;
		visitedCells = 0;
	}
	
	/*
	 * Solves the given maze using DFS 
	 * 
	 * @param n The n x n size of the maze 
	 * 
	 */
	public void dfsSolution(int n)
	{
		GridGenerator g = new GridGenerator();
		MazeGenerator m = new MazeGenerator(n);
		
		Cell[][] grid = g.createGrid(n);
		Cell[][] maze = m.createMaze(grid);
		Graph g2 = new Graph(maze);
		
		//setting start and end cells
		Cell start = maze[0][0];
		Cell end = maze[maze.length - 1][maze[0].length - 1];
		
		maze[start.getX()][start.getY()].getWalls()[NORTH] = true; //opens north wall of start
		maze[end.getX()][end.getY()].getWalls()[SOUTH] = true; //opens south wall of end
		
		dfsVisit(start, maze, end); //starts the DFS Solution 
	}
	
	/*
	 * Visits a cell and calls DFS visit on all of it's neighbors
	 * 
	 * @param c The current cell that is being visited
	 * @param m The current maze that is being solved
	 * @param end The ending cell 
	 * 
	 */
	public void dfsVisit(Cell c, Cell[][] m, Cell end)
	{
		if(dfsCellTracker.contains(c) == false)
		{
			dfsCellTracker.add(c); //adds the current cell to the cells visited
		}
		
		c.setColor("Gray"); //sets the current cell to gray (visited)
	
		if(c.getX() == end.getX() && c.getY() == end.getY()) //reached the ending cell
		{
			ArrayList<Cell> path = getPath(c); //gets the path from the start to end
			Collections.reverse(path);
			dfsCellPathTracker = path;
			
			System.out.println();
					
			printDFSPath(m);
			System.out.println();
			printDFSShort(m);
			
		    System.out.println("Path Length: " + path.size());
			for(Cell c2 : path)
			{
				System.out.print("(" + c2.getX() + "," + c2.getY() + ")" + " ");
			}
			System.out.println();
			
			return; //stops the program
		}
		
		c.getAdjacent(m); //gets all the adjacent cell);
		for(Cell adjacent : c.getAdjacencyList())
		{
			if(adjacent.getColor().equalsIgnoreCase("White")) //has not been visited 
			{
				adjacent.setParent(c); //sets the parent to c
				dfsVisit(adjacent, m, end); //calls dfsVisit on the adjacent node, searching for the end
			}
		}
		c.setColor("Black"); //after all the recursive calls have finished, c has been fully explored
	}
	
	/*
	 * Gets the shortest path represented as the cells by backtracking from 
	 * the end until null, the parent of the starting node
	 * 
	 * @return the shortest path represented as the cells
	 * 
	 */
	public ArrayList<Cell> getPath(Cell end)
	{
		ArrayList<Cell> solutionPath = new ArrayList<Cell>();
		while(end != null)
		{
			solutionPath.add(end);
			end = end.getParent();
		}
		return solutionPath;
	}

	/*
	 * Prints the DFS path
	 * 
	 */
	public void printDFSPath(Cell[][] maze)
	{
		for(int x = 0; x < maze.length; x++)
		{
			Cell[] row = maze[x];
		     displayDFSTop(row);
		     displayDFSMiddlePath(row);
		    if(x == maze.length - 1)
		    {
		    	displayDFSBottom(row);
		    }
		    
		}
	}
	
	/*
	 * Prints the DFS path solution
	 * 
	 */
	public void printDFSShort(Cell[][] maze)
	{
		for(int x = 0; x < maze.length; x++)
		{
			Cell[] row = maze[x];
		    displayDFSTop(row);
		    displayDFSMiddlePathSolution(row);
		    if(x == maze.length - 1)
		    {
		       displayDFSBottom(row);
		    }
		    
		}
	}
	
	/*
	 * Displays top of a cell row
	 * 
	 */
	public void displayDFSTop(Cell[] row)
	{
		for(int x = 0; x < row.length; x++)
		{
			Cell current = row[x];
			if(current.getWalls()[NORTH] == true)
			{
				System.out.print("+ ");
			}
			else
			{
				System.out.print("+-");
			}
		}
		System.out.println("+");
	}
	
	/*
	 * Displays middle of a cell row
	 * 
	 */
	public void displayDFSMiddlePath(Cell[] row)
	{		
		for(int x = 0; x < row.length; x++)
		{
			Cell current = row[x];
			if(dfsCellTracker.contains(current))
			{
				
				dfsPathTracker = 0;
				if(dfsCellTracker.indexOf(current) > 9) //cell has visited over 10 cells
				{
					dfsPathTracker = -1; //set to -1 because it has to increase regardless, need to preserve the 0 starting value
					Cell temp = dfsCellTracker.get(9); //get the 10th cell
					int index = 9; //current  index of the temp cell
					while(temp.getX() != current.getX() || temp.getY() != current.getY()) //while it hasn't reached the desired cell
					{	
						if(temp.getX() == current.getX() && temp.getY() == current.getY()) //got to the right cell
						{
							break;
						}
						dfsPathTracker++; //path tracker increases
						index++; //index increases
						if(dfsPathTracker > 9) //went over 9 again, reset to 0
						{
							dfsPathTracker = 0;	
						}
						temp = dfsCellTracker.get(index); //temp is now the next element in the cell list
					}
				}
				else
				{
					dfsPathTracker = dfsCellTracker.indexOf(current);
				}
				
				
				
				if(current.getWalls()[WEST] == true)
				{
					System.out.print(" " + dfsPathTracker);
					visitedCells++;
				}
				else
				{
					System.out.print("|" + dfsPathTracker);
					visitedCells++;
				}
				
				if(x == row.length - 1)
				{
					if(current.getWalls()[EAST] == true)
					{
						System.out.print(" ");
					}
					else
					{
						System.out.print("|");
					}
				}
				
			}
			else //current was not visited at all during BFS exploration
			{
				if(current.getWalls()[WEST] == true)
				{
					System.out.print("  ");
				}
				else
				{
					System.out.print("| ");
				}
				
				if(x == row.length - 1)
				{
					if(current.getWalls()[EAST] == true)
					{
						System.out.print(" ");
					}
					else
					{
						System.out.print("|");
					}
				}
			}	
		}
		System.out.println();
	}
	
	/*
	 * Displays middle of a cell row
	 * 
	 */
	public void displayDFSMiddlePathSolution(Cell[] row)
	{		
		for(int x = 0; x < row.length; x++)
		{
			Cell current = row[x];
			if(dfsCellPathTracker.contains(current)) //current cell was part of the shortest path
			{
				if(current.getWalls()[WEST] == true)
				{
					System.out.print(" " + "#");
				}
				else
				{
					System.out.print("|" + "#");
				}
				
				if(x == row.length - 1)
				{
					if(current.getWalls()[EAST] == true)
					{
						System.out.print(" ");
					}
					else
					{
						System.out.print("|");
					}
				}
				
			}
			else //current was not visited at all during BFS exploration
			{
				if(current.getWalls()[WEST] == true)
				{
					System.out.print("  ");
				}
				else
				{
					System.out.print("| ");
				}
				
				if(x == row.length - 1)
				{
					if(current.getWalls()[EAST] == true)
					{
						System.out.print(" ");
					}
					else
					{
						System.out.print("|");
					}
				}
			}	
		}
		System.out.println();
	}
	
	/*
	 * Displays bottom of a cell row
	 * 
	 */
	public void displayDFSBottom(Cell[] row)
	{
		for(int x = 0; x < row.length; x++)
		{
			Cell current = row[x];
			if(current.getWalls()[SOUTH] == true)
			{
				System.out.print("+ ");
			}
			else
			{
				System.out.print("+-");
			}	
		}
		System.out.println("+");
	}
	
	/*
	 * Gets the cell tracker
	 * 
	 */
	public ArrayList<Cell> getCellTracker()
	{
		return dfsCellTracker;
	}
	
	/*
	 * Gets the cell tracker
	 * 
	 */
	public ArrayList<Cell> getCellPathTracker()
	{
		return dfsCellPathTracker;
	}
	
}
