from baike import url_manager, html_download, html_parser, html_output


class SpiderMain(object):
    def __init__(self):
        self.urls = url_manager.UrlManager()
        self.download = html_download.HtmlDown()
        self.parser = html_parser.HtmlParser()
        self.out = html_output.HtmlOutput()

    def crow(self, root_url):
        count = 1
        self.urls.add_new_url(root_url)
        while self.urls.has_new_url():
            try:
                new_url = self.urls.get_new_url()
                print 'craw %d:%s' % (count, new_url)
                html_count = self.download.download(new_url)
                data = self.parser.parser(html_count)
                self.out.collect_data(data)
                if count == 1000:
                    break
                count = count+1
            except:
                print 'craw failed:'
        self.out.output_html()



if __name__ == '__main__':
    root_url = 'http://www.qiushibaike.com/'
    obj_spider = SpiderMain()
    obj_spider.crow(root_url)
