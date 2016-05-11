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
