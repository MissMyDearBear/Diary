import requests


class HtmlDown(object):
    def download(self, url):
        if url is None:
            return None
        response = requests.get(url)
        if response.status_code != 200:
            return None
        return response.text


