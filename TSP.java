import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class TSP 
{
	static String input = "";
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		Window window = new Window(750, 750); 
		window.setup(); //Initialises Window
		scan.close();
    }
	
}
class Window implements ActionListener
{
	public JFrame frame = new JFrame("Delivery Service");
	public int width, height;
	private JPanel bottom, right, mapPanel;
	private Map map;
	BufferedImage mapImg;
	protected JLabel label1;
	protected JTextArea bottomTA, rightTA;
	protected JButton bottomButton, showAddresses;
	protected JScrollPane scroll1, scroll2;
	protected String input;
	protected boolean bottomPressed;
	protected ImageIcon imageIcon;
	
	Computer comp;
	
	public Window(int width, int height)
	{
		this.width = width; //takes in size of window
		this.height = height;
	}
	
	public void setup()
	{
		
		frame.setSize(width, height);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); //Initialises frame

        bottom = new JPanel(); //defines bottom, right and map panels
        right = new JPanel();
        mapPanel = new JPanel();
        
        try
		{
			mapImg = ImageIO.read(getClass().getResource("map.png")); //gets map.png
		}
		catch(Exception IOException)
		{
			System.out.println("Invalid File");
		}
        ImageIcon mapIcon = new ImageIcon(mapImg); //changes bufferedImage to Image for resizing
        Image image = mapIcon.getImage();
        Image newimg = image.getScaledInstance(555, 555,  java.awt.Image.SCALE_SMOOTH); //resizes
        mapIcon = new ImageIcon(newimg); //image to imageIcon
		label1 = new JLabel(mapIcon); //inserts imageIcon into JLabel
		
		mapPanel.add(label1); //inserts label1 container into map panel
        bottom.setLayout(new BorderLayout());
        bottom.setBorder(BorderFactory.createEtchedBorder()); 
        right.setLayout(new BorderLayout());
        right.setBorder(BorderFactory.createEtchedBorder()); //sets BorderLayout for bottom and right panels
        
        bottomTA = new JTextArea(10, 30); //creates text area for bottom panel
        
        bottomButton = new JButton("Compute"); //defines "compute" button for bottom panel
        bottomPressed = false;
        bottomButton.setPreferredSize(new Dimension(175, 150));
        bottomButton.addActionListener // add action listener that invokes TSP algorithm and paints locations onto map
        (
        		new ActionListener()
        		{
        			public void actionPerformed(ActionEvent e) 
        			{
        				input = bottomTA.getText();
        				comp = new Computer(input);
        				comp.run();
        				map = new Map(comp.route);
        				frame.add(BorderLayout.CENTER, map);
        				frame.validate();
        			}
        		}
        );
        
        rightTA = new JTextArea(30, 5); // defines right text field for output
        rightTA.setLineWrap(true);
        
        showAddresses = new JButton("Addresses"); //defines address button
        showAddresses.setPreferredSize(new Dimension(150, 100));
        showAddresses.addActionListener //adds action listener that lists all IDs -> fastest route
        (
        		new ActionListener()
        		{
        			public void actionPerformed(ActionEvent e)
        			{
        				String input = new String();
        				Iterator<Vertex> it = comp.route.iterator();
        				
        				while(it.hasNext())
        				{
        					input += "  "+it.next().id;
        				}
    
        				rightTA.setText(input);
        			}
        		}
        );
        
        scroll2 = new JScrollPane(rightTA); //adds right text panel to scroll pane and defines
        scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll2.setPreferredSize(new Dimension(175, 450));
        
        scroll1 = new JScrollPane(bottomTA);//adds bottom text panel to scroll pane and defines
        scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll1.setPreferredSize(new Dimension(553, 150));
        
        bottom.add(BorderLayout.WEST, scroll1);
        bottom.add(BorderLayout.EAST, bottomButton); //adds text panel with scroll bar and compute button to bottom panel
        right.add(BorderLayout.NORTH, scroll2);
        right.add(BorderLayout.SOUTH, showAddresses);//add text panel with scroll bar and addresses button to right panel
        
        frame.add(BorderLayout.SOUTH, bottom);
        frame.add(BorderLayout.CENTER, mapPanel);
        frame.add(BorderLayout.EAST, right);
        //adds all panels to frame

        
		frame.setVisible(true); //renders frame
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
	}
	
}
class Map extends JPanel
{
	private static final long serialVersionUID = 3206285781625669519L;
	public ArrayList<Vertex> route;
	public BufferedImage image;
	public int x, y, index, oldX, oldY;
	public Map(ArrayList<Vertex> route)
	{
		this.route = route; //takes in fastest route after TSP algorithm runs
		try 
		{          
			image = ImageIO.read(getClass().getResource("map.png")); //takes in map.png
	    } 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, 555, 555, null); //draws map.png onto map panel
		for(int i = 0;i < route.size();i++)
		{
			x = positionX(route.get(i).north, route.get(i).west, 555, 555);//gets x coordinate for i vertex
			y = positionY(route.get(i).north, route.get(i).west, 555, 555);//gets y coordinate for i vertex
			if(oldX != 0 && oldY != 0) //draws red line from previous vertex to current
			{
				g2d.setColor(Color.RED);
				g2d.drawLine(x, y, oldX, oldY);
			}
			g2d.setColor(Color.BLUE); 
			g2d.fillOval(x, y, 7, 7); //draws vertex at location on map.png
			oldX = x; //sets old coordinates to current
			oldY = y;
		}
		g2d.setColor(Color.RED);
		g2d.drawLine(positionX(route.get(route.size()-1).north, route.get(route.size()-1).west, 555, 555),  //draws red line back to apache pizza
				positionY(route.get(route.size()-1).north, route.get(route.size()-1).west, 555, 555),
				positionX(53.38196, -6.59274, 555, 555), 
				positionY(53.38197, -6.59274, 555, 555));
	}
	public int positionX(double latitude, double longitude, int mapWidth, int mapHeight)
	{
		double diff =  0.13047; //difference in latitude
		diff = diff / mapHeight; //difference divided by the map height (555)
		
		latitude = 53.41318 - latitude; //latitude in relation to map dimensions
		return 555 - (int)(latitude / diff); //pixel x coorinate
	}
	public int positionY(double latitude, double longitude, int mapWidth, int mapHeight)
	{
		double diff = 0.25752; //difference in longitude
		diff = diff / mapWidth; //difference divided by the map width (555)
		
		longitude = longitude + 6.71261;//longitude in relation to map dimensions
		return 555 - (int)(longitude / diff);//pixel y coorinate
	}
}
class Computer implements Runnable //TSP algorithm
{
	private String input;
	private int noVertices;
	private double[][] distanceMatrix;
	public ArrayList<Vertex> vertices;
	public ArrayList<Vertex> route = new ArrayList<Vertex>();
	
	public Computer(String input)
	{
		this.input = input; //input taken from bottom text panel
	}
	
	@Override
	public void run() 
	{
		String[] lines = input.split("\\r?\\n"); //splits string into rows
		noVertices = lines.length;
		distanceMatrix = new double[noVertices+1][noVertices+1];//no of locations +apache pizza
		vertices = new ArrayList<Vertex>();
		Vertex apache = new Vertex(); //premake apache vertex
		apache.id = noVertices+1;
		apache.label = "Apache Pizza";
		apache.waitingTime = 0;
		apache.north = 53.38196;
		apache.west = -6.59274;
		
		for(int i = 0;i < lines.length;i++) //assigning id, label, waitingTime, north and west to vertices
		{
			String[] line;
			Vertex vertex = new Vertex();
			line = lines[i].split(",");
			vertex.id = Integer.parseInt(line[0]);
			vertex.label = line[1];
			vertex.waitingTime = Integer.parseInt(line[2]);
			vertex.north = Double.parseDouble(line[3]);
			vertex.west = Double.parseDouble(line[4]);
			vertices.add(vertex);
		}
		vertices.add(apache); //add apache pizza
		
		for(int row = 0;row < distanceMatrix.length;row++) //creates distance matrix of all locations
		{
			for(int col = 0; col < distanceMatrix[row].length;col++)
			{
				distanceMatrix[row][col] = distanceCalc(vertices.get(row), vertices.get(col));
			}
		}
		
		vertices.get(apache.id-1).visited = true; 
		Vertex current = apache;//begin at apache pizza
		for(int i = 0; i < vertices.size();i++)
		{
				if(nearestNeighbour(current,distanceMatrix,vertices).id != 0)
				{
					route.add(nearestNeighbour(current, distanceMatrix, vertices));//adds closest vertex to route ArrayList
					current = nearestNeighbour(current, distanceMatrix, vertices);//sets current to closest vertex
					vertices.get(current.id-1).visited = true;//sets current as visited
				}
		}
	}	
	
	public double distanceCalc(Vertex X, Vertex Y) //Haversine formula to calculate distance with GPS coordinates
	{
		double lat1 = X.north * Math.PI/180;
		double lat2 = Y.north * Math.PI/180;
		double latDiff, longDiff, a, c;
		
		latDiff = (Y.north - X.north) * Math.PI/180;
		longDiff = (Y.west - X.west) * Math.PI/180;
		
		a = Math.sin(latDiff/2) * Math.sin(latDiff/2) + 
				Math.cos(lat1) * Math.cos(lat2) *
				Math.sin(longDiff/2) * Math.sin(longDiff/2);
		c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		return 6371 * c;
	}
	
	public Vertex nearestNeighbour(Vertex vertex, double[][] distanceMatrix, ArrayList<Vertex> vertices)
	{
		Vertex nearest = new Vertex();
		double shortest = 100;
		for(int i = 0;i < distanceMatrix[vertex.id-1].length;i++) //consults distance matrix to find location that is closest to current
		{
			if(distanceMatrix[vertex.id-1][i] < shortest && vertices.get(i).visited == false)
			{
				nearest = vertices.get(i);
				shortest = distanceMatrix[vertex.id-1][i];
			}
		}
		return nearest;
	}
}

class Vertex 
{
	String label;
	boolean visited;
	int waitingTime, id;
	double north;
	double west;
	
	public Vertex()
	{}
	
	public Vertex(int id, String label, int waitingTime, double north, double west)
	{
		this.id = id;
		this.waitingTime = waitingTime;
		this.north = north;
		this.west = west;
		this.label = label;
		visited = false;
	}
}