package maze;

public class GridGenerator 
{
	/*
	 * Constructs a GridGenerator object
	 * 
	 */
	public GridGenerator()
	{
		
	}
	
	/*
	 * Makes a 2D Array (grid) of cells of n x n size
	 * 
	 * @param n The size of the grid
	 * 
	 */
	public Cell[][] createGrid(int n)
	{
		Cell[][] grid = new Cell[n][n];
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid[0].length; y++)
			{
				Cell freshCell = new Cell(x,y); //new cell at x, y 
				grid[x][y] = freshCell;
			}
		}
		return grid;
	}

}
