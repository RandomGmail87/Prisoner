package prisoner;
import java.util.Scanner;
public class Prisoner {
public static int gMAX_CENTERS = 12;
public static Scanner gSCANNER = new Scanner(System.in);    //initializing public variables
public static int[] gPrisoner = new int [gMAX_CENTERS];
public static String error = "ERROR, you need to eneter a valid value based on the next message.";  // making strings for quick access
public static String options = "\nMAIN MENU\n0 - Clear centers, 1 - List centers, 2 - Add/Subtract prisoners, 3 - Add new center, 4 - Center analysis, 5 - Transfer prisoners, 6 - Exit\nSelect an option: ";
	public static void main(String[] args) {
		System.out.println("Spring 2024 - UTSA - CS1083 - Section 004 - Project 2 - Prisoner - written by Garrett Christal\n");
		
		System.out.println("Please, enter the initial number of detention centers in the database (MAX 12): ");
		int gCenterCounter = gSCANNER.nextInt();
		
		while(gCenterCounter < 0 || gCenterCounter > gMAX_CENTERS) {
			System.out.println(error);
			System.out.println("Please, enter the initial number of detention centers in the database (MAX 12): ");			//How many centers there will be
			gCenterCounter = gSCANNER.nextInt();
		}
		while(true) {
		System.out.println(options);
		int choice = gSCANNER.nextInt();
		
		while(true) {									
			if (choice >= 0 && choice <= 6) {
				if(choice == 0) {
					clear(gCenterCounter);								//choice selection calling respective methods
					break;
				}else if(choice == 1) {
					list(gCenterCounter);
					break;
				}else if(choice == 2) {
					addSub(gCenterCounter);
					break;
				}else if(choice == 3) {
					int newCenterPrisoners = append(gCenterCounter);
				    if(newCenterPrisoners > 0 && gCenterCounter < gMAX_CENTERS) {
				        gPrisoner[gCenterCounter++] = newCenterPrisoners;
				    }
					break;
				}else if(choice == 4) {
					analysis(gCenterCounter);
					break;
				}else if(choice == 5) {
					transfer(gCenterCounter);
					break;
				}else if(choice == 6) {
					System.out.println("Logging out...Systems shuting down...Have a good day administrator");  				//"logging off"
					System.exit(0);
				}
			} else {
				System.out.println(error + "\n" + options);
				choice = gSCANNER.nextInt();
				continue;
			}
		}
		gSCANNER.close();
	}
}
	public static void clear(int gCenterCounter) {
		System.out.println("**MASS RELOCATION INITIATED**");
		for(int i = 0; i < gCenterCounter; ++i) {
			gPrisoner[i] = 0;
			System.out.println("Center[" + i + "] has \"relocated\" the prisoners");				//clearing all array elements
		}
		System.out.println("--Relocation protocol complete--");
	}
	public static void list(int gCenterCounter) {
		System.out.println("List of Prisoner Detention Center Occupancy");					//looping out array
		for(int i = 0; i < gCenterCounter; ++i) {
			System.out.println("Center[" + i + "] : " + gPrisoner[i]);
		}
}
	public static void addSub(int gCenterCounter) {
		int minimum = 0;
		System.out.println("Enter the index (0 to " + (gCenterCounter - 1) + ")");
		int index = gSCANNER.nextInt();
			
		while(index < 0 || index > gCenterCounter -1) {
			System.out.println(error);
			System.out.println("Enter the index (0 to " + (gCenterCounter - 1) + ") :");
			index = gSCANNER.nextInt();
		}
		
		System.out.println("The current occupancy of the center at index " + index + " is : " + gPrisoner[index]);		//adding or subtracting from array 
		System.out.println("Enter the number of prisoners in this operation (0 - 200) : ");
		int quantity = gSCANNER.nextInt();
			
		while(quantity < 0 || quantity > 200) {
			System.out.println(error);
			System.out.println("Enter the number of prisoners in this operation (0 - 200) : ");
			quantity = gSCANNER.nextInt();
		}
		gSCANNER.nextLine();
		System.out.println("Are the prisoners added to the center at index " + index + "? (Y/N) : ");
		String add = gSCANNER.nextLine();
		while(!add.equals("Y") && !add.equals("N")){
			System.out.println(error + "\nAre the prisoners added to the center at index \" + index + \"? (Y/N) : ");		//methods calculating out choices continued...
			add = gSCANNER.nextLine();
		}
		if(add.equals("Y")) {
			gPrisoner[index] += quantity;
			System.out.println("Inamtes enroute, expect Center[" + index + "]'s occupancy to be at " + gPrisoner[index] +"\n... back to menu");
		}else if(add.equals("N")) {
			minimum = (gPrisoner[index] - quantity);
			if(minimum < 0) {
				System.out.println("We cant have negative prisoners... I dont think?...try again");
				return;
			}
			gPrisoner[index] -= quantity;
			System.out.println(quantity + " prisoners \"relocated\"\n... back to menu");
		}
	}
	public static int append(int preSize) {
		if(preSize > 11) {
			System.out.println("The database is full, no more centers can be added");
			return 0;
		}
		System.out.println("Enter the number of prisoners to assign to the new center (0 - 200) : ");		//quick add a new center
		int quantity = gSCANNER.nextInt();
		while(quantity < 0 || quantity > 200) {
			System.out.println(error);
			System.out.println("Enter the number of prisoners to assign to the new center (0 - 200) : ");
			quantity = gSCANNER.nextInt();
		}
		return quantity;
	}
	public static void analysis(int gCenterCounter) {
	String[] classification = {"Under capacity\t\t: ","Just right\t\t: ","Close to over capacity\t: ","Full\t\t\t: ","Over capacity\t\t: "};
	System.out.println("Occupancy Classification Summary");
	for(int i = 0; i < 5; ++i) {
		System.out.println(classification[i] + getPrisonersByClass(gCenterCounter, i));		//categorizes each center by population
	}
	}
	public static int getPrisonersByClass(int length, int classiNum) {

		int count = 0;
		if(classiNum == 0) {
			for(int j = 0; j < length; ++j) {
				if(gPrisoner[j] <= 50 && gPrisoner[j] >= 0) {
					++count;
				}
			}
		}
		if(classiNum == 1) {
			for(int j = 0; j < length; ++j) {
				if(gPrisoner[j] <= 150 && gPrisoner[j] >= 51) {				//secondary method to analysis, cycles through classifications
					++count;
				}
			}
		}if(classiNum == 2) {
			for(int j = 0; j < length; ++j) {
				if(gPrisoner[j] < 200 && gPrisoner[j] >= 151) {
					++count;
				}
			}
		}if(classiNum == 3) {
			for(int j = 0; j < length; ++j) {
				if(gPrisoner[j] == 200) {
					++count;
				}
			}
		}if(classiNum == 4) {
			for(int j = 0; j < length; ++j) {
				if(gPrisoner[j] > 200) {
					++count;
				}
			}
		}
		return count;
	}
	public static void transfer(int sizeLength) {
		
		System.out.println("Enter the center where the prisoners will be transferred from (0 to " + (sizeLength - 1) + ")");
		
		int idxFrom = gSCANNER.nextInt();
		while(idxFrom < 0 || idxFrom >= sizeLength) {
			System.out.println(error);
			System.out.println("Enter the center where the prisoners will be transferred from (0 to " + (sizeLength - 1) + ")");
			idxFrom = gSCANNER.nextInt();
		}
		System.out.println("The current occupancy of the center at index " + idxFrom + " is : " + gPrisoner[idxFrom]);
		
		System.out.println("Enter the number of prisoners to transfer to the other center (0 - " + gPrisoner[idxFrom] + ") :");			//extra credit: transfer prisoners between centers
		int move = gSCANNER.nextInt();
		while(move > gPrisoner[idxFrom]) {
			System.out.println("Not enough prisoners to move");
			System.out.println("Enter the number of prisoners to transfer to the other center (0 - " + gPrisoner[idxFrom] + ") :");
			move = gSCANNER.nextInt();
		}
		
		System.out.println("Enter the center where the prisoners will be transferred to (0 - " + gPrisoner[idxFrom] + ") that is not " + idxFrom + " :");
		int target = gSCANNER.nextInt();
		while(idxFrom == target || target < 0 || target >= sizeLength) {
			System.out.println(error);
			System.out.println("Enter the center where the prisoners will be transferred to (0 - " + gPrisoner[idxFrom] + ") that is not " + idxFrom + " :");
			idxFrom = gSCANNER.nextInt();
		}
		gPrisoner[idxFrom] -= move;
		gPrisoner[target] += move;
		System.out.println("Transfer complete");
	}
}