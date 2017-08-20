package com.xasxt.tickect.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.xasxt.tickect.service.RouteService;
import com.xasxt.tickect.vo.AjaxResp;
import com.xasxt.tickect.vo.RouteInfo;
import com.xasxt.tickect.vo.StationFeeInfo;

public class RouteAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(RouteAction.class);
	@Resource
	private RouteService routeService;
	
	 RouteInfo routeInfo;
	
	public RouteInfo getRouteInfo() {
		return routeInfo;
	}

	public void setRouteInfo(RouteInfo routeInfo) {
		this.routeInfo = routeInfo;
	}

	/**
	 * ��ȡ��·�б�
	 * @return
	 */
	public String list(){
		
		List<RouteInfo> list= this.routeService.getList();
		super.save2valueStack("list", list);
		return SUCCESS;
	}
	
	/**
	 * ���·�����Ƿ�Ϸ�
	 * @return
	 */
	public String checkRoute(){
		AjaxResp resp= new AjaxResp();
		if(routeInfo.getRouteName()==null||routeInfo.getRouteName().length()==0){
			resp.setCode(-2);
			resp.setMessage("δ�ṩ��Ч����!");
		}else{
			try{
				RouteInfo Info=this.routeService.getInfoByName(routeInfo.getRouteName());
				if(Info!=null){
					resp.setResult(false);
				}else{
					resp.setResult(true);
				}
			}catch(Exception e){
				resp.setCode(-99);
				resp.setMessage(e.getMessage());
			}
		}
		super.save2valueStack("result", resp);
		return SUCCESS;
	}
	/**
	 * ����·��
	 * @return
	 */
	public String addRoute(){
		System.out.println("����:"+routeInfo.getRouteName()+","+routeInfo.getStartStation()+","+routeInfo.getTerminalStation()+","
				+routeInfo.getBaggaceFee()+","+routeInfo.getPrice());
		if(routeInfo!=null){
			boolean result=this.routeService.addRoute(routeInfo);
			if(result)
			return SUCCESS;
			else{
				addActionMessage("������¼��");
				return "false";
			}
		}else{
			addActionError("�����쳣");
			return "false";
		}
	}
	/**
	 * ��ȡ��·;��վ���б�
	 * 
	 * @return
	 */
	public String ajaxByWayList() {
	//	System.out.println("ajax�ģ�"+this.routeInfo.getRouteId());
		AjaxResp resp = new AjaxResp();
		if (this.routeInfo == null || this.routeInfo.getRouteId() == null) {
			resp.setCode(-2);
			resp.setMessage("δ�ύ��Ч�Ĳ���!");
		} else {
			try {
				List<StationFeeInfo> statList = routeService.getByWayList(this.routeInfo.getRouteId());
				resp.setResult(statList);
			} catch (Exception e) {
				logger.error(e.getMessage());
				resp.setCode(-99);
				resp.setMessage(e.getMessage());
			}
		}
	
		super.save2valueStack("result", resp);
		return SUCCESS;
	}
	
}