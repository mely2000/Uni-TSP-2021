# Uni-TSP-2021
Uni 2nd Year Travelling Salesman Problem

For the TSP algorithm I decided to implement a greedy algorithm. This algorithm would mean starting at Apache Pizza, then going to the closest vertex to it on the graph. Since the delivery person was not limited by roads or traffic, this was quite straightforward. With the help of a distance matrix containing all the locations inputted and the haversine formula, the program searches for the closest location that isn’t visited and moves to it, while adding it to the route that the motorcyclist must take. So, to begin I will show you the variables that define my vertices. 

 

So, into the run() method. This method begins with splitting the user input into lines. The distance matrix is initialised here, with the size being the number of locations plus 1 in order to contain apache pizza. Also, the apache vertex parameters are defined here. 

 

The program then moves into a for loop, spanning the number of lines that the program has split the user input into (no. Of locations). This for loop splits these lines into a new array wherever there is a comma. By doing this, the program takes out the data of the location and assigns this location to a vertex. This vertex is then added into a vertices ArrayList, which is a collection of all the locations that have been inputted. At the very end, after the for loop has finished analysing the user input, the Apache Pizza vertex is added to vertices. We now have a collection with a vertex for every location and its data, meaning it’s id (1 – n (including Apache Pizza)), its label, the amount of time it has been waiting and, it’s GPS north and west coordinates. 

 

Now we must go about finding a way to calculate the distance between the locations (vertices). For this we need a special method, the distanceCalc method. The distanceCalc method takes two locations’ latitude and longitude, and uses the Haversine formula to calculate the distance between them. The Haversine formula is needed as the we must calculate distance across the sphere we call Earth. According to Wikipedia, The haversine formula determines the great-circle distance between two points on a sphere given their longitudes and latitudes.  

 

So, the distanceCalc method takes in two vertices, their GPS coordinates, which show their latitude and longitude and uses the Haversine formula to calculate distance. 

 

With this method we can construct our distanceMatrix. My program spans a 2D double array and inserts the distances between all vertices with the help of the distanceCalc method. 

 

Now that we have our distanceMatrix, we can easily find the best route for out delivery person. However, I though it best to have the algorithm to find the closest vertex in a separate method. This is where nearestNeighbour comes in. NearestNeighbour takes in a vertex, the distanceMatrix and the ArrayList vertices and finds the closest vertex that has not been visited yet. When given a vertex, it will move to that vertex’s row on the distanceMatrix, and find the value that is the smallest(closest vertex). After running through the respective vertex’s row, the method returns the closest vertex.  

 

With all the tools we need we can now begin adding the correct vertices into our route ArrayList. We start by assigning Apache Pizza as the current vertex, and that it has been visited. Then, we move into a for loop that spans our vertices ArrayList. This for loop checks that as long as the id of the vertex is not 0, it will add the nearestNeighbour of the current vertex to the route ArrayList, then it will assign current to that nearest vertex, and finally assigning that vertex with the status of visited. 

 

 

The main method of my TSP app begins with starting a Scanner object for user inputs, and then initialising my Window class, which I will explain. This window object then begins the setup() method, which handles the rest of the app. Finally the scan object is closed. 

 

The Window class handles the rest of the majority of the program. It begins by declaring a variety of JContainers that will make up the GUI, also a BufferedImage of the map surrounding Maynooth that the delivery person can deliver to. Also, of course an instance of the Computer class that handles the TSP algorithm.  

 

The Window constructor takes in two values, width and height that define the frame’s dimensions. 

 

Then, there is the setup() method, which begins by defining the frame with it’s width and height, then giving the frame a BorderLayout, setting it’s DefaultCloseOperation, and setting Resizable to false. It then defines the three panels that will make up the GUI, bottom, right and mapPanel. The method begins with mapPanel, reading in the “map.png” that should be in the program’s folder along with it. Once the method has this png, it scales it to 555 by 555. It then converts it into an ImageIcon and then puts it into a JLabel called label1. 

 

Label1 is then added into the mapPanel. Then, the bottom and right panels have their layouts and borders defined with BorderLayout and EtchedBorders. 

 

Then, the method begins work on the bottomButton, labelled “Compute”. Firstly, it defines a text area called bottomTA which will be used for the user input. It then defines the button bottomButtom, assigning it’s name and it’s dimensions(175, 150). It then adds an ActionListener to the button, that calls the actionPerformed() method. This method takes in the text that is typed into the bottomTA as a String, and calls the comp object with that String(user input). The comp object’s run method is then called which begins the TSP algorithm. After the TSP algorithm has run, we will have the fastest route the delivery person can take as an ArrayList. We then define the map oject with the parameter of that route ArrayList, in order to paint the route onto the map. It then adds this map object onto the JFrame and then validates the frame in order to rerender. 

 

I have learned a lot from this experience. It has made clear to me that the best way to get better at a language is to simply build what I want instead of learning by reading. By just going ahead and trying to build software you will come across more problems and weaknesses with your knowledge of that language, which allows you to refine your skills. For example, I am ten times better at using the Swing package than I was before and have earned valuable knowledge in building GUIs.  
