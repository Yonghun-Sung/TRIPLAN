package com.site.triplan.controller;

import com.site.triplan.service.PlanService;
import com.site.triplan.vo.AreaVo;
import com.site.triplan.vo.AttractionVo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.site.triplan.util.parsingXml.getTagValue;

@Controller
@RequestMapping("/triplan")
public class PlanController {
    private PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    // map 테스트 중 ------------------------------------------------------------------------------
    @RequestMapping("/map")
    public String showMap() {
        return "test_map";
    }
    // ------------------------------------------------------------------------------ map 테스트 중

    @GetMapping("/planinsertform")
    //public String show_planInsertForm(Model model, @RequestParam Integer areaCode) {
    public String show_planInsertForm(Model model) {
        // 본인이 받은 api키를 추가
        String key = "8cHlpbteCRtJBou8%2FnhQqhIndcUFnxYvrzPQFJQKsjbtXDA9Z%2BGwQuRNTaWcXfSWgyZe2cE1Fh4K8KyncXYj%2Fw%3D%3D";

        List<AttractionVo> tourList = new ArrayList<>();
        List<AreaVo> areaList = new ArrayList<>();
        areaList = planService.getAreaInfo();

        try{
            String url = "http://apis.data.go.kr/B551011/KorService/areaBasedList?serviceKey=" + key + "&pageNo=1&numOfRows=20&MobileApp=AppTest&MobileOS=WIN&arrange=B&contentTypeId=12&areaCode=6";

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

        model.addAttribute("areaList", areaList);
        model.addAttribute("tourList", tourList);
        return "user_plan_insertform";
    }
    // ------------------------------------------------------------------------- tourlist 테스트 중

    @GetMapping("/getSigunguList")
    public @ResponseBody List<AreaVo> getSigunguList(@RequestParam Integer areaCode) {
        String key = "8cHlpbteCRtJBou8%2FnhQqhIndcUFnxYvrzPQFJQKsjbtXDA9Z%2BGwQuRNTaWcXfSWgyZe2cE1Fh4K8KyncXYj%2Fw%3D%3D";
        List<AreaVo> sigunguList = new ArrayList<>();

        try{
            String url = "http://apis.data.go.kr/B551011/KorService/areaCode?serviceKey=" + key + "&areaCode=" + areaCode + "&numOfRows=50&pageNo=1&MobileOS=WIN&MobileApp=AppTest";

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

                AreaVo sigungu = new AreaVo();
                sigungu.setCode(getTagValue("code", eElement));
                sigungu.setName(getTagValue("name", eElement));
                sigunguList.add(sigungu);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return sigunguList;
    }

    @GetMapping("/searchSpotList")
    public @ResponseBody List<AttractionVo> searchSpotList(@RequestParam String keyword, @RequestParam String areaCode, @RequestParam String sigunguCode) {
        String key = "8cHlpbteCRtJBou8%2FnhQqhIndcUFnxYvrzPQFJQKsjbtXDA9Z%2BGwQuRNTaWcXfSWgyZe2cE1Fh4K8KyncXYj%2Fw%3D%3D";
        List<AttractionVo> tourList = new ArrayList<>();
        try{
            String encodeKeyword = URLEncoder.encode(keyword,"utf-8");
            String url = "http://apis.data.go.kr/B551011/KorService/searchKeyword?numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&ServiceKey=" + key + "&arrange=B&contentTypeId=12&areaCode=" + areaCode + "&sigunguCode=" + sigunguCode + "&cat1=&cat2=&cat3=&keyword=" + encodeKeyword;

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
        return tourList;
    }


    @GetMapping("/plandetail")
    public String showPlanDetail() {
        return "user_plan_detail";
    }
}
