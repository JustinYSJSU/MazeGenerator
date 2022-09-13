package maze;

//Runs both the BFS and DFS solving methods and saves the output in a text file

public class MainTester 
{
	public static void main(String[] args)
	{
        
		BFS_MazeSolver b = new BFS_MazeSolver();
		BFS_MazeSolver b2 = new BFS_MazeSolver();
		BFS_MazeSolver b3 = new BFS_MazeSolver();
		BFS_MazeSolver b4 = new BFS_MazeSolver();
		
		DFS_MazeSolver d = new DFS_MazeSolver();
		DFS_MazeSolver d2 = new DFS_MazeSolver();
		DFS_MazeSolver d3 = new DFS_MazeSolver();
		DFS_MazeSolver d4 = new DFS_MazeSolver();
		
		
		System.out.println("***SIZE: 4***");
		System.out.println("BFS SOLUTION");
		
		b.bfsSolution(4);
		System.out.println();
		
		System.out.println("DFS SOLUTION");
		d.dfsSolution(4);
		
		System.out.println("=================================================================");
		
		System.out.println("***SIZE: 5***");
        System.out.println("BFS SOLUTION");
		
        
		b.bfsSolution(5);
		System.out.println();
		
		System.out.println("DFS SOLUTION");
		d.dfsSolution(5);
		
		System.out.println("=================================================================");
		
		System.out.println("***SIZE: 6***");
		System.out.println("BFS SOLUTION");
		b2.bfsSolution(6);
		System.out.println();
		System.out.println("DFS SOLUTION");
		d2.dfsSolution(6);
		
		System.out.println("=================================================================");
		
		System.out.println("***SIZE: 7***");
		System.out.println("BFS SOLUTION");
		b2.bfsSolution(7);
		System.out.println();
		System.out.println("DFS SOLUTION");
		d2.dfsSolution(7);
		
		System.out.println("=================================================================");
		
		
		System.out.println("***SIZE: 8***");
		System.out.println("BFS SOLUTION");
		b3.bfsSolution(8);
		System.out.println();
		System.out.println("DFS SOLUTION");
		d3.dfsSolution(8);
		
		
		System.out.println("=================================================================");
		
		System.out.println("***SIZE: 9***");
		System.out.println("BFS SOLUTION");
		b3.bfsSolution(9);
		System.out.println();
		System.out.println("DFS SOLUTION");
		d3.dfsSolution(9);
		
		
		System.out.println("=================================================================");
		
		System.out.println("***SIZE: 10***");
		System.out.println("BFS SOLUTION");
		b4.bfsSolution(10);
		System.out.println();
		System.out.println("DFS SOLUTION");
		d4.dfsSolution(10);
		
		System.out.println("=================================================================");
	}

}
