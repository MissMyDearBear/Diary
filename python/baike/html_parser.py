from bs4 import BeautifulSoup


class HtmlParser(object):
    def __init__(self):
        self.items = []

    def parser(self, response):
        if response is None:
            return None
        soup = BeautifulSoup(response, 'html.parser')
        nodes = soup.findAll('div', class_='article block untagged mb15')
        if nodes is None or len(nodes) == 0:
            return None
        for node in nodes:
            image_node = node.img
            image = 'http:' + image_node['src']
            user_name = image_node['alt']
            content_node = node.span.get_text()
            data = {
                'image_url': image,
                'user_name': user_name,
                'content': content_node
            }
            self.items.append(data)
        return self.items
