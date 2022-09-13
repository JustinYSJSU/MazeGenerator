package maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BFS_MazeSolver 
{
	private static final int NORTH = 0;
	private static final int EAST = 1;
	private static final int SOUTH = 2;
	private static final int WEST = 3;
	
	private ArrayList<Cell> bfsCellTracker;
	private ArrayList<Cell> bfsCellPathTracker;
	private int visitedCells;
	
	private int bfsPathTracker;
	
	public BFS_MazeSolver()
	{
		bfsCellPathTracker = new ArrayList<Cell>();
		bfsCellTracker = new ArrayList<Cell>();
		bfsPathTracker = -1;
		visitedCells = 0;
	}
	
	/*
	 * Solves the maze using BFS
	 * 
	 * @param n The n x n size of the maze
	 * 
	 */
	public void bfsSolution(int n)
	{
		//displaying the initial maze
		GridGenerator g = new GridGenerator();
		MazeGenerator m = new MazeGenerator(n);
		
		
		Cell[][] grid = g.createGrid(n);
		Cell[][] maze = m.createMaze(grid);
		Graph g2 = new Graph(maze); //Graph representation
		
		Cell start = maze[0][0];
		Cell end = maze[maze.length - 1][maze[0].length - 1];
		
		maze[start.getX()][start.getY()].getWalls()[NORTH] = true;
		maze[end.getX()][end.getY()].getWalls()[SOUTH] = true;
			
		//actually solving the maze using BFS 
		Queue<Cell> cellQ = new LinkedList<>(); //creating Cell Queue
		Cell current = start; //current cell beings at the starting cell
		current.setColor("Gray");
	    current.setParent(null);
	    
		bfsCellPathTracker.add(current);
	    bfsCellTracker.add(current); //adds starting cell to the list of cells visited
		cellQ.add(start); //adds current cell to the queue
		
		while(cellQ.isEmpty() == false) //cell queue is not empty
		{
			current = cellQ.remove(); //current is set to the cell that was removed 
			
			if(current.getX() == end.getX() && current.getY() == end.getY()) //ending cell has been reached
			{
				ArrayList<Cell> path = getPath(current); //get the path through the maze
				Collections.reverse(path);
				bfsCellPathTracker = path;
				
				System.out.println();
				
				if(bfsCellTracker.contains(current) == false)
				{
					bfsCellTracker.add(current);					
				}
				
				printBFSPath(maze); //print the cells visited
				System.out.println();
				printBFSShort(maze); //print the shortest path
				
			    System.out.println("Path Length: " + path.size()); //length of the path
				for(Cell c : path)
				{
					System.out.print("(" + c.getX() + "," + c.getY() + ")" + " ");
				}
				System.out.println();
				System.out.println("Cells Visited: " + this.visitedCells); //visited cells 
				break;
			}
					
			current.getAdjacent(maze);
			for(Cell c : current.getAdjacencyList()) //for all the cells in a given adjacency list
			{	
				if(c.getColor().equalsIgnoreCase("White")) //add to the queue if it was white (unexplored)
				{
					c.setColor("Gray");
					c.setParent(current);
					cellQ.add(c);
				}
			} 
			current.setColor("Black");
			
			if(bfsCellTracker.contains(current) == false) //avoid adding duplicates 
			{
				bfsCellTracker.add(current);					
			}
		}
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
	 * Prints the BFS path
	 * 
	 */
	public void printBFSPath(Cell[][] maze)
	{
		for(int x = 0; x < maze.length; x++)
		{
			Cell[] row = maze[x];
		    displayBFSTop(row);
		    displayBFSMiddlePath(row);
		    if(x == maze.length - 1)
		    {
		    	displayBFSBottom(row);
		    }
		    
		}
	}
	
	/*
	 * Prints the BFS shortest path solution
	 * 
	 */
	public void printBFSShort(Cell[][] maze)
	{
		for(int x = 0; x < maze.length; x++)
		{
			Cell[] row = maze[x];
		    displayBFSTop(row);
		    displayBFSMiddlePathSolution(row);
		    if(x == maze.length - 1)
		    {
		    	displayBFSBottom(row);
		    }
		    
		}
	}
	/*
	 * Displays top of a cell row
	 * 
	 */
	public void displayBFSTop(Cell[] row)
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
	public void displayBFSMiddlePath(Cell[] row)
	{		
		for(int x = 0; x < row.length; x++)
		{
			Cell current = row[x];
			if(bfsCellTracker.contains(current))
			{
				
				bfsPathTracker = 0;
				if(bfsCellTracker.indexOf(current) > 9) //cell has visited over 10 cells
				{
					bfsPathTracker = -1; //set to -1 because it has to increase regardless, need to preserve the 0 starting value
					Cell temp = bfsCellTracker.get(9); //get the 10th cell
					int index = 9; //current  index of the temp cell
					while(temp.getX() != current.getX() || temp.getY() != current.getY()) //while it hasn't reached the desired cell
					{	
						if(temp.getX() == current.getX() && temp.getY() == current.getY()) //got to the right cell
						{
							break;
						}
						bfsPathTracker++; //path tracker increases
						index++; //index increases
						if(bfsPathTracker > 9) //went over 9 again, reset to 0
						{
							bfsPathTracker = 0;	
						}
						temp = bfsCellTracker.get(index); //temp is now the next element in the cell list
					}
				}
				else
				{
					bfsPathTracker = bfsCellTracker.indexOf(current);
				}
				
				
				
				if(current.getWalls()[WEST] == true)
				{
					System.out.print(" " + bfsPathTracker);
					visitedCells++;
				}
				else
				{
					System.out.print("|" + bfsPathTracker);
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
	public void displayBFSMiddlePathSolution(Cell[] row)
	{		
		for(int x = 0; x < row.length; x++)
		{
			Cell current = row[x];
			if(bfsCellPathTracker.contains(current)) //current cell was part of the shortest path
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
	public void displayBFSBottom(Cell[] row)
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
		return bfsCellTracker;
	}
	
	/*
	 * Gets the cell tracker
	 * 
	 */
	public ArrayList<Cell> getCellPathTracker()
	{
		return bfsCellPathTracker;
	}
}
