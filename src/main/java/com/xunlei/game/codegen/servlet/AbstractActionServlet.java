package com.xunlei.game.codegen.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunlei.game.api.Result;
import com.xunlei.game.api.ResultException;
import com.xunlei.game.codegen.constants.Action;
import com.xunlei.game.codegen.util.LoggerUtil;
import com.xunlei.game.kit.entry.MapEntry;
import com.xunlei.game.util.JsonUtil;

public class AbstractActionServlet extends HttpServlet implements LoggerUtil {

	private static final long serialVersionUID = -5543931705435898578L;
	public static final String ACTIN_NAME = "action";
	private static final int MAX_LENGTH = 20480;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Result result = Result.OK;
		MapEntry request = null;
		String response = null;
		String actionName = null;
		try {
			request = createRequest(req);
			actionName = req.getParameter(ACTIN_NAME);
			Action action = null;
			try {
				action = Action.valueOf(actionName);
			} catch (Exception e) {
				action = Action.other;
			}
			switch (action) {
			case add:
				String id = add(request);
				response = "{\"result\":" + result.getResult() + ",\"data\":{\"id\":\"" + id + "\"},\"errinfo\":\""
						+ result.getMessage() + "\"}";
				break;
			case update:
				result = update(request);
				response = toJson(result);
				break;
			case delete:
				result = delete(request);
				response = toJson(result);
				break;
			case deletelist:
				result = deleteList(request);
				response = toJson(result);
				break;
			case list:
				int total = total(request);
				if (total == 0) {
					response = "{\"result\":" + result.getResult() + ",\"data\":[],\"total\":0,\"errinfo\":\""
							+ result.getMessage() + "\"}";
					break;
				}
				List<Object> data = list(request);
				response = "{\"result\":" + result.getResult() + ",\"data\":" + JsonUtil.encode(data) + ",\"total\":" + total
						+ ",\"errinfo\":\"" + result.getMessage() + "\"}";
				break;
			case view:
				Map<String, Object> dataMap = view(request);
				response = "{\"result\":" + result.getResult() + ",\"data\":" + JsonUtil.encode(dataMap) + ",\"errinfo\":\""
						+ result.getMessage() + "\"}";
				break;
			case viewlist:
				List<Object> viewList = viewList(request);
				if (viewList == null || viewList.isEmpty()) {
					response = "{\"result\":" + result.getResult() + ",\"data\":[],\"total\":0,\"errinfo\":\""
							+ result.getMessage() + "\"}";
					break;
				}
				response = "{\"result\":" + result.getResult() + ",\"data\":" + JsonUtil.encode(viewList) + ",\"total\":" + viewList.size()
						+ ",\"errinfo\":\"" + result.getMessage() + "\"}";
				break;
			case other:
				response = handle(req, resp, actionName, request);
				break;
			default:
				break;
			}
		} catch (IllegalArgumentException e) {
			response = toJson(Result.PARAM_ERROR);
		} catch (NullPointerException e) {
			response = toJson(Result.PARAM_ERROR);
		} catch (SQLException e) {
			response = toJson(Result.DB_ERROR);
		} catch (ResultException e) {
			response = toJson(e.getResult());
		} catch (Exception e) {
			response = toJson(Result.INTERNAL_SERVER_ERROR);
			servletLog.error("请求异常：{}", e);
			e.printStackTrace();
		} finally {
			servletLog.info("{}:{};{}-->{}", new Object[] { req.getServletPath(), actionName, request, response });
			resp.getWriter().write(response);
		}
	}

	protected MapEntry createRequest(HttpServletRequest req) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = req.getInputStream();
		byte[] cache = new byte[MAX_LENGTH];
		for (;;) {
			int length = is.read(cache);
			if (length == MAX_LENGTH) {
				baos.write(cache, 0, length);
				continue;
			}
			if (length > 0) {
				baos.write(cache, 0, length);
			}
			break;
		}
		String request = baos.toString("UTF-8");
		Map<String, Object> map = null;
		if (request != null && !request.isEmpty()) {
			map = JsonUtil.toMap(request);
		} else {
			map = new HashMap<String, Object>();
		}
		return new MapEntry(map);
	}

	protected String add(MapEntry params) throws SQLException, ResultException {
		throw new UnsupportedOperationException("add");
	}

	protected Result update(MapEntry params) throws SQLException {
		throw new UnsupportedOperationException("update");
	}

	protected Result delete(MapEntry params) throws SQLException {
		throw new UnsupportedOperationException("delete");
	}

	protected Result deleteList(MapEntry params) throws SQLException {
		throw new UnsupportedOperationException("deleteList");
	}

	protected int total(MapEntry params) throws SQLException, ResultException {
		throw new UnsupportedOperationException("total");
	}

	protected List<Object> list(MapEntry params) throws SQLException, ResultException {
		throw new UnsupportedOperationException("list");
	}

	protected Map<String, Object> view(MapEntry params) throws SQLException, ResultException {
		throw new UnsupportedOperationException("view");
	}

	protected List<Object> viewList(MapEntry params) throws SQLException, ResultException {
		throw new UnsupportedOperationException("viewList");
	}

	protected String handle(HttpServletRequest req, HttpServletResponse resp, String actionName, MapEntry params) throws Exception {
		throw new UnsupportedOperationException("other");
	}

	protected String toJson(Result result) {
		return "{\"result\":" + result.getResult() + ",\"errinfo\":\"" + result.getMessage() + "\"}";
	}

}
