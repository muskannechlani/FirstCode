

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

class node{                                                           //node to store source,destination,weight

	int src;
	int dest;
	int weight;
	node(int src,int dest,int weight)
	{

		this.src=src;
		this.dest=dest;
		this.weight=weight;

	}

}
public class Kruskals implements Comparator
{
	public static void main(String[] args) {
		
		ArrayList <node> a=new ArrayList();     //to store all the edges sorted
		ArrayList <TreeSet>newarr=new ArrayList();       //to store disjoint sets
		int verticesno;    					                                      	//number of vertices are stored in this variable
		int weight=0;
		node temp = null;
		System.out.println("Enter number of houses:");
		Scanner s=new Scanner(System.in);
		verticesno=s.nextInt();
		LinkedList <node>l[]=new LinkedList[verticesno];  			                    //using adjacency list to represent GRAPH
		for(int i=0;i<verticesno;i++)
		{

			l[i]=new LinkedList();                                                    //TO STORE ROUTES FOR A PARTICULAR HOUSE
			for(int j=0;j<i;j++)								                      //IT WILL ALWAYS ACCEPT SOURCE > DESTINATION PATH THUS NO LOOP FORMATION
			{
				System.out.println("enter route weight between house " +(i+1)+ " and " +(j+1)+ " (1/0) ");
				weight=s.nextInt();
				if(weight==0)														                //SINCE WEIGHT IS 0,IT SHOULD BE ASSIGNED A GREATER NUMBER
				{

					weight=999;

				}
				else
				{

					temp=new node((i+1), (j+1), weight);                  //CREATING USER ENTERED PATH BETWEEN USER ENTERED HOUSES
					l[i].add(temp);
					a.add(temp);								                          //SIMULTANEOUSLY ADDING EDGES TO ARRAYLIST
					temp=new node((j+1), (i+1), weight);	                //FOR UNDIRECTED GRAPH 
					l[j].add(temp);
					

				}

			}

		}

		System.out.println("ADJACENCY LIST ");
		for(int i=0;i<verticesno;i++)
		{

			System.out.print( (i+1) +"  ");
			for(node temp1:l[i])
			{

				System.out.print("  "+ (i+1)+" -> "+temp1.dest + " = "+temp1.weight);
			}
			System.out.println();

		}
		Collections.sort(a, new Kruskals());
		System.out.println("after sorting according to weights");
		int count=0;
		for(node temp1:a)
		{

			System.out.println(temp1.src+" -> "+temp1.dest + " =	"+temp1.weight);
		}
		int count123=0,min=100,max=100,sum=0,flag=0;
		for(int i=0;i<a.size();i++)                                       //LOOP TO ADD EDGES OR FING MINIMUM SPANNING TREE
		{
			node temppp=a.get(i);                                          //TO RETRIEVE EDGE
			int y=temppp.src;
			int z=temppp.dest;
			if(count==0)			                                          //THIS INDICATES NO SET HAS BEEN ADDED TO NEWARR ARRAYLIST
			{
				TreeSet h=new TreeSet();
				h.add(y);			
				h.add(z);
				newarr.add(h);		                                       //adding source and destination in a set actually adding a disjoint set
				count++;			                                          //keeping  track of how n=many disjoint seta are there in arraylist
				System.out.println("first one " + temppp.src+" -> "+temppp.dest + " =	"+temppp.weight);
				sum=sum+temppp.weight;			                            //update sum
			}else
			{	count123=0;			                          //a variable to check if our source and destination are present in disjoint set or not 
				flag=0;				        //this flag when set indicates one of source or dest is in set another one has to be added in the same set
				min=100;
				max=100;
				for(int j=0;j<count;j++)
				{	
				count123=0;
				TreeSet t=(TreeSet)newarr.get(j);		                                                //retreiving a disjoint set
					if(t.contains(y))						                                                  //checking if it contains source
					{
						
						count123++;
					}
					if(t.contains(z))			                                                        //checking if it contains destination
					{
						
						count123++;
					}


					if(count123==2)			                                        //if it contains both cycle will be formed so not to add this edge
					{
						System.out.println("cannot add edge as it forms cycle " +temppp.src + " => " +temppp.dest +" = " +temppp.weight);
						System.out.println(" ");
						break;
					}
					if(count123==1)			//if it contains any one there are two possiblities 1)both src and dest are in different sets 2)either of them is in one set and another one is not in any set
					{

						if(min==100)
						{
							min=j;
							flag=1;			//if set either of them is in one set and another one is not in any set
							
						}else
						{
							max=j;
							flag=0;			//if reset both src and dest are in different sets 
						}
						

					}
					

				}
				
				 if(flag==0 && max!=100)			// both src and dest are in different sets  combine both sets we have their indexes in min and max
				{
					 TreeSet h1=new TreeSet<>();
					 TreeSet h2=new TreeSet<>();
					 h1=(TreeSet)newarr.get(min);
					 h2=(TreeSet)newarr.get(max);
					 h1.addAll(h2);
					 h2.clear();
					 System.out.println("" +temppp.src + " => " +temppp.dest +" = " +temppp.weight);
					 sum=sum+temppp.weight;
				}
				 else if(flag==1 && min!=100)                                       //we have to add the one which is not present in the set
				 {
					 TreeSet h1=new TreeSet<>();
					 h1=(TreeSet)newarr.get(min);
					 h1.add(y);
					 h1.add(z);
					 System.out.println("" +temppp.src + " => " +temppp.dest +" = " +temppp.weight);
					 sum=sum+temppp.weight;
				 }else if(count123==0)		                                	//both src and dest are not present so make a separate disjoint set
					{
						TreeSet h1=new TreeSet<>();
						h1.add(y);
						h1.add(z);
						newarr.add(h1);
						count++;
						 System.out.println("" +temppp.src + " => " +temppp.dest +" = " +temppp.weight);
						sum=sum+temppp.weight;
					}
			}
		}
		System.out.println("SHORTEST PATH IS => "  +sum);
	}
	@Override
	public int compare(Object e1, Object e2) {
		// TODO Auto-generated method stub
		node n1=(node)e1;
		node n2 =(node)e2;
		if(n1.weight>n2.weight)
		{

			return 1;

		}
		else if(n1.weight<n2.weight)
		{

			return -1;

		}
		else
			return 0;
	}

}
/*
Enter number of houses:
9
enter route weight between house 2 and 1 (1/0) 
12
enter route weight between house 3 and 1 (1/0) 
0
enter route weight between house 3 and 2 (1/0) 
5
enter route weight between house 4 and 1 (1/0) 
1
enter route weight between house 4 and 2 (1/0) 
0
enter route weight between house 4 and 3 (1/0) 
0
enter route weight between house 5 and 1 (1/0) 
14
enter route weight between house 5 and 2 (1/0) 
3
enter route weight between house 5 and 3 (1/0) 
0
enter route weight between house 5 and 4 (1/0) 
0
enter route weight between house 6 and 1 (1/0) 
0
enter route weight between house 6 and 2 (1/0) 
0
enter route weight between house 6 and 3 (1/0) 
8
enter route weight between house 6 and 4 (1/0) 
0
enter route weight between house 6 and 5 (1/0) 
4
enter route weight between house 7 and 1 (1/0) 
0
enter route weight between house 7 and 2 (1/0) 
0
enter route weight between house 7 and 3 (1/0) 
0
enter route weight between house 7 and 4 (1/0) 
6
enter route weight between house 7 and 5 (1/0) 
2
enter route weight between house 7 and 6 (1/0) 
0
enter route weight between house 8 and 1 (1/0) 
0
enter route weight between house 8 and 2 (1/0) 
0
enter route weight between house 8 and 3 (1/0) 
0
enter route weight between house 8 and 4 (1/0) 
0
enter route weight between house 8 and 5 (1/0) 
11
enter route weight between house 8 and 6 (1/0) 
0
enter route weight between house 8 and 7 (1/0) 
13
enter route weight between house 9 and 1 (1/0) 
0
enter route weight between house 9 and 2 (1/0) 
0
enter route weight between house 9 and 3 (1/0) 
0
enter route weight between house 9 and 4 (1/0) 
0
enter route weight between house 9 and 5 (1/0) 
0
enter route weight between house 9 and 6 (1/0) 
9
enter route weight between house 9 and 7 (1/0) 
0
enter route weight between house 9 and 8 (1/0) 
10
ADJACENCY LIST 
1  1 -> 2 = 12  1 -> 4 = 1  1 -> 5 = 14
2  2 -> 1 = 12  2 -> 3 = 5  2 -> 5 = 3
3  3 -> 2 = 5  3 -> 6 = 8
4  4 -> 1 = 1  4 -> 7 = 6
5  5 -> 1 = 14  5 -> 2 = 3  5 -> 6 = 4  5 -> 7 = 2  5 -> 8 = 11
6  6 -> 3 = 8  6 -> 5 = 4   6 -> 9 = 9
7  7 -> 4 = 6  7 -> 5 = 2   7 -> 8 = 13
8  8 -> 5 = 11  8 -> 7 = 13  8 -> 9 = 10
9  9 -> 6 = 9  9 -> 8 = 10
after sorting according to weights
4 -> 1 =	1
7 -> 5 =	2
5 -> 2 =	3
6 -> 5 =	4
3 -> 2 =	5
7 -> 4 =	6
6 -> 3 =	8
9 -> 6 =	9
9 -> 8 =	10
8 -> 5 =	11
2 -> 1 =	12
8 -> 7 =	13
5 -> 1 =	14
first one 4 -> 1 =	1
7 => 5 = 2
5 => 2 = 3
6 => 5 = 4
3 => 2 = 5
7 => 4 = 6
cannot add edge as it forms cycle 6 => 3 = 8
 
9 => 6 = 9
9 => 8 = 10
cannot add edge as it forms cycle 8 => 5 = 11
 
cannot add edge as it forms cycle 2 => 1 = 12
 
cannot add edge as it forms cycle 8 => 7 = 13
 
cannot add edge as it forms cycle 5 => 1 = 14
 
SHORTEST PATH IS => 40
*/
