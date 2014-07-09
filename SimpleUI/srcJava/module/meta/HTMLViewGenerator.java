package module.meta;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HTMLViewGenerator {

	/**
	 * Generates a view for an item which has its meta model. Needs a js
	 * function activateSubmit(boolean) that (de)activates the submit button
	 * 
	 * @param itemWithMeta
	 * @param table
	 *            of data typ
	 * @return HTML code of view of an item
	 */
	public static String generateItemView(JSONObject itemWithMeta) {

		try {
			JSONObject metaData = itemWithMeta
					.getJSONObject(MetaParser.META_DATA);

			if (!itemWithMeta.isNull(DataParser.ALL_DB_DATA)) {
				return generateItemView(
						itemWithMeta.get(DataParser.ALL_DB_DATA), metaData,
						metaData.getString(MetaParser.CLASS_NAME), "", false);
			}

		} catch (JSONException e) {

		}
		return "Error";
	}

	public static String generateItemView(Object itemJSON, JSONObject metaData,
			String table, String dept, boolean readOnly) {
		String result = "";
		int arrayIndex = 0;
		boolean form = false;
		try {
			// collect all fields which from the type dateLong
			List<String> readOnlyFields = MetaParser.getAllFieldsByParameter(
					metaData, MetaParser.FIELDS_READ_ONLY);

			// collect all fields which from the type urlImage
			List<String> urlImageFields = MetaParser.getAllFieldsByParameter(
					metaData, MetaParser.TYPE_URL_IMAGE);

			// collect all fields which from the type urlImage
			List<String> idFields = MetaParser.getAllFieldsByParameter(
					metaData, MetaParser.TYPE_ID);

			// collect complex fields for recursive calls
			List<String> complexFields = MetaParser
					.getAllComplexFields(metaData);

			JSONArray allDbData = new JSONArray();
			if (itemJSON instanceof JSONArray) {
				allDbData = (JSONArray) itemJSON;
			} else {
				allDbData.put(itemJSON);
			}

			for (int i = 0; i < allDbData.length(); i++) {
				JSONObject oneEntry;

				oneEntry = (JSONObject) allDbData.get(i);

				Iterator<?> keys = oneEntry.keys();

				if (dept == "") {
					result += "<form action='' method='post' id='form_edit_entry'>";
					result += "<input type=hidden name="
							+ MetaParser.META_TABLE + " value=" + table + " />";
					form = true;
				}

				result += "<table class='table_view'>";
				result += "<tr><th style='width:15%;'>Attribut</th><th>Wert</th></tr>";
				while (keys.hasNext()) {
					String key = keys.next().toString();

					result += "<tr><td>" + key + "</td><td>";

					if (complexFields.contains(key)) {
						JSONObject subMeta = MetaParser
								.getMetaFromComplexField(metaData, key);
						String newdept = dept + key + ".";
						result += generateItemView(oneEntry.get(key), subMeta,
								table, newdept, readOnlyFields.contains(key));
					} else {
						String htmlString = oneEntry.get(key).toString();
						result += "<INPUT type='text' name="
								+ dept
								+ ((dept != "") ? arrayIndex + "_" : "")
								+ key
								+ " onkeydown='activateSubmit(true)' style='width:100%;"
								+ ((readOnlyFields.contains(key)
										|| idFields.contains(key) || readOnly) ? "background:#eaeaea;"
										: "")
								+ "' value='"
								+ escapeHtml(htmlString)
								+ "' "
								+ ((readOnlyFields.contains(key))
										|| (idFields.contains(key) || readOnly) ? "readonly"
										: "") + " />";
						result += "</td></tr>";
					}
				}
				result += "<tr><th></th>";
				if (dept == "") {
					result += "<th><div align='right'><span style='margin-right:10px;' id='edit_status'></span><INPUT TYPE=submit id='submitButton' value='Speichern' disabled /><input type='button' value='Abbrechen' onclick='window.location=\"/adminPanel.jsp?page=db&table="
							+ table + "\";' />  </div></th>";
				} else {
					arrayIndex++;
				}
				result += "</tr></table>";
				if (form) {
					result += "</form>";
				}
				result += "<br/>";

			}
			return result;
		} catch (JSONException e) {
			return "Error";
		}
	}

	public static String escapeHtml(String htmlString) {
		StringBuffer sb = new StringBuffer();
		int n = htmlString.length();
		for (int i = 0; i < n; i++) {
			char c = htmlString.charAt(i);
			switch (c) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '�':
				sb.append("&agrave;");
				break;
			case '�':
				sb.append("&Agrave;");
				break;
			case '�':
				sb.append("&acirc;");
				break;
			case '�':
				sb.append("&Acirc;");
				break;
			case '�':
				sb.append("&auml;");
				break;
			case '�':
				sb.append("&Auml;");
				break;
			case '�':
				sb.append("&aring;");
				break;
			case '�':
				sb.append("&Aring;");
				break;
			case '�':
				sb.append("&aelig;");
				break;
			case '�':
				sb.append("&AElig;");
				break;
			case '�':
				sb.append("&ccedil;");
				break;
			case '�':
				sb.append("&Ccedil;");
				break;
			case '�':
				sb.append("&eacute;");
				break;
			case '�':
				sb.append("&Eacute;");
				break;
			case '�':
				sb.append("&egrave;");
				break;
			case '�':
				sb.append("&Egrave;");
				break;
			case '�':
				sb.append("&ecirc;");
				break;
			case '�':
				sb.append("&Ecirc;");
				break;
			case '�':
				sb.append("&euml;");
				break;
			case '�':
				sb.append("&Euml;");
				break;
			case '�':
				sb.append("&iuml;");
				break;
			case '�':
				sb.append("&Iuml;");
				break;
			case '�':
				sb.append("&ocirc;");
				break;
			case '�':
				sb.append("&Ocirc;");
				break;
			case '�':
				sb.append("&ouml;");
				break;
			case '�':
				sb.append("&Ouml;");
				break;
			case '�':
				sb.append("&oslash;");
				break;
			case '�':
				sb.append("&Oslash;");
				break;
			case '�':
				sb.append("&szlig;");
				break;
			case '�':
				sb.append("&ugrave;");
				break;
			case '�':
				sb.append("&Ugrave;");
				break;
			case '�':
				sb.append("&ucirc;");
				break;
			case '�':
				sb.append("&Ucirc;");
				break;
			case '�':
				sb.append("&uuml;");
				break;
			case '�':
				sb.append("&Uuml;");
				break;
			case '�':
				sb.append("&reg;");
				break;
			case '�':
				sb.append("&copy;");
				break;
			case '�':
				sb.append("&euro;");
				break;
			// be carefull with this one (non-breaking whitee space)
			case ' ':
				sb.append("&nbsp;");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
}
