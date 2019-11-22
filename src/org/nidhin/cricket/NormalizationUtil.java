package org.nidhin.cricket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NormalizationUtil {
	public static Map<String,String> normalizedStadiumMap = new HashMap<>();
    static {
         List<String> stadiumList = new ArrayList<>();
        stadiumList.add("Sir Vivian Richards Stadium North Sound");
        stadiumList.add("Darren Sammy National Cricket Stadium Gros Islet");
        stadiumList.add("Central Broward Regional Park Stadium Turf Ground");
        stadiumList.add("Rangiri Dambulla International Stadium");
        stadiumList.add("R Premadasa Stadium");
        stadiumList.add("SuperSport Park");
        stadiumList.add("New Wanderers Stadium");
        stadiumList.add("Reliance Stadium");
        stadiumList.add("Punjab Cricket Association Stadium Mohali");
        stadiumList.add("Madhavrao Scindia Cricket Ground");
        stadiumList.add("Vidarbha Cricket Association Stadium Jamtha");
        stadiumList.add("Eden Gardens");
        stadiumList.add("Shere Bangla National Stadium");
        stadiumList.add("M Chinnaswamy Stadium");
        stadiumList.add("Feroz Shah Kotla");
        stadiumList.add("MA Chidambaram Stadium Chepauk");
        stadiumList.add("Sardar Patel Stadium Motera");
        stadiumList.add("Wankhede Stadium");
        stadiumList.add("Sawai Mansingh Stadium");
        stadiumList.add("Queens Sports Club");
        stadiumList.add("Harare Sports Club");
        stadiumList.add("Moses Mabhida Stadium");
        stadiumList.add("Kingsmead");
        stadiumList.add("Newlands");
        stadiumList.add("St George's Park");
        stadiumList.add("Dr. Y.S. Rajasekhara Reddy ACA-VDCA Cricket Stadium");
        stadiumList.add("Nehru Stadium");
        stadiumList.add("Old Trafford");
        stadiumList.add("Riverside Ground");
        stadiumList.add("The Rose Bowl");
        stadiumList.add("Kennington Oval");
        stadiumList.add("Lord's");
        stadiumList.add("Sophia Gardens");
        stadiumList.add("Queen's Park Oval Port of Spain");
        stadiumList.add("Sabina Park Kingston");
        stadiumList.add("Windsor Park Roseau");
        stadiumList.add("Sydney Cricket Ground");
        stadiumList.add("Western Australia Cricket Association Ground");
        stadiumList.add("Adelaide Oval");
        stadiumList.add("Stadium Australia");
        stadiumList.add("Melbourne Cricket Ground");
        stadiumList.add("Brisbane Cricket Ground Woolloongabba");
        stadiumList.add("Bellerive Oval");
        stadiumList.add("Rajiv Gandhi International Stadium Uppal");
        stadiumList.add("Barabati Stadium");
        stadiumList.add("Holkar Cricket Stadium");
        stadiumList.add("Mahinda Rajapaksa International Cricket Stadium Sooriyawewa");
        stadiumList.add("Pallekele International Cricket Stadium");
        stadiumList.add("Subrata Roy Sahara Stadium");
        stadiumList.add("Saurashtra Cricket Association Stadium");
        stadiumList.add("JSCA International Stadium Complex");
        stadiumList.add("Edgbaston");
        stadiumList.add("Maharashtra Cricket Association Stadium");
        stadiumList.add("Seddon Park");
        stadiumList.add("Eden Park");
        stadiumList.add("McLean Park");
        stadiumList.add("Westpac Stadium");
        stadiumList.add("Basin Reserve");
        stadiumList.add("Trent Bridge");
        stadiumList.add("Headingley");
        stadiumList.add("Green Park");
        stadiumList.add("Khan Shaheb Osman Ali Stadium");
        stadiumList.add("Himachal Pradesh Cricket Association Stadium");
        stadiumList.add("Galle International Stadium");
        stadiumList.add("P Sara Oval");
        stadiumList.add("Sinhalese Sports Club Ground");
        stadiumList.add("Manuka Oval");
        stadiumList.add("Punjab Cricket Association IS Bindra Stadium Mohali");
        stadiumList.add("Arbab Niaz Stadium");
        stadiumList.add("Rawalpindi Cricket Stadium");
        stadiumList.add("Gaddafi Stadium");
        stadiumList.add("Kinrara Academy Oval");
        stadiumList.add("County Ground");
        stadiumList.add("Indian Petrochemicals Corporation Limited Sports Complex Ground");
        stadiumList.add("Nehru Stadium Fatorda");
        stadiumList.add("Civil Service Cricket Club Stormont");
        stadiumList.add("Sector 16 Stadium");
        stadiumList.add("Vidarbha Cricket Association Ground");
        stadiumList.add("Captain Roop Singh Stadium");
        stadiumList.add("P Saravanamuttu Stadium");
        stadiumList.add("AMI Stadium");
        stadiumList.add("Zahur Ahmed Chowdhury Stadium");

        for(int i=0;i<stadiumList.size();i++){
            normalizedStadiumMap.put(stadiumList.get(i),"STAD_"+(i+1));
        }
    }

}
