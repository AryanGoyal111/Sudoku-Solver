import java.util.Scanner;
class LinkedSolver{
	node head=null;
	node head1=null;
	int status=0;
	int size=0;
	class node{
		node next=null;
		int pos1,pos2,row,col;
		node(int pos1,int pos2,int row,int col){
			this.pos1=pos1;
			this.pos2=pos2;
			this.row=row;
			this.col=col;
		}
	}
	void listFormate(int pos1,int pos2,int row,int col,int rescue){
		int ptr=0;
		node ele=new node(pos1,pos2,row,col);
		if(head==null)
			head=ele;
		else{
			node temp=head;
			while(temp.next!=null){
				if((temp.pos1==ele.pos1&&temp.pos2>ele.pos2)||(temp.pos1>ele.pos1)){
					if(temp==head){
						ele.next=temp;
						head=ele;
					}
					else{
						node temp1=head;
						while(temp1.next!=temp)
							temp1=temp1.next;
						temp1.next=ele;
						ele.next=temp;
					}
					ptr=1;
					break;
				}
				else
					temp=temp.next;
			}
			if(ptr==0){
				if((temp.pos1==ele.pos1&&temp.pos2>ele.pos2)||(temp.pos1>ele.pos1)){
					if(temp==head){
						ele.next=temp;
						head=ele;
					}
					else{
						node temp1=head;
						while(temp1.next!=temp)
							temp1=temp1.next;
						temp1.next=ele;
						ele.next=temp;
					}
				}
				else
					temp.next=ele;
			}
		}
		if(rescue!=0)
			SudokuPuzzle.sudoku[pos1][pos2][row][col]=rescue;
		size++;
	}
	void dataFeeder(int pos1,int pos2,int row,int col){
		node ele=new node(pos1,pos2,row,col);
		if(head1==null)
			head1=ele;
		else{
			node temp=head1;
			while(temp.next!=null)
				temp=temp.next;
			temp.next=ele;
		}
			
	}
	void showList(){
		node temp=head;
		while(temp!=null){
			System.out.println("Pos1: "+temp.pos1+"Pos2: "+temp.pos2+"row: "+temp.row+"Col: "+temp.col);
			temp=temp.next;
		}
	}
	void listFinder(int rescue){
		node temp=head;
		int ptr=0;
		outter:
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					if(temp.pos1==i&&temp.pos2==j){
						System.out.println("Pos1: "+temp.pos1+"Pos2: "+temp.pos2);
						if(temp.next!=null)
							temp=temp.next;
					}
					else{
						System.out.println("i: "+i+"j: "+j);
						ptr=taskManager(i,j,rescue);
					}
					if(ptr==1){
						status=1;
						listFinder(rescue);
						break outter;
					}
				}
			
		}
	}
	int taskManager(int rowhd,int colhd,int rescue){
		node temp=head,ext=head1;
		int i=-1,j=-1;
		int rows[]=new int[2],cols[]=new int[2];
		while(temp!=null){
			if(temp.pos1==rowhd&&temp.pos2!=colhd){
				i++; 
				rows[i]=temp.row+1;
			}
			else if(temp.pos1!=rowhd&&temp.pos2==colhd){
				j++;
				cols[j]=temp.col+1;
			}
			temp=temp.next;
		}
		// while(ext!=null){									//testing
			// if(ext.pos1==rowhd&&ext.pos2!=colhd){
				// if(ext.row!=-1){
					// i++;
					// rows[i]=ext.row+1;
				// }
			// }
			// else if(ext.pos1!=rowhd&&ext.pos2==colhd){
				// if(ext.col!=-1){
					// j++;
					// cols[j]=ext.col+1;
				// }
			// }
			// ext=ext.next;
		// }
		int value1=-1,value2=-1,incr1=0,incr2=0,count=0,value_1[]=new int[2],value_2[]=new int[2];
		if(rows[0]!=0){
			if(rows[1]!=0)
				value1=placeStrategy(rows[0]-1,rows[1]-1);
			else{
				for(i=0;i<3;i++)
					if(i!=rows[0]-1)
						value_1[incr1++]=i;
			}
		}
		if(cols[0]!=0){
			if(cols[1]!=0)
				value2=placeStrategy(cols[0]-1,cols[1]-1);
			else{
				for(i=0;i<3;i++)
					if(i!=cols[0]-1)
						value_2[incr2++]=i;
				if(value1!=-1){
					for(i=0;i<2;i++){
						if(SudokuPuzzle.sudoku[rowhd][colhd][value1][value_2[i]]!=0)
							count++;
						else
							value2=value_2[i];
					}
					if(count==0)
						value2=-1;
				}
				else{
					if(incr1!=0){
						for(i=0;i<2;i++)
							for(j=0;j<2;j++){
								if(SudokuPuzzle.sudoku[rowhd][colhd][value_1[i]][value_2[j]]!=0)
									count++;
								else{
									value1=value_1[i];
									value2=value_2[j];
								}
							}
						if(count!=3){
							value1=-1;
							value2=-1;
						}
					}
					else{
						for(i=0;i<3;i++)
							for(j=0;j<2;j++){
								if(SudokuPuzzle.sudoku[rowhd][colhd][i][value_2[j]]!=0)
									count++;
								else{
									value1=i;
									value2=value_2[j];
								}
							}
						if(count!=5){
							value1=-1;
							value2=-1;
						}
					}
				}
			}
		}
		else{
			if(value1!=-1){
				for(i=0;i<3;i++){
					if(SudokuPuzzle.sudoku[rowhd][colhd][value1][i]!=0)
						count++;
					else
						value2=i;
				}
				if(count!=2)
					value2=-1;
					
			}
			else{
				if(incr1!=0){
					for(i=0;i<2;i++)
						for(j=0;j<3;j++){
							if(SudokuPuzzle.sudoku[rowhd][colhd][value_1[i]][j]!=0)
								count++;
							else{
								value1=value_1[i];
								value2=j;
							}
						}
					if(count!=5){
						value1=-1;
						value2=-1;
					}
				}
				else{
					for(i=0;i<3;i++)
						for(j=0;j<3;j++){
							if(SudokuPuzzle.sudoku[rowhd][colhd][i][j]!=0)
								count++;
							else{
								value1=i;
								value2=j;
							}
						}
					if(count!=8){
						value1=-1;
						value2=-1;
					}
				}
			}
		}
		if(value1!=-1&&value2==-1){					//testing
			// node temp1=head1;
			// while(temp1!=null){
				// if(rowhd==temp1.pos1&&colhd==temp1.pos2&&value1==temp1.row&&value2==temp1.col)
					// return 0;
				// temp1=temp1.next;
			// }
			dataFeeder(rowhd,colhd,value1,-1);
			return 1;
		}
		if(value2!=-1&&value1==-1){
			if(incr1!=0){
				for(i=0;i<2;i++){
					if(SudokuPuzzle.sudoku[rowhd][colhd][value_1[i]][value2]!=0)
						count++;
					else
						value1=value_1[i];
				}
				if(count!=1)
					value1=-1;
			}
			else{
				for(i=0;i<3;i++){
					if(SudokuPuzzle.sudoku[rowhd][colhd][i][value2]!=0)
						count++;
					else
						value1=i;
				}
				if(count!=2)
					value1=-1;
			}
			if(value1==-1){								//testing
				// node temp1=head1;
				// while(temp1!=null){
					// if(rowhd==temp1.pos1&&colhd==temp1.pos2&&value1==temp1.row&&value2==temp1.col)
						// return 0;
					// temp1=temp1.next;
				// }
				dataFeeder(rowhd,colhd,-1,value2);
				return 1;
			}
		}
		if(value1!=-1&&value2!=-1){
			System.out.println("Value1: "+value1+"Value2: "+value2);
			listFormate(rowhd,colhd,value1,value2,rescue);
			return 1;
		}
		else
			return 0;
	}
	int placeStrategy(int value1,int value2){
		for(int i=0;i<3;i++)
			if(i!=value1&&i!=value2)
				return i;
		return 0;
	}
	int checkStatus(){
		int state=status;
		status=0;
		head1=null;
		return state;
	}
	int checkSize(){
		return size;
	}
}
class SudokuPuzzle{
	static int sudoku[][][][]=new int[3][3][3][3];
	public static void main(String arr[]){
		Scanner user=new Scanner(System.in);
		int pos1,pos2,row,col,again,copy,temp[][]=new int[10][2];
		int ptr[]=new int[9];
		LinkedSolver cust[]=new LinkedSolver[10];
		for(int i=0;i<10;i++)
			cust[i]=new LinkedSolver();
		System.out.println("Do you Know Programming: ");
		int com=user.nextInt();
		if(com==1){
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)
					for(int k=0;k<3;k++)
						for(int l=0;l<3;l++)
							sudoku[i][k][j][l]=user.nextInt();
		}
		else
			do{
				System.out.println("Head Row: ");
				pos1=user.nextInt();
				System.out.println("Head Colomn: ");
				pos2=user.nextInt();
				System.out.println("Row: ");
				row=user.nextInt();
				System.out.println("Colomn: ");
				col=user.nextInt();
				sudoku[pos1][pos2][row][col]=user.nextInt();
				System.out.println("Continue: 1"); 
				again=user.nextInt();
			}while(again==1);
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				for(int k=0;k<3;k++){
					for(int l=0;l<3;l++){
						int input=sudoku[i][k][j][l];
						if(input==0)
							System.out.print("_ ");
						else{
							System.out.print(input+" ");
							temp[input][0]++;													//temp[0] unused
							cust[input].listFormate(i,k,j,l,0);									//cust[0] usused
						}
					}
					System.out.print("| ");
				}
				System.out.println();
			}
			System.out.println("-------------------------");
		}
		for(int i=1;i<10;i++){
			temp[i][1]=i;
		}     
		for(int i=1;i<10;i++){			
			for(int j=i+1;j<10;j++)
				if(temp[i][0]<temp[j][0]){
					int ext=temp[i][0];
					temp[i][0]=temp[j][0];
					temp[j][0]=ext;
					ext=temp[i][1];
					temp[i][1]=temp[j][1];
					temp[j][1]=ext;
				}
			ptr[i-1]=temp[i][1];
		}
		// for(int i=1;i<10;i++){				//test
			// System.out.println("Ans: "+cust[i].checkSize());
		// }
		// cust[6].listFinder(6);
		for(int i=0;i<9;i++){
			if(cust[ptr[i]].checkSize()<9){
				cust[ptr[i]].listFinder(ptr[i]);
				if(cust[ptr[i]].checkStatus()==1)
					i=-1;
			}
		}
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				for(int k=0;k<3;k++){
					for(int l=0;l<3;l++){
						copy=sudoku[i][k][j][l];
						if(copy==0)
							System.out.print("_ ");
						else
							System.out.print(copy+" ");
					}
					System.out.print("| ");
				}
				System.out.println();
			}
			System.out.println("-------------------------");
		}
	}
}