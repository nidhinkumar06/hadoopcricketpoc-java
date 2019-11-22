package org.nidhin.cricket;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class AllTeam {
	private static StringBuffer csv_data = new StringBuffer();
	private static final String[] batsmen = { "Kohli" };
	static int count = 0;

	public static void main(String... args) throws IOException {
		Path path = Paths.get("/home/nidhin/Desktop/poc");

		for (String batsman : batsmen) {
			try (DirectoryStream<Path> ds = Files
					.newDirectoryStream(path, "*.yaml")) {
				Iterator<Path> iterator = ds.iterator();
				System.out.println(iterator.toString());
				int c = 0;
				while (iterator.hasNext()) {
					Path filePath = iterator.next();
					convertFile(filePath);
					c++;
				}
			}
		}
		
		Files.write(Paths.get("/home/nidhin/Desktop/POCCricket/allteam.csv"),
				csv_data.toString().getBytes());

	}

	private static void convertFile(Path filePath) {
		String yamlContent = null;
//		System.out.println("filepath" + filePath);

		try {

			yamlContent = new String(Files.readAllBytes(filePath));
			Yaml yaml = new Yaml();
			Map<String, Object> map = (Map<String, Object>) yaml
					.load(yamlContent);
			Map<String, Object> matchInfo = ((Map) map.get("info"));

			ArrayList<String> teams = (ArrayList<String>) matchInfo
					.get("teams");

			ArrayList<String> manofmatch = (ArrayList<String>) matchInfo
					.get("player_of_match");

			Map<String, Object> matchResult = ((Map) matchInfo.get("outcome"));
			Map<String, Object> toss = ((Map) matchInfo.get("toss"));
			

			String teamA = teams.get(0);
			String teamB = teams.get(1);
			String gender = (String) matchInfo.get("gender");
			String matchType = (String) matchInfo.get("match_type");
			String city = (String) matchInfo.get("city");
			String venue = (String) matchInfo.get("venue");
			venue = venue.replaceAll(",", "");
			int overs = (int) matchInfo.get("overs");
			String tossWinner = (String) toss.get("winner");
			String tossDecision = (String) toss.get("decision");
			String winner = (String) matchResult.get("winner");
			String matchPlayer = manofmatch.get(0);
			

			csv_data.append(teamA + "," + teamB + "," + gender + ","
					+ matchType + "," + city + "," + venue + "," + overs + ","
					+ tossWinner + "," + tossDecision + "," + winner + ","
					+ matchPlayer + "\n");
			
//			System.out.println("csvlength" + csv_data.length());

		} catch (Exception e) {
//			System.out.println("Exception" + e);
//			count = count + 1;
//			System.out.println("count" + count);
			return;
		}

	}
}
