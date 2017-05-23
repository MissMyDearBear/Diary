import json

class HtmlOutput(object):
    def __init__(self):
        self.data = []

    def collect_data(self, data):
        if data is None:
            return
        self.data = data

    def output_html(self):
        self.write_to_json(self.data)
        fout = open('output.html', 'w')
        fout.write("<html>")
        fout.write("<body>")
        fout.write("<table>")
        fout.write("<tr>")
        fout.write("<td>name</td>")
        fout.write("<td>image</td>")
        fout.write("<td>content</td>")
        fout.write("</tr>")
        if len(self.data) > 0:
            for item in self.data:
                fout.write("<tr>")
                fout.write("<td>%s</td>" % (item['user_name']).encode('utf-8'))
                fout.write("<td>%s</td>" % (item['image_url']))
                fout.write("<td>%s</td>" % (item['content']).encode('utf-8'))
                fout.write("</tr>")

        fout.write("</table>")
        fout.write("</body>")
        fout.write("</html>")
        fout.close()

    def write_to_json(self, data):
        if data is None:
            return
        f = open('json.txt', 'w')
        json_str = json.dumps(data).encode('utf-8')
        print json_str
        f.write(json_str)
        f.close()

