package edu.escuelaing.arem.lambdaec2;

import spark.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import static spark.Spark.get;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        get("/", (req, res) -> loadHome());
        get("/square", (req, res) -> callAPIGateway(req));
    }

    private static Object callAPIGateway(Request req) throws MalformedURLException {
        URL url = new URL("https://kym2x4mfr5.execute-api.us-east-1.amazonaws.com/Beta?value=" + req.queryParams("value"));

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.openStream()))) {
            String inputLine = null;
            inputLine = reader.readLine();
            return inputLine;
        } catch (IOException x) {
            System.err.println(x);
            return "404";
        }
    }

    private static Object loadHome() throws IOException {
        return "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Statistics Calculator API</title>\n" +
                "</head>\n" +
                "<script src=\"https://unpkg.com/axios/dist/axios.min.js\"></script>\n" +
                "<script type=\"text/javascript\">\n" +
                "    function submitForm() {\n" +
                "        var input = document.getElementById('input').value;\n" +
                "\n" +
                "        var headers = {'Content-Type': 'application/json'};\n" +
                "        var Url = \"/square?value=\"+ input;\n" +
                "        axios({\n" +
                "            method: 'get',\n" +
                "            url: Url\n" +
                "        }).then(data => document.getElementById('response').innerHTML = \"Square value: \" + data.data)\n" +
                "    .catch(err => alert(err));\n" +
                "    }\n" +
                "\n" +
                "</script>\n" +
                "\n" +
                "<body bgcolor=\"#afafaf\">\n" +
                "<center>\n" +
                "    <h4>API From EC2 using AWS Lambda</h4>\n" +
                "    <input id=\"input\" type=\"text\" placeholder=\"Number\" onsubmit=\"submitForm()\"/><br><br>\n" +
                "    <input type=\"button\" value=\"submit\" onclick=\"submitForm()\"/><br><br>\n" +
                "    <label id=\"response\"></label>\n" +
                "</center>\n" +
                "</body>\n" +
                "</html>";
    }


}
