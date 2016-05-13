USE onlyfeed_db;

DROP TABLE IF EXISTS feed_samples;

CREATE TABLE feed_samples (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    url VARCHAR(512) NOT NULL,
    logo_url VARCHAR(512) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO feed_samples (name, url, logo_url) values ("Хабрахабр / Интересные публикации", 
													   "http://habrahabr.ru/rss", 
													   "https://habrahabr.ru/images/logo.png"); 
INSERT INTO feed_samples (name, url, logo_url) values ("Geektimes / Интересные публикации", 
                                                       "https://geektimes.ru/rss/", 
                                                       "https://geektimes.ru/images/logo.png");
INSERT INTO feed_samples (name, url, logo_url) values ("TJ: популярное", 
                                                       "https://tjournal.ru/rss", 
                                                       "https://tjournal.ru/static/main/img/default-pictures/default-big-original.png"); 
INSERT INTO feed_samples (name, url, logo_url) values ("RT на русском", 
                                                       "https://russian.rt.com/rss", 
                                                       "http://freebeacon.com/wp-content/uploads/2014/04/Russia-Today-Logo.jpg"); 
INSERT INTO feed_samples (name, url, logo_url) values ("CINEMAHOLICS", 
                                                       "http://cinemaholics.ru/rss/", 
                                                       "https://pp.vk.me/c621225/v621225464/155d7/AS5uKs8mH5U.jpg"); 
INSERT INTO feed_samples (name, url, logo_url) values ("Журнал «Нож»", 
                                                       "http://knife.media/feed/", 
                                                       "https://pp.vk.me/c630621/v630621937/113b9/aSuZTGQjtlY.jpg");
INSERT INTO feed_samples (name, url, logo_url) values ("Lenta.ru : Новости", 
                                                       "https://lenta.ru/rss", 
                                                       "https://pbs.twimg.com/profile_images/738155148/LentaNovosti_400x400.jpg");
INSERT INTO feed_samples (name, url, logo_url) values ("N+1: научные статьи, новости, открытия", 
                                                       "https://nplus1.ru/rss", 
                                                       "https://nplus1.ru/i/logo.png");
INSERT INTO feed_samples (name, url, logo_url) values ("Naked Science", 
                                                       "http://naked-science.ru/feedrss.xml", 
                                                       "http://science-film.ru/uploads/video/5559eaa2c8fcf.jpg");
INSERT INTO feed_samples (name, url, logo_url) values ("Fashiony.ru - модное сообщество", 
                                                       "http://feeds.feedburner.com/fashiony?format=xml", 
                                                       "http://st2-fashiony.ru/img/fashiony-logo.png");
INSERT INTO feed_samples (name, url, logo_url) values ("ЯПлакалъ - развлекательное сообщество", 
                                                       "http://www.yaplakal.com/news.xml", 
                                                       "http://www.yaplakal.com/html/static/top-logo.png");
INSERT INTO feed_samples (name, url, logo_url) values ("Drom.ru: Новости", 
                                                       "http://www.drom.ru/cached_files/xml/news.rss", 
                                                       "http://images.drom.ru/img/drom_88x31.gif");
INSERT INTO feed_samples (name, url, logo_url) values ("За рулем - Онлайн", 
                                                       "http://www.zr.ru/rss-news/", 
                                                       "http://www.zr.ru/f/media/logo.png");
INSERT INTO feed_samples (name, url, logo_url) values ("Новости на Колеса.Ру", 
                                                       "http://www.kolesa.ru/rss/news.xml", 
                                                       "http://www.kolesa.ru/web/images/logo.png");
INSERT INTO feed_samples (name, url, logo_url) values ("Анекдоты из России - Anekdot.ru", 
                                                       "http://www.anekdot.ru/rss/export20.xml", 
                                                       "http://www.anekdot.ru/i/logo-88-31.gif?rss");
INSERT INTO feed_samples (name, url, logo_url) values ("Выбор редакции Rutube", 
                                                       "http://rutube.ru/mrss/video/editorsfeed/", 
                                                       "https://pbs.twimg.com/profile_images/472011410491207680/za4vFSwp_400x400.jpeg");
INSERT INTO feed_samples (name, url, logo_url) values ("ВЕСТИ", 
                                                       "http://www.vesti.ru/vesti.rss", 
                                                       "http://www.vesti.ru/i/blog_logo_vesti.jpg");
INSERT INTO feed_samples (name, url, logo_url) values ("Главное — Рамблер/Новости", 
                                                       "http://news.rambler.ru/rss/head/", 
                                                       "http://news.rl0.ru/st//i/16.105.106171142/touch-icon-57.png");
INSERT INTO feed_samples (name, url, logo_url) values ("Газета.Ru - Новость часа", 
                                                       "http://www.gazeta.ru/export/rss/lastnews.xml", 
                                                       "http://www.gazeta.ru/i/gazeta_og_image.jpg");
INSERT INTO feed_samples (name, url, logo_url) values ("Газета.Ru - Политика", 
                                                       "http://www.gazeta.ru/export/rss/politics.xml", 
                                                       "http://www.gazeta.ru/i/gazeta_og_image.jpg");
INSERT INTO feed_samples (name, url, logo_url) values ("Новостной портал Ria.ru: больше, чем просто новости", 
                                                       "http://ria.ru/export/rss2/index.xml", 
                                                       "http://cdn12.img22.ria.ru/i/ria_logo.png?0000444");
INSERT INTO feed_samples (name, url, logo_url) values ("Афиша: Кино", 
                                                       "http://feeds.feedburner.com/afisha_msk_cinema", 
                                                       "http://s.afisha.ru/dsn/logo-afisha-100x100.gif");
INSERT INTO feed_samples (name, url, logo_url) values ("Афиша: Рестораны", 
                                                       "http://feeds.feedburner.com/afisha_msk_restaurants", 
                                                       "http://s.afisha.ru/dsn/logo-afisha-100x100.gif");
