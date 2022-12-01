package com.site.triplan.controller;

import com.site.triplan.service.PlanService;
import com.site.triplan.vo.AreaVo;
import com.site.triplan.vo.AttractionVo;

import com.site.triplan.vo.PlanVo;
import com.site.triplan.vo.ReplyVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

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

    // 메인: 지역
    @GetMapping("/main")
    public String user_main(Model model) {
        List<AreaVo> areaList = new ArrayList<>();
        areaList = planService.getAreaInfo();
        model.addAttribute("areaList", areaList);
        return "user_main";
    }

    // <일정보기>
    @GetMapping("/planlist")
    public String planlist(Model model, @RequestParam(required=false, defaultValue="")String code) {
        if (code.isEmpty())
            code = "%";
        List<AreaVo> areaList = new ArrayList<>();
        List<PlanVo> planList = new ArrayList<>();
        areaList = planService.getAreaInfo();
        planList = planService.getPlanList(code);
        model.addAttribute("areaList", areaList);
        model.addAttribute("planList", planList);
        return "user_plan_list";
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
    //--AttractionVo들이 넘어오는걸 name 속성을 이용해 배열로 받을 예정
    @PostMapping("/planinsertform")
    public String insertPlan(Model model, HttpServletRequest request, PlanVo plan
                           , @RequestParam("name") String[] names, @RequestParam("memo") String[] memos
                           , @RequestParam("loc_x") String[] loc_xs, @RequestParam("loc_y") String[] loc_ys
                           , @RequestParam("photo_path") String[] photo_paths, @RequestParam("day") Integer[] days
                           , @RequestParam("order") Integer[] orders) {

        HttpSession session = request.getSession();
        String session_id = (String)session.getAttribute("session_id");

        String view = "";
        if (session_id == null) {
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            int user_code = planService.getUserCodeById(session_id);
            plan.setUser_code(user_code);

            List<AttractionVo> placeList = new ArrayList<>();
            for (int i = 0; i < names.length; i++) {
                AttractionVo place = new AttractionVo();
                place.setName(names[i]);
                place.setMemo(memos[i]);
                place.setLoc_x(loc_xs[i]);
                place.setLoc_y(loc_ys[i]);
                place.setPhoto_path(photo_paths[i]);
                place.setDay(days[i]);
                place.setOrder(orders[i]);

                placeList.add(place);
            }

            String new_plan_code = planService.insertPlan(plan, placeList);
            if (new_plan_code == null) {
                view = "redirect:/triplan/planinsertform?errCode=1";
            } else {

                view = "redirect:/triplan/plandetail?code=" + new_plan_code;
            }
        }

        return view;
    }

    // 일정 상세보기
    @GetMapping("/plandetail")
    public String showPlanDetail(Model model, HttpServletRequest request, @RequestParam(required=true)String code) {
        HttpSession session = request.getSession();
        String session_id = (String)session.getAttribute("session_id");

        PlanVo plan = planService.getPlanDetail(code);
        List<AttractionVo> placeList = new ArrayList<>();
        List<ReplyVo> replyList = new ArrayList<>();
        if (session_id != null) {
            int user_code = planService.getUserCodeById(session_id);
            int isLike = planService.isLike(code, user_code);
            if (isLike > 0)
                model.addAttribute("isLike", isLike);

            model.addAttribute("now_user_id", session_id);
        }
        placeList = planService.getPlaceList(code);
        replyList = planService.getReplyList(code);
        model.addAttribute("plan", plan);
        model.addAttribute("placeList", placeList);
        model.addAttribute("replyList", replyList);
        return "user_plan_detail";
    }

    // 좋아요 등록
    @GetMapping("/insertLike")
    public String insertLike(Model model, HttpServletRequest request, @RequestParam String code) {
        HttpSession session = request.getSession();
        String session_id = (String)session.getAttribute("session_id");

        String view = "";
        if (session_id == null) {
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            int user_code = planService.getUserCodeById(session_id);
            int result = planService.insertLike(code, user_code);
            if (result > 0)
                view = "redirect:/triplan/plandetail?code=" + code;
            else
                view = "redirect:/triplan/plandetail?code=" + code + "&errCode=1";
        }
        return view;
    }
    // 좋아요 해제
    @GetMapping("/deleteLike")
    public String deleteLike(Model model, HttpServletRequest request, @RequestParam String code) {
        HttpSession session = request.getSession();
        String session_id = (String)session.getAttribute("session_id");

        String view = "";
        if (session_id == null) {
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            int user_code = planService.getUserCodeById(session_id);
            int result = planService.deleteLike(code, user_code);
            if (result > 0)
                view = "redirect:/triplan/plandetail?code=" + code;
            else
                view = "redirect:/triplan/plandetail?code=" + code + "&errCode=1";
        }
        return view;
    }

    // 댓글 등록
    @PostMapping("/reply")
    public String insertReply(Model model, HttpServletRequest request, ReplyVo reply) {
        HttpSession session = request.getSession();
        String session_id = (String)session.getAttribute("session_id");

        String view = "";
        if (session_id == null) {
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            int user_code = planService.getUserCodeById(session_id);
            reply.setUser_code(Integer.toString(user_code));
            int result = planService.insertReply(reply);
            if (result > 0)
                view = "redirect:/triplan/plandetail?code=" + reply.getPlan_code() + "&focus=re";
            else
                view = "redirect:/triplan/plandetail?code=" + reply.getPlan_code() + "&focus=re&errCode=1";
        }
        return view;
    }

    // 댓글 수정
    @PostMapping("/updateReply")
    public String updateReply(Model model, HttpServletRequest request, ReplyVo reply) {
        HttpSession session = request.getSession();
        String session_id = (String)session.getAttribute("session_id");

        String view = "";
        if (session_id == null) {
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            int user_code = planService.getUserCodeById(session_id);
            reply.setUser_code(Integer.toString(user_code));
            int result = planService.updateReply(reply);
            if (result > 0)
                view = "redirect:/triplan/plandetail?code=" + reply.getPlan_code() + "&focus=re";
            else
                view = "redirect:/triplan/plandetail?code=" + reply.getPlan_code() + "&focus=re&errCode=1";
        }
        return view;
    }

    // 댓글 삭제
    @GetMapping("/deleteReply/{plan_code}/{code}")
    public String deleteReply(Model model, HttpServletRequest request, ReplyVo reply) {
        HttpSession session = request.getSession();
        String session_id = (String)session.getAttribute("session_id");
        String view = "";
        if (session_id == null) {
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            int user_code = planService.getUserCodeById(session_id);
            reply.setUser_code(Integer.toString(user_code));
            int result = planService.deleteReply(reply);
            if (result > 0)
                view = "redirect:/triplan/plandetail?code=" + reply.getPlan_code() + "&focus=re";
            else
                view = "redirect:/triplan/plandetail?code=" + reply.getPlan_code() + "&focus=re&errCode=1";
        }
        return view;
    }
}
