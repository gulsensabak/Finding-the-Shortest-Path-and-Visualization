import java.io.*;
import java.util.*;
public class FindingTheShortestPath {
	private static final int pauseDuration = 260;
	public static void main(String[] args) {
		int canvas_width = 1000;
		int canvas_height = 1000;
		StdDraw.setCanvasSize(canvas_width,canvas_height);
		StdDraw.setXscale(0,100);
		StdDraw.setYscale(0,100);
		StdDraw.enableDoubleBuffering();
		pathFinder("coordinates3.txt");
	}


	public static double distanceFinder(int x1, int y1, int x2, int y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	public static int pathFinder(String filename) {

		/* Find the smallestTotalDistance */
		double smallestTotalDistance = 0;

		//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

		//To read txt file lines.
		try {
			Scanner scanner= new Scanner(new File(filename));

			//Creating house objects and adding them to the multiFunctionArrayList.
			ArrayList<House> multiFunctionArrayList= new ArrayList<>();
			while(scanner.hasNextLine()) {
				String[] data = scanner.nextLine().split(" ");
				House myHouse= new House(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]));
				// I add the House objects to arraylitemArrayList.
				multiFunctionArrayList.add(myHouse);
			}

			//line işlemleri
			ArrayList<Double> countArrayList= new ArrayList<>();
			ArrayList<String> nameOfCoordinates= new ArrayList<>();
			ArrayList<ArrayList<House>> denemeArrayList=new ArrayList<>();

			for(ArrayList<House> m: permute(multiFunctionArrayList)) {	
				denemeArrayList.add(m);
				for(int i=0; i< m.size();i++) {
					if(i<m.size()-1) {
						double distance=  distanceFinder(m.get(i).getX(), m.get(i).getY(),
								m.get(i+1).getX(),m.get(i+1).getY());
						smallestTotalDistance+=distance;
					}
					if(i==m.size()-1) {
						double distance2=  distanceFinder(m.get(i).getX(), m.get(i).getY(),
								m.get(0).getX(),m.get(0).getY());
						smallestTotalDistance+=distance2;
					}
				}

				countArrayList.add(smallestTotalDistance);
				smallestTotalDistance=0;

			}

			smallestTotalDistance= countArrayList.get(0);
			int counter=0;
			for(int l=0; l<countArrayList.size();l++) {
				if(countArrayList.get(l)<smallestTotalDistance) {
					smallestTotalDistance=countArrayList.get(l);
					counter++;
				}
			}	
			ArrayList<House> sonPath=permute(multiFunctionArrayList).get(countArrayList.indexOf(smallestTotalDistance));

			for (int i = 0; i < multiFunctionArrayList.size(); i++) {
				if(i==0) {
					StdDraw.setPenRadius(0.03);
					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.point(multiFunctionArrayList.get(i).getX(), multiFunctionArrayList.get(i).getY());
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.text(multiFunctionArrayList.get(i).getX(), multiFunctionArrayList.get(i).getY(), multiFunctionArrayList.get(i).getName().substring(0,2));
					StdDraw.show();					
				}
				else {
					StdDraw.setPenRadius(0.012);
					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.point(multiFunctionArrayList.get(i).getX(), multiFunctionArrayList.get(i).getY());
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.text(multiFunctionArrayList.get(i).getX(), multiFunctionArrayList.get(i).getY(), multiFunctionArrayList.get(i).getName());
					StdDraw.show();
				}
			}

			for(int i=0; i<sonPath.size(); i++) {
				if(i<sonPath.size()-1) {
					StdDraw.setPenColor(StdDraw.BOOK_RED);
					StdDraw.line(sonPath.get(i).getX(), sonPath.get(i).getY(), sonPath.get(i+1).getX(), sonPath.get(i+1).getY());
					StdDraw.show();
					StdDraw.pause(pauseDuration);
				}
				if(i==sonPath.size()-1) {
					StdDraw.setPenColor(StdDraw.BOOK_RED);
					StdDraw.line(sonPath.get(i).getX(), sonPath.get(i).getY(), sonPath.get(0).getX(), sonPath.get(0).getY());
					StdDraw.show();
					StdDraw.pause(pauseDuration);

				}
			}






			scanner.close();

		}



		catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}


		int roundedValue = (int) Math.round(smallestTotalDistance);
		return roundedValue;

	}

	public static void toCreatePermutation(ArrayList<House> arrayListForHouseObjects, ArrayList<ArrayList<House>> permutations, List<House> result) {
		if (arrayListForHouseObjects.size() == result.size()) {
			ArrayList<House> temp = new ArrayList<>(result);
			permutations.add(temp);
		}        
		for (int i=0; i<arrayListForHouseObjects.size(); i++) {
			if (!result.contains(arrayListForHouseObjects.get(i))) {
				result.add(arrayListForHouseObjects.get(i));
				toCreatePermutation(arrayListForHouseObjects, permutations, result);
				result.remove(result.size() - 1);
			}
		}
	}
	//To create all permutations by helping the method of toCreatePermutation.
	public static ArrayList<ArrayList<House>> permute(ArrayList<House> houseObjectList) {
		ArrayList<ArrayList<House>> finalPermutations = new ArrayList<ArrayList<House>>();
		if (houseObjectList == null || houseObjectList.size() == 0) {
			return finalPermutations;
		}
		ArrayList<House> result = new ArrayList<>();
		toCreatePermutation(houseObjectList, finalPermutations, result);
		return finalPermutations;

	}
}