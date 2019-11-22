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

public class IndividualPerformance {
	private static final String[] quarters = { "Q1", "Q1", "Q1", "Q2", "Q2",
			"Q2", "Q3", "Q3", "Q3", "Q4", "Q4", "Q4" };
	private static StringBuffer csv_data = new StringBuffer();
	private static final String[] batsmen = { "Kohli" };

	public static void main(String... args) throws IOException {
		Path path = Paths.get("/home/nidhin/Desktop/poc");

		for (String batsman : batsmen) {
			try (DirectoryStream<Path> ds = Files.newDirectoryStream(path,
					"*.yaml")) {
				Iterator<Path> iterator = ds.iterator();
				System.out.println(iterator.toString());
				int c = 0;
				while (iterator.hasNext()) {
					Path filePath = iterator.next();
					convertFile(filePath, batsman);
					c++;
				}
			}
		}

		Files.write(
				Paths.get("/home/nidhin/Desktop/POCCricket/individualPerformance.csv"),
				csv_data.toString().getBytes());

	}

	private static void convertFile(Path filePath, String batsmanName) {
		String yamlContent = null;

		try {
			yamlContent = new String(Files.readAllBytes(filePath));
			Yaml yaml = new Yaml();
			Map<String, Object> map = (Map<String, Object>) yaml
					.load(yamlContent);
			Map<String, Object> matchInfo = ((Map) map.get("info"));
			ArrayList<String> teams = (ArrayList<String>) matchInfo
					.get("teams");
			String firstTeam = teams.get(0);
			String secondTeam = teams.get(1);
			String opposite_team = ("india").equalsIgnoreCase(firstTeam) ? secondTeam
					: firstTeam;

			String winning_team = (String) ((Map) matchInfo.get("outcome"))
					.get("winner");
			String result = ("india").equalsIgnoreCase(winning_team) ? "INDIA_WON"
					: "INDIA_LOST";
			String location = (String) matchInfo.get("venue");
			location = location.replace(",", "");
			location = NormalizationUtil.normalizedStadiumMap.get(location);
			Date matchDate = (Date) (((ArrayList<Date>) matchInfo.get("dates"))
					.get(0));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(matchDate);
			int month = calendar.get(Calendar.MONTH);
			String quarter_of_year = quarters[month];

			Map firstInningsMap = (Map) ((Map) ((ArrayList) map.get("innings"))
					.get(0)).get("1st innings");
			String firstInningsTeam = (String) firstInningsMap.get("team");

			ArrayList firstInningDeliveries = (ArrayList) firstInningsMap
					.get("deliveries");

			Map secondInningsMap = (Map) ((Map) ((ArrayList) map.get("innings"))
					.get(1)).get("2nd innings");
			String secondInningsTeam = (String) secondInningsMap.get("team");

			ArrayList secondInningDeliveries = (ArrayList) secondInningsMap
					.get("deliveries");

			boolean chasing = ("india").equalsIgnoreCase(firstInningsTeam) ? false
					: true;

			ArrayList indiaInningDeliveries = firstInningDeliveries;
			if (chasing) {
				indiaInningDeliveries = secondInningDeliveries;
			}
			int totalIndiaRuns = 0;
			int batsmanRuns = 0;

			int batsmanScoreIn10Overs = 0;
			int indiaScoreIn10Overs = 0;
			int batsmanScoreIn20Overs = 0;
			int indiaScoreIn20Overs = 0;
			int batsmanScoreIn30Overs = 0;
			int indiaScoreIn30Overs = 0;
			int batsmanScoreIn40Overs = 0;
			int indiaScoreIn40Overs = 0;
			int batsmanScoreIn50Overs = 0;
			int indiaScoreIn50Overs = 0;

			for (Object deliveryDetailObj : indiaInningDeliveries) {
				Map deliveryDetail = (Map) deliveryDetailObj;

				for (Object entry : deliveryDetail.entrySet()) {
					Map.Entry mapEntry = (Map.Entry) entry;
					DecimalFormat df = new DecimalFormat("#.#");
					df.setRoundingMode(RoundingMode.CEILING);
					double ballOfover = (Double) mapEntry.getKey();
					String ballOfOverStr = df.format(ballOfover).toString();
					Map ballDetailMap = (Map) mapEntry.getValue();
					String batsman = (String) ballDetailMap.get("batsman");
					String bowler = (String) ballDetailMap.get("batsman");

					Map runsMap = (Map) ballDetailMap.get("runs");
					int batsmanRunsThisBall = (Integer) runsMap.get("batsman");
					int extraRunsThisBall = (Integer) runsMap.get("extras");
					int totalRunsInThisBall = (Integer) runsMap.get("total");

					totalIndiaRuns = totalIndiaRuns + totalRunsInThisBall;

					if (batsman.contains(batsmanName)) {
						batsmanRuns = batsmanRuns + batsmanRunsThisBall;
					}

					if (ballOfover <= 9.5) {
						batsmanScoreIn10Overs = batsmanRuns;
						indiaScoreIn10Overs = totalIndiaRuns;

					}
					if (ballOfover <= 19.5) {
						batsmanScoreIn20Overs = batsmanRuns;
						indiaScoreIn20Overs = totalIndiaRuns;
					}
					if (ballOfover <= 29.5) {
						batsmanScoreIn30Overs = batsmanRuns;
						indiaScoreIn30Overs = totalIndiaRuns;
					}
					if (ballOfover <= 39.5) {
						batsmanScoreIn40Overs = batsmanRuns;
						indiaScoreIn40Overs = totalIndiaRuns;
					}
					if (ballOfover <= 50 || ballOfover <= 49.6) {
						batsmanScoreIn50Overs = batsmanRuns;
						indiaScoreIn50Overs = totalIndiaRuns;
					}
				}
				String runsTaken = (String) deliveryDetail.get("runs");
			}
			boolean batsmanPlayed = true;
			if (batsmanScoreIn10Overs == 0 && batsmanScoreIn20Overs == 0
					&& batsmanScoreIn30Overs == 0 && batsmanScoreIn40Overs == 0
					&& batsmanScoreIn50Overs == 0) {
				batsmanPlayed = false;
			}
			if (batsmanPlayed) {
				csv_data.append(batsmanName + ", " + opposite_team + ", "
						+ location + ", " + matchDate.toString() + ", "
						+ quarter_of_year + ", " + chasing + ", "
						+ batsmanScoreIn10Overs + ", " + indiaScoreIn10Overs
						+ ", " + batsmanScoreIn20Overs + ", "
						+ indiaScoreIn20Overs + ", " + batsmanScoreIn30Overs
						+ ", " + indiaScoreIn30Overs + ", "
						+ batsmanScoreIn40Overs + ", " + indiaScoreIn40Overs
						+ ", " + batsmanScoreIn50Overs + ", "
						+ indiaScoreIn50Overs + ", " + result + "\n");
			}

		} catch (Exception e) {
			return;
		}

	}
}
