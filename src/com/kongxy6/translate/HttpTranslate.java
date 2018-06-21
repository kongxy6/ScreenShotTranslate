package com.kongxy6.translate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kongxy6.lang.LANG;
import com.kongxy6.util.Util;

public class HttpTranslate {
	public String url;

	public Map<String, String> formData;

	public Map<LANG, String> langMap;

	public CloseableHttpClient httpClient;

	/**
	 * 使用搜狗进行翻译
	 */
	public HttpTranslate(LANG from, LANG to, String text) {
		// TODO Auto-generated constructor stub
		this.url = "http://fanyi.sogou.com/reventondc/translate";

		this.langMap = new HashMap<>();
		this.setLangSupport();

		this.formData = new HashMap<>();
		this.setFormData(from, to, text);

		this.httpClient = HttpClients.createDefault();
	}

	public void setFormData(LANG from, LANG to, String text) {
		formData.put("from", langMap.get(from));
		formData.put("to", langMap.get(to));
		formData.put("client", "pc");
		formData.put("fr", "browser_pc");
		formData.put("text", text);
		formData.put("useDetect", "on");

		if (from == LANG.AUTO) {
			formData.put("useDetectResult", "on");
		} else {
			formData.put("useDetectResult", "off");
		}

		formData.put("needQc", "1");
		formData.put("uuid", token());
		formData.put("oxford", "on");
		formData.put("isReturnSugg", "off");
	}

	public String query() throws Exception {
		HttpPost request = new HttpPost(Util.getUrlWithQueryString(url, formData));
		CloseableHttpResponse httpResponse = httpClient.execute(request);
		HttpEntity httpEntity = httpResponse.getEntity();
		String result = EntityUtils.toString(httpEntity, "UTF-8");
		EntityUtils.consume(httpEntity);
		httpResponse.close();
		return result;
	}

	private String query(String text) throws Exception, IOException {
		formData.put("text", text);
		formData.put("uuid", token());
		HttpPost request = new HttpPost(Util.getUrlWithQueryString(url, formData));
		CloseableHttpResponse httpResponse = httpClient.execute(request);
		HttpEntity httpEntity = httpResponse.getEntity();
		String result = EntityUtils.toString(httpEntity, "UTF-8");
		EntityUtils.consume(httpEntity);
		httpResponse.close();
		return result;
	}

	public TranslateResEntity resolve(String text) throws IOException {
		TranslateResEntity translateResEntity = new TranslateResEntity();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jNode = mapper.readTree(text);
		if (!jNode.path("dictionary").isMissingNode()) {
			translateResEntity.setText(removeMarks(jNode.path("translate").path("text").toString()));
			int counts = jNode.path("dictionary").path("sugg").path(0).path("usual").size();
			for (int i = 0; i < counts; ++i) {
				translateResEntity.add(
						removeMarks(jNode.path("dictionary").path("sugg").path(0).path("usual").path(i).path("pos")
								.toString()),
						removeMarks(jNode.path("dictionary").path("sugg").get(0).path("usual").get(i).path("values")
								.get(0).toString()));
			}
			return translateResEntity;
		} else if (!jNode.path("translate").path("qc_text").isMissingNode()) {
			translateResEntity.setText(removeMarks(jNode.path("translate").path("qc_text").toString()));
			try {
				translateResEntity = resolve(query(translateResEntity.getText()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("重查询失败");
				e.printStackTrace();
			}
			return translateResEntity;
		} else {
			return translateResEntity;
		}
	}

	private String token() {
		String result = "";
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
		try {
			FileReader reader = new FileReader("./tk/Sogou.js");
			engine.eval(reader);
			if (engine instanceof Invocable) {
				Invocable invoke = (Invocable) engine;
				result = String.valueOf(invoke.invokeFunction("token"));
			}
		} catch (ScriptException | NoSuchMethodException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void setLangSupport() {
		langMap.put(LANG.AUTO, "auto");
		langMap.put(LANG.ZH, "zh-CHS");
		langMap.put(LANG.EN, "en");
		langMap.put(LANG.JP, "ja");
		langMap.put(LANG.KOR, "ko");
		langMap.put(LANG.FRA, "fr");
		langMap.put(LANG.RU, "ru");
		langMap.put(LANG.DE, "de");
	}

	public void closeClient() throws IOException {
		httpClient.close();
	}

	private String removeMarks(String oldString) {
		String newString = "";
		for (int i = 1; i < oldString.length() - 1; ++i) {
			newString += oldString.charAt(i);
		}
		return newString;
	}
}
