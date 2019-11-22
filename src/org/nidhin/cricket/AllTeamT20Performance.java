package org.nidhin.cricket;

import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class AllTeamT20Performance {
	private static StringBuffer csv_data = new StringBuffer();
	private static final String[] teams = { "Afghanistan", "Australia",
			"Bangladesh", "England", "India", "Ireland", "New Zeland",
			"Pakistan", "South Africa", "Sri Lanka", "West Indies", "Zimbabwe" };
	private static final String[] months = { "JAN", "FEB", "MAR", "APR", "MAY",
			"JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };

	static int count = 0;

	public static void main(String... args) throws IOException {
		Path path = Paths.get("/home/nidhin/Desktop/poc");

		for (String team : teams) {
			try (DirectoryStream<Path> ds = Files.newDirectoryStream(path,
					"*.yaml")) {
				Iterator<Path> iterator = ds.iterator();
				System.out.println(iterator.toString());
				int c = 0;
				while (iterator.hasNext()) {
					Path filePath = iterator.next();
					convertFile(filePath, team.toLowerCase());
					c++;
				}
			}
		}

		Files.write(Paths
				.get("/home/nidhin/Desktop/POCCricket/allt20performance.csv"),
				csv_data.toString().getBytes());

	}

	private static void convertFile(Path filePath, String teamName) {
		String yamlContent = null;
		count = count + 1;
		System.out.println("count" + count);

		try {
			yamlContent = new String(Files.readAllBytes(filePath));
			Yaml yaml = new Yaml();
			Map<String, Object> map = (Map<String, Object>) yaml
					.load(yamlContent);
			Map<String, Object> matchInfo = ((Map) map.get("info"));

			String matchType = (String) matchInfo.get("match_type");

			ArrayList<String> teams = (ArrayList<String>) matchInfo
					.get("teams");
			String firstTeam = teams.get(0);
			String secondTeam = teams.get(1);

			String gender = (String) matchInfo.get("gender");
			String city = (String) matchInfo.get("city");

			if ((firstTeam.equalsIgnoreCase(teamName) || secondTeam
					.equalsIgnoreCase(teamName))
					&& matchType.equalsIgnoreCase("t20")) {

				String opposite_team = (teamName).equalsIgnoreCase(firstTeam) ? secondTeam
						: firstTeam;

				String winning_team = (String) ((Map) matchInfo.get("outcome"))
						.get("winner");
				String result = (teamName).equalsIgnoreCase(winning_team) ? "WON"
						: "LOST";
				String location = (String) matchInfo.get("venue");
				location = location.replaceAll(",", "");

				Date matchDate = (Date) (((ArrayList<Date>) matchInfo
						.get("dates")).get(0));
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(matchDate);
				int month = calendar.get(Calendar.MONTH);
				String monthName = months[month];

				Map firstInningsMap = (Map) ((Map) ((ArrayList) map
						.get("innings")).get(0)).get("1st innings");
				String firstInningsTeam = (String) firstInningsMap.get("team");

				ArrayList firstInningDeliveries = (ArrayList) firstInningsMap
						.get("deliveries");

				Map secondInningsMap = (Map) ((Map) ((ArrayList) map
						.get("innings")).get(1)).get("2nd innings");
				String secondInningsTeam = (String) secondInningsMap
						.get("team");

				ArrayList secondInningDeliveries = (ArrayList) secondInningsMap
						.get("deliveries");

				boolean chasing = (teamName).equalsIgnoreCase(firstInningsTeam) ? false
						: true;

				ArrayList teamInningDeliveries = firstInningDeliveries;
				if (chasing) {
					teamInningDeliveries = secondInningDeliveries;
				}
				int teamTotalRuns = 0;

				int teamScoreIn5Overs = 0;
				int teamScoreIn10Overs = 0;
				int teamScroreIn15Overs = 0;
				int teamScroreIn20Overs = 0;

				for (Object deliveryDetailObj : teamInningDeliveries) {
					Map deliveryDetail = (Map) deliveryDetailObj;

					for (Object entry : deliveryDetail.entrySet()) {
						Map.Entry mapEntry = (Map.Entry) entry;
						DecimalFormat df = new DecimalFormat("#.#");
						df.setRoundingMode(RoundingMode.CEILING);
						double ballOfover = (Double) mapEntry.getKey();
						String ballOfOverStr = df.format(ballOfover).toString();
						Map ballDetailMap = (Map) mapEntry.getValue();
						Map runsMap = (Map) ballDetailMap.get("runs");

						int totalRunsInThisBall = (Integer) runsMap
								.get("total");

						teamTotalRuns = teamTotalRuns + totalRunsInThisBall;

						if (ballOfover <= 4.5) {
							teamScoreIn5Overs = teamTotalRuns;
						}
						if (ballOfover <= 9.5) {
							teamScoreIn10Overs = teamTotalRuns;
						}
						if (ballOfover <= 14.5) {
							teamScroreIn15Overs = teamTotalRuns;
						}
						if (ballOfover <= 19.5) {
							teamScroreIn20Overs = teamTotalRuns;
						}
						if (ballOfover <= 20 || ballOfover <= 19.6) {
							teamScroreIn20Overs = teamTotalRuns;
						}
					}
				}

				csv_data.append(teamName + "," + opposite_team + "," + gender
						+ "," + location + "," + city + "," + chasing + ","
						+ matchDate.toString() + "," + monthName + ","
						+ teamScoreIn5Overs + "," + teamScoreIn10Overs + ","
						+ teamScroreIn15Overs + "," + teamScroreIn20Overs + ","
						+ result + "\n");
			}

		} catch (Exception e) {
			return;
		}

	}
}
