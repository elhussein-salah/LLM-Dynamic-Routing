package web;

import fi.iki.elonen.NanoHTTPD;
import domain.UserQuery;
import domain.ResultDTO;
import com.google.gson.Gson;
import router.RouterService;

import java.util.Map;


public class WebServer extends NanoHTTPD {

    private final RouterService service;
    private final Gson gson = new Gson();

    public WebServer(int port, RouterService service) {
        super(port);
        this.service = service;
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            if (Method.POST.equals(session.getMethod()) && "/query".equals(session.getUri())) {

                session.parseBody(null);
                Map<String, String> params = session.getParms();
                String queryText = params.get("text");

                if (queryText == null || queryText.isEmpty()) {
                    return newFixedLengthResponse(Response.Status.BAD_REQUEST, "text/plain", "Query is empty");
                }

                UserQuery query = new UserQuery(queryText);
                ResultDTO result = service.handle(query);

                String jsonResponse = gson.toJson(result);
                return newFixedLengthResponse(Response.Status.OK, "application/json", jsonResponse);
            } else {

                String html = "<!DOCTYPE html><html><head><title>LLM Router</title>" +
                        "<style>" +
                        "body { margin:0; font-family: 'Segoe UI', sans-serif; background: #121212; color:white; }" +
                        "header { background: #1F1F1F; padding: 15px; text-align:center; font-size:24px; font-weight:bold; }" +
                        "main { display:flex; flex-direction:column; height: calc(100vh - 60px); }" +
                        "#results { flex:1; overflow-y:auto; padding:20px; display:flex; flex-direction:column-reverse; gap:10px; position:relative; }" +
                        "#welcome { position:absolute; top:50%; left:50%; transform:translate(-50%,-50%); color:#AAAAAA; font-size:20px; text-align:center; }" +
                        ".result-card { background:#1F1F1F; padding:15px; border-radius:10px; box-shadow: 0 2px 6px rgba(0,0,0,0.5); }" +
                        ".query { color:#00FFAB; font-weight:bold; margin-bottom:5px; }" +
                        ".answer { color:#FFFFFF; margin-bottom:5px; }" +
                        ".meta { font-size:12px; color:#AAAAAA; }" +
                        "#inputArea { display:flex; padding:15px; background:#1F1F1F; border-top:1px solid #333; }" +
                        "#queryInput { flex:1; padding:18px; border-radius:8px; border:none; outline:none; background:#2C2C2C; color:white; font-size:18px; }" +
                        "#sendBtn { margin-left:10px; padding:15px 25px; border:none; border-radius:8px; background:#00FFAB; color:black; cursor:pointer; font-weight:bold; font-size:16px; }" +
                        "#sendBtn:hover { background:#00d18b; }" +
                        "</style></head><body>" +
                        "<header>LLM Router</header>" +
                        "<main>" +
                        "<div id='results'>" +
                        "  <div id='welcome'>Hi there! Ask me anything you like...</div>" +
                        "</div>" +
                        "<div id='inputArea'>" +
                        "<input type='text' id='queryInput' placeholder='Type your query here...'/>" +
                        "<button id='sendBtn' onclick='sendQuery()'>Send</button>" +
                        "</div>" +
                        "<script>" +
                        "async function sendQuery() {" +
                        "  const queryText = document.getElementById('queryInput').value;" +
                        "  if(!queryText) return;" +
                        "  document.getElementById('welcome').style.display='none';" + // اختفاء الرسالة عند أول استعلام
                        "  const formData = new FormData();" +
                        "  formData.append('text', queryText);" +
                        "  const response = await fetch('/query', { method:'POST', body: formData });" +
                        "  const result = await response.json();" +
                        "  const resultsDiv = document.getElementById('results');" +
                        "  const card = document.createElement('div');" +
                        "  card.className='result-card';" +
                        "  card.innerHTML = `<div class='query'>You: ${queryText}</div>` +" +
                        "                   `<div class='answer'>Bot: ${result.answer}</div>` +" +
                        "                   `<div class='meta'>Model: ${result.usedModel} | Time: ${result.elapsedMs} ms | From Cache: ${result.fromCache}</div>`;" +
                        "  resultsDiv.prepend(card);" +
                        "  document.getElementById('queryInput').value='';" +
                        "  resultsDiv.scrollTop = resultsDiv.scrollHeight;" +
                        "}" +
                        "document.getElementById('queryInput').addEventListener('keypress', function(e) { if(e.key==='Enter'){ sendQuery(); } });" +
                        "</script>" +
                        "</main></body></html>";


                return newFixedLengthResponse(html);
            }
        } catch (Exception e) {
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", e.getMessage());
        }
    }


}













