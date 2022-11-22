package com.site.triplan.controller;

import com.site.triplan.vo.AttractionVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

@Controller
@RequestMapping("/triplan")
public class PlanController {

    // map 테스트 중 ------------------------------------------------------------------------------
    @RequestMapping("/map")
    public String showMap() {
        return "test_map";
    }
    // ------------------------------------------------------------------------------ map 테스트 중

    // tourlist 테스트 중 -------------------------------------------------------------------------
    // tag값의 정보를 가져오는 함수
    public static String getTagValue(String tag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        result = nlList.item(0).getTextContent();

        return result;
    }

    // tag값의 정보를 가져오는 함수
    public static String getTagValue(String tag, String childTag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        for(int i = 0; i < eElement.getElementsByTagName(childTag).getLength(); i++) {

            //result += nlList.item(i).getFirstChild().getTextContent() + " ";
            result += nlList.item(i).getChildNodes().item(0).getTextContent() + " ";
        }

        return result;
    }

    //@RequestMapping("/tourlist")
    @RequestMapping("/planinsertform")
    public String show_planInsertForm(Model model) {
        // 본인이 받은 api키를 추가
        String key = "8cHlpbteCRtJBou8%2FnhQqhIndcUFnxYvrzPQFJQKsjbtXDA9Z%2BGwQuRNTaWcXfSWgyZe2cE1Fh4K8KyncXYj%2Fw%3D%3D";

        ArrayList<AttractionVo> tourList = new ArrayList<>();

        try{
            // parsing할 url 지정(API 키 포함해서)
            //ex1) 제주도 전체
            //String url = "http://apis.data.go.kr/B551011/KorService/areaBasedList?serviceKey=" + key + "&pageNo=1&numOfRows=10&MobileApp=AppTest&MobileOS=WIN&arrange=P&contentTypeId=12&areaCode=39";
            //ex2) 제주-제주시
            String url = "http://apis.data.go.kr/B551011/KorService/areaBasedList?serviceKey=" + key + "&pageNo=1&numOfRows=10&MobileApp=AppTest&MobileOS=WIN&arrange=P&contentTypeId=12&areaCode=39&sigunguCode=4";

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("item");

            for(int temp = 0; temp < nList.getLength(); temp++){
                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

                AttractionVo attraction = new AttractionVo();
                attraction.setName(getTagValue("title", eElement));
                attraction.setLoc_x(getTagValue("mapy", eElement));
                attraction.setLoc_y(getTagValue("mapx", eElement));
                attraction.setImgPath(getTagValue("firstimage", eElement));

                tourList.add(attraction);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("tourList", tourList);
        return "user_plan_insertform";
    }
    // ------------------------------------------------------------------------- tourlist 테스트 중

    @RequestMapping("/plandetail")
    public String showPlanDetail() {
        return "user_plan_detail";
    }
}
