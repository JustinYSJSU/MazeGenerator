package maze;

import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator 
{	
	private static final int NORTH = 0;
	private static final int EAST = 1;
	private static final int SOUTH = 2;
	private static final int WEST = 3;
	
	private Random r;
	
	/*
	 * Creates a MazeGenerator object
	 * 
	 * @param seed The seed for the maze
	 * 
	 */
	public MazeGenerator(int seed)
	{
		r = new Random(seed);
	}
	        
	/*
	 * Generates a maze given a 2D Array (grid) of Cells
	 * 
	 * @return the 2D Array that has been converted to a maze
	 * 
	 */
	public Cell[][] createMaze(Cell[][] c)
	{
	
		Stack<Cell> cellStack = new Stack<Cell>(); //creates a stack of cell positions
		int totalCellCount = (int) Math.pow(c.length, 2); //total cells = n * n
		Cell currentCell = c[0][0]; //select starting cell at 0,0
		int visitedCellCount = 1; //1 since we start at a cell
		
		while(visitedCellCount < totalCellCount)
		{
			 HashSet<Cell> h = currentCell.getNeighbors(c); //gets all neighbors of the current cell	 
			 if(h.size() > 0) //the cell has neighbors that still have walls
			 {
				Cell[] allNeighbors = h.toArray(new Cell[h.size()]); //converts hash set to array to get a random neighbor
				
				int random = r.nextInt(h.size()); 
				Cell randomNeighbor = allNeighbors[random]; //random neighbor of the current cell 
				int directionRelationship = currentCell.identifyDirectionRelationship(randomNeighbor); //get the direction of the neighbor: north, east, west, or south
				currentCell.removeWall(directionRelationship, randomNeighbor); //remove the appropriate walls depending on the direction of the neighbor
				h.remove(randomNeighbor); //random neighbor is no longer a true neighbor, remove it for backtracking later 
				
				cellStack.push(currentCell); //push currentCell
				currentCell = randomNeighbor;
				visitedCellCount++; //increase visited cell count
			 }
			 else //no cells with the correct wall configuration 
			 {
				 Cell popped = cellStack.pop(); //push currentCell
			     currentCell = popped;
			 }
		}
		return c;
	}
	
	/*
	 * Generates a completed maze in ASCII form
	 * 
	 * @param maze The completed maze
	 * 
	 */
	public void displayMaze(Cell[][] maze)
	{
		for(int x = 0; x < maze.length; x++)
		{
			Cell[] row = maze[x];
		    displayTop(row);
		    displayMiddle(row);
		    if(x == maze.length - 1)
		    {
		    	displayBottom(row);
		    }
		    
		}
	}
	
	public void displayBottom(Cell[] row)
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
	
	public void displayMiddle(Cell[] row)
	{
		for(int x = 0; x < row.length; x++)
		{
			Cell current = row[x];
			
			
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
		System.out.println();
	}
	
	public void displayTop(Cell[] row)
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
	
	public Random getRandom()
	{
		return this.r;
	}
}
