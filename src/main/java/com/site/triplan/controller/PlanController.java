package com.site.triplan.controller;

import com.site.triplan.service.PlanService;
import com.site.triplan.vo.AreaVo;
import com.site.triplan.vo.AttractionVo;

import com.site.triplan.vo.PlanVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    // 일정 모달창
    @GetMapping("/planModal")
    public String showMap(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String session_id = (String)session.getAttribute("session_id");

        String view = "";
        if (session_id == null) {
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            List<AreaVo> areaList = new ArrayList<>();
            areaList = planService.getAreaInfo();
            model.addAttribute("areaList", areaList);
            view = "user_plan_modal";
        }
        return view;
    }

    // 일정 작성폼
    @GetMapping("/planinsertform")
    public String show_planInsertForm(Model model, HttpServletRequest request, @RequestParam(required=false, defaultValue="")String areaCode) {
        HttpSession session = request.getSession();
        String session_id = (String)session.getAttribute("session_id");

        String view = "";
        if (session_id == null) {
            view = "redirect:/triplan/loginform?errCode=2";
        } else {

            // 본인이 받은 api키를 추가
            String key = "8cHlpbteCRtJBou8%2FnhQqhIndcUFnxYvrzPQFJQKsjbtXDA9Z%2BGwQuRNTaWcXfSWgyZe2cE1Fh4K8KyncXYj%2Fw%3D%3D";

            List<AttractionVo> tourList = new ArrayList<>();
            List<AreaVo> areaList = new ArrayList<>();
            areaList = planService.getAreaInfo();

            try {
                String url = "http://apis.data.go.kr/B551011/KorService/areaBasedList?serviceKey=" + key + "&pageNo=1&numOfRows=25&MobileApp=AppTest&MobileOS=WIN&arrange=B&contentTypeId=12&areaCode=" + areaCode;

                DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                Document doc = dBuilder.parse(url);

                // 제일 첫번째 태그
                doc.getDocumentElement().normalize();

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("item");

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    Element eElement = (Element) nNode;

                    AttractionVo attraction = new AttractionVo();
                    attraction.setName(getTagValue("title", eElement));
                    attraction.setLoc_x(getTagValue("mapy", eElement));
                    attraction.setLoc_y(getTagValue("mapx", eElement));
                    attraction.setImgPath(getTagValue("firstimage", eElement));

                    tourList.add(attraction);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            model.addAttribute("areaList", areaList);
            model.addAttribute("tourList", tourList);
            view = "user_plan_insertform";
        }
        return view;
    }

    // 시군구 리스트
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

    // 관광지 리스트
    @GetMapping("/searchSpotList")
    public @ResponseBody List<AttractionVo> searchSpotList(@RequestParam String keyword, @RequestParam String areaCode, @RequestParam String sigunguCode) {
        String key = "8cHlpbteCRtJBou8%2FnhQqhIndcUFnxYvrzPQFJQKsjbtXDA9Z%2BGwQuRNTaWcXfSWgyZe2cE1Fh4K8KyncXYj%2Fw%3D%3D";
        List<AttractionVo> tourList = new ArrayList<>();
        String url = "";
        try{

            if (keyword == "") {
                url = "http://apis.data.go.kr/B551011/KorService/areaBasedList?serviceKey=" + key + "&pageNo=1&numOfRows=25&MobileApp=AppTest&MobileOS=WIN&arrange=B&contentTypeId=12&areaCode=" + areaCode + "&sigunguCode=" + sigunguCode;
            } else {
                String encodeKeyword = URLEncoder.encode(keyword,"utf-8");
                url = "http://apis.data.go.kr/B551011/KorService/searchKeyword?numOfRows=25&pageNo=1&MobileOS=ETC&MobileApp=AppTest&ServiceKey=" + key + "&arrange=B&contentTypeId=12&areaCode=" + areaCode + "&sigunguCode=" + sigunguCode + "&cat1=&cat2=&cat3=&keyword=" + encodeKeyword;
            }

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

    // 일정 작성
    @PostMapping("/planinsertform")
    public String insertPlan(Model model, PlanVo plan, AttractionVo place) {
        System.out.println(plan.getTitle());
        System.out.println(place.getName());
        Integer plan_code = planService.insertPlan(plan, place);
        return "redirect:/triplan/plandetail?code=" + plan_code;
    }

    // 일정 상세보기
    @GetMapping("/plandetail")
    public String showPlanDetail() {
        return "user_plan_detail";
    }
}
