package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Adjacency;
import entities.AdjacencyList;
import entities.City;

public class CSVManager {
	public static AdjacencyList load(String path) {

		List<City> list = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
			// skips first line with csv metadata
			String content = br.readLine();
			content = br.readLine();

			while (content != null) {
//				System.out.println(content);
				Pattern pattern = Pattern.compile("\"(.*?)\"");
				Matcher matcher = pattern.matcher(content);
				StringBuilder sb = new StringBuilder();
				while (matcher.find()) {
					// replacing every [,] between two ["] with [u00A0] for lossless formatting :)
					String replacement = matcher.group(0).replaceAll(",", " ").replaceAll("\"", "");
					matcher.appendReplacement(sb, replacement);
				}
				matcher.appendTail(sb);

				String[] splitContent = sb.toString().split(",");
				list.add(new City(splitContent));
				content = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

//		for(City c : list) {
//			System.out.println(c);
//		}

		AdjacencyList adj = new AdjacencyList(false);

		for (int i = 0; i < list.size();) {
			City city = list.get(i);
			System.out.println("looking at " + city);
			adj.addVertex(city);

			for (int j = i + 1; j < list.size(); j++) {
				City similarCity = list.get(j);
				int offset = 5;

				if (city.getCountry().equals(similarCity.getCountry())) {
					adj.addAdjacency(city, similarCity, true);
				}

				if (city.getLat() - offset <= similarCity.getLat() && city.getLat() + offset >= similarCity.getLat()) {
					if (city.getLng() - offset <= similarCity.getLng() && city.getLng() + offset >= similarCity.getLng()) {
						if (!adj.addAdjacency(city, similarCity, true)) {
							Adjacency adjacency = adj.getAdjacency(city, similarCity);
							adjacency.setWeight(adjacency.getWeight() + 0.6);
						}
					}
				}

			}
			list.remove(0);
		}

		return adj;
	}

}