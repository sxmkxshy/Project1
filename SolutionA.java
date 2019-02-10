package com.company;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

class Road {
    public String city1;
    public String city2;

    public Road(String city1, String city2) {
        this.city1 = city1;
        this.city2 = city2;
    }

}

class RoadMap {
    Map<String, Set<Road>> roadMap = new HashMap<String, Set<Road>>();

    //This function helps to get all the cities in the graph
    public Set<String> getAllCities() {
        return this.roadMap.keySet();
    }

    //This function will read the input 
    public void readLine(String line) {
        String[] csv = line.split(",");
        String city1 = csv[0];
        String city2 = csv[1];
        addRoad(city1, city2);
    }

    private void addCity(String city) {
        this.roadMap.put(city, new HashSet<Road>());
    }

    //This function will add both outgoing and incoming roads between two cities
    private void addRoad(String city1, String city2) {
        Road road1 = new Road(city1, city2);
        Road road2 = new Road(city2, city1);
        if (!this.roadMap.containsKey(city1)) {
            addCity(city1);
        }
        if (!this.roadMap.containsKey(city2)) {
            addCity(city2);
        }
        this.roadMap.get(city1).add(road1);
        this.roadMap.get(city2).add(road2);
    }

    //This function will return all the outgoing roads from a city
    public Set<Road> getAllOutgoingRoads(String node) {
        return this.roadMap.get(node);
    }

}

public class SolutionA {
    static RoadMap roadMap = new RoadMap();

    public static void readMap(Scanner scanner) {
        while (true) {
            String mapLine = scanner.nextLine();
            if (mapLine.equals("")) {
                break;
            }
            roadMap.readLine(mapLine);
        }


    }


    public static void findAnyRouteToCity(String source, String destination) {
        // PART-A: Write the required java code to find the possible routes from source to destination
        if (source == null || destination == null) {
            System.out.println("source and destination cannot be blanks.Please enter valid values");
            return;
        }
        if (!roadMap.roadMap.containsKey(source) || !roadMap.roadMap.containsKey(destination)) {
            System.out.println("Either the source or destination is not in the roadmap \n" + "Please enter valid values");
            return;
        }
        if (source.equals(destination)) {
            System.out.println("Source and Destination are same.Please give a different destination");
            return;
        }
        Map<String, Boolean> visited = new HashMap<>();
        Set<String> cities = roadMap.getAllCities();
        for (String city : cities) {
            visited.put(city, false);
        }
        visited.put(source, true);
        //calling the printRoutes method to print all possible routes between source and destination
        int count = printRoutes(source, destination, visited, "");
        // checks value of the count variable returned by the called method that tells you if no route was found
        if (count == 0) {
            System.out.println("Not Reachable");
        }
    }

    // function to print all possible routes between two cities by doing graph depth traversal using recursion
    public static int printRoutes(String source, String destination, Map<String, Boolean> visited, String route) {
        int routeCount = 0;
        String newRoute = route + source + "->";
        visited.put(source, true);
        Set<Road> roads = roadMap.getAllOutgoingRoads(source);
        for (Road road : roads) {
            if (road.city2.equals(destination)) {
                System.out.println(newRoute + road.city2);
                routeCount++;
            } else if (!(road.city2.equals(destination)) && visited.get(road.city2) == false) {
                routeCount += printRoutes(road.city2, destination, visited, newRoute);
            }
        }
        visited.put(source, false);
        return routeCount;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Map");
        try {
            readMap(scanner);
            System.out.println("Enter the source ");
            String source = scanner.nextLine();
            System.out.println("Enter the destination ");
            String destination = scanner.nextLine();
            System.out.println("The route from " + source + " to " + destination + " is");
            findAnyRouteToCity(source, destination);
        } catch (Exception e) {
            e.getMessage();
        }

    }
}
