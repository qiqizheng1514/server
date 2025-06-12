-- 插入示例用户数据（如果不存在）
INSERT INTO users (username, password, nickname, avatar)
SELECT 'admin', '$2a$10$rTZ8z5ORbVnq7UUqe1.8QOxjWHyzmRWwxjgvX5UbxP5PJvPKZIlS.', '管理员', '/avatars/admin.jpg'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO users (username, password, nickname, avatar)
SELECT 'user1', '$2a$10$rTZ8z5ORbVnq7UUqe1.8QOxjWHyzmRWwxjgvX5UbxP5PJvPKZIlS.', '游客1', '/avatars/user1.jpg'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user1');

INSERT INTO users (username, password, nickname, avatar)
SELECT 'user2', '$2a$10$rTZ8z5ORbVnq7UUqe1.8QOxjWHyzmRWwxjgvX5UbxP5PJvPKZIlS.', '游客2', '/avatars/user2.jpg'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user2');

-- 插入福州景点数据（如果不存在）
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '三坊七巷', '三坊七巷是中国十大历史文化名街之一，被誉为"中国城市里坊制度活化石"和"中国明清建筑博物馆"。这里保存了大量明清古建筑，街区总面积约40公顷，由南后街、安民巷、黄巷、塔巷、杨桥巷、郎官巷、衣锦坊、光禄坊、宫巷、塔巷五坊等组成。三坊七巷是福州市的历史文化街区，也是国家5A级旅游景区，拥有众多名人故居和历史建筑。', '福建省福州市鼓楼区南后街道', '全天开放', 0, 4.7, '/images/spots/1.jpg', 26.0812, 119.2955
WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '三坊七巷');

INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福州西湖公园', '福州西湖公园是福州市区内最大的综合性公园，始建于1911年，总面积约42.7公顷。公园内湖光山色，景色秀丽，是福州市民休闲娱乐的重要场所。西湖公园以湖为主景，园内有"十景"，分别是"平湖秋月"、"湖心春晓"、"湖山胜概"、"曲水流觞"、"荷风送香"、"柳浪闻莺"、"梅雪争春"、"松涛迎客"、"竹韵幽情"和"菊岛秋容"。', '福建省福州市鼓楼区湖滨路68号', '06:00-22:00', 0, 4.5, '/images/spots/2.jpg', 26.0835, 119.2927
WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福州西湖公园');

INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '烟台山', '烟台山是福州市区内的一座小山，海拔57米，因山顶曾设有烟墩台而得名。烟台山是福州近代对外开放的重要见证，山上保存有大量的西式建筑和历史遗迹，是中国近代史的重要组成部分。现在的烟台山已经成为福州市的重要文化旅游景点，山上有烟台山历史风貌区、福州外国语学校等。', '福建省福州市鼓楼区烟台山路', '全天开放', 0, 4.3, '/images/spots/3.jpg', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '烟台山');

INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '鼓山', '鼓山位于福州市区东北部，海拔925米，是福州市的最高峰，也是福建省著名的佛教圣地。鼓山上的涌泉寺始建于梁武帝天监年间（公元502-519年），距今已有1500多年历史，是福建省规模最大、保存最完整的古刹之一。鼓山风景区内自然风光与人文景观交相辉映，有"鼓山36奇景"之称，是福州市民登高望远、休闲娱乐的好去处。', '福建省福州市晋安区鼓山镇', '07:30-17:30', 50, 4.6, '/images/spots/4.jpg', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '鼓山');

-- 插入示例景点评价数据（如果不存在）
INSERT INTO reviews (spot_id, user_id, username, avatar, rating, title, content)
SELECT 1, 1, 'admin', '/avatars/admin.jpg', 5, '非常推荐！', '三坊七巷的古建筑保存完好，文化氛围浓厚，值得一游！'
WHERE NOT EXISTS (
    SELECT 1 FROM reviews 
    WHERE spot_id = 1 AND user_id = 1 AND title = '非常推荐！'
);

INSERT INTO reviews (spot_id, user_id, username, avatar, rating, title, content)
SELECT 1, 2, 'user1', '/avatars/user1.jpg', 4, '不错的体验', '环境优美，就是人稍微多了点'
WHERE NOT EXISTS (
    SELECT 1 FROM reviews 
    WHERE spot_id = 1 AND user_id = 2 AND title = '不错的体验'
);

INSERT INTO reviews (spot_id, user_id, username, avatar, rating, title, content)
SELECT 2, 3, 'user2', '/avatars/user2.jpg', 5, '完美的旅行', '森林公园的空气非常好，植物种类丰富，一定要带相机来！'
WHERE NOT EXISTS (
    SELECT 1 FROM reviews 
    WHERE spot_id = 2 AND user_id = 3 AND title = '完美的旅行'
);

-- 插入示例收藏数据（如果不存在）
INSERT INTO favorites (user_id, spot_id, spot_name, spot_image)
SELECT 1, 1, '三坊七巷', '/images/spots/sanfangqixiang.jpg'
WHERE NOT EXISTS (
    SELECT 1 FROM favorites 
    WHERE user_id = 1 AND spot_id = 1
);

INSERT INTO favorites (user_id, spot_id, spot_name, spot_image)
SELECT 1, 2, '福州国家森林公园', '/images/spots/forest_park.jpg'
WHERE NOT EXISTS (
    SELECT 1 FROM favorites 
    WHERE user_id = 1 AND spot_id = 2
);

INSERT INTO favorites (user_id, spot_id, spot_name, spot_image)
SELECT 2, 3, '鼓山', '/images/spots/gushan.jpg'
WHERE NOT EXISTS (
    SELECT 1 FROM favorites 
    WHERE user_id = 2 AND spot_id = 3
);

-- 以下为spotsData.js自动生成的景点数据
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '三坊七巷', '三坊七巷是中国十大历史文化名街之一，被誉为"中国城市里坊制度活化石"和"中国明清建筑博物馆"。这里保存了大量明清古建筑，街区总面积约40公顷，由南后街、安民巷、黄巷、塔巷、杨桥巷、郎官巷、衣锦坊、光禄坊、宫巷、塔巷五坊等组成。三坊七巷是福州市的历史文化街区，也是国家5A级旅游景区，拥有众多名人故居和历史建筑。', '福建省福州市鼓楼区南后街道', '全天开放', 0, 4.7, '/images/spots/1.jpg', 26.0812, 119.2955 WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '三坊七巷');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福州西湖公园', '福州西湖公园是福州市区内最大的综合性公园，始建于1911年，总面积约42.7公顷。公园内湖光山色，景色秀丽，是福州市民休闲娱乐的重要场所。西湖公园以湖为主景，园内有"十景"，分别是"平湖秋月"、"湖心春晓"、"湖山胜概"、"曲水流觞"、"荷风送香"、"柳浪闻莺"、"梅雪争春"、"松涛迎客"、"竹韵幽情"和"菊岛秋容"。', '福建省福州市鼓楼区湖滨路68号', '06:00-22:00', 0, 4.5, '/images/spots/2.jpg', 26.0835, 119.2927 WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福州西湖公园');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '烟台山', '烟台山是福州市区内的一座小山，海拔57米，因山顶曾设有烟墩台而得名。烟台山是福州近代对外开放的重要见证，山上保存有大量的西式建筑和历史遗迹，是中国近代史的重要组成部分。现在的烟台山已经成为福州市的重要文化旅游景点，山上有烟台山历史风貌区、福州外国语学校等。', '福建省福州市鼓楼区烟台山路', '全天开放', 0, 4.3, '/images/spots/3.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '烟台山');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '鼓山', '鼓山位于福州市区东北部，海拔925米，是福州市的最高峰，也是福建省著名的佛教圣地。鼓山上的涌泉寺始建于梁武帝天监年间（公元502-519年），距今已有1500多年历史，是福建省规模最大、保存最完整的古刹之一。鼓山风景区内自然风光与人文景观交相辉映，有"鼓山36奇景"之称，是福州市民登高望远、休闲娱乐的好去处。', '福建省福州市晋安区鼓山镇', '07:30-17:30', 50, 4.6, '/images/spots/4.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '鼓山');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福州国家森林公园', '福州国家森林公园位于福州市区北郊，总面积约860公顷，是福州市最大的森林公园。公园内森林覆盖率高达95%以上，是福州市的"绿肺"，也是市民休闲娱乐的好去处。公园内有多个景区，如百花园、竹海、樱花园、杜鹃园等，四季景色各异，是亲近自然的好地方。', '福建省福州市晋安区连江北路', '08:00-17:30', 30, 4.4, '/images/spots/5.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福州国家森林公园');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '左海公园', '左海公园位于福州市区中心，是一个以湖泊为主的城市公园，总面积约88公顷。左海原名"瓮头埕"，因位于西湖以东，故称"左海"，是福州市民休闲娱乐的好去处。公园内有湖泊、亭台楼阁、花坛草坪等景观，环境优美，是福州市区内重要的生态景观和休闲场所。', '福建省福州市鼓楼区杨桥中路', '全天开放', 0, 4.3, '/images/spots/6.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '左海公园');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福州文化宫', '福州文化宫位于福州市区中心，是一座集文化、艺术、娱乐、休闲于一体的综合性文化场所。文化宫建于1953年，是福州市最早的文化活动中心之一，经过多次改造，现已成为福州市重要的文化地标。文化宫内设有剧场、展览馆、图书馆、文化活动室等多个功能区，是市民参与文化活动的重要场所。', '福建省福州市鼓楼区八一七北路', '09:00-21:00', 0, 4.0, '/images/spots/7.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福州文化宫');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '金山寺', '金山寺位于福州市晋安区鼓山镇，是福建省著名的佛教寺庙，始建于唐代，距今已有1200多年历史。金山寺依山而建，殿宇宏伟，环境幽静，是福州市重要的宗教文化遗产和旅游景点。寺内保存有大量的历代文物和佛教艺术品，如佛像、经书、石刻等，具有很高的历史和艺术价值。', '福建省福州市晋安区鼓山镇金山路', '08:00-17:00', 20, 4.2, '/images/spots/8.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '金山寺');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福州森林公园', '福州森林公园位于福州市晋安区，总面积约1068公顷，是福州市重要的生态公园和休闲场所。公园内森林资源丰富，植被茂密，空气清新，是市民亲近自然、休闲娱乐的好去处。公园内设有多个景区，如百花园、竹林、观景台等，还有各类休闲娱乐设施，如游乐场、餐厅等。', '福建省福州市晋安区岳峰镇', '06:00-18:00', 0, 4.4, '/images/spots/9.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福州森林公园');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '青云山', '青云山位于福州市晋安区东北部，海拔约700米，是福州市区周边的一座著名山峰。青云山自然风光优美，植被茂密，山势起伏，有"小武夷"之称，是福州市民登山健身、休闲娱乐的好去处。山上有青云寺、青云亭等人文景观，还有观景台、瀑布等自然景观，景色宜人，四季各有特色。', '福建省福州市晋安区青云山风景区', '07:00-18:00', 15, 4.1, '/images/spots/10.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '青云山');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福州温泉公园', '福州温泉公园位于福州市晋安区鼓山脚下，是一处以温泉为主题的综合性公园，总面积约60公顷。公园内有多处温泉泉眼，水质清澈，富含多种矿物质，具有很高的医疗保健价值。除了温泉资源外，公园内还有花园、湖泊、亭台楼阁等景观，环境优美，是休闲度假的好去处。', '福建省福州市晋安区温泉路88号', '09:00-22:00', 40, 4.3, '/images/spots/11.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福州温泉公园');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福州海峡文化艺术中心', '福州海峡文化艺术中心位于福州市台江区，是一座集演艺、展览、会议、培训等功能于一体的大型文化设施。中心建筑造型独特，外形如同一朵盛开的茉莉花，是福州市的标志性建筑之一。中心内设有大剧院、音乐厅、美术馆、博物馆等多个场馆，经常举办各类文化艺术活动，是福州市重要的文化地标。', '福建省福州市台江区茶亭街道鳌峰路1号', '09:00-21:00（演出时间另行安排）', 0, 4.5, '/images/spots/12.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福州海峡文化艺术中心');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福州国家湿地公园', '福州国家湿地公园位于福州市闽侯县，是一处以湿地生态系统保护和展示为主题的国家级湿地公园。公园总面积约1500公顷，包括湿地、水域、森林等多种生态系统，是福州市重要的生态保护区和科普教育基地。公园内栖息着众多珍稀鸟类和水生动植物，是观鸟、科普、休闲的理想场所，也是福州市民亲近自然的好去处。', '福建省福州市闽侯县南通镇', '08:30-17:30', 20, 4.2, '/images/spots/13.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福州国家湿地公园');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福州大学', '福州大学创建于1958年，是福建省重点建设的高水平大学，也是国家"双一流"建设高校。学校位于福州市闽侯县，校园占地面积约2700亩，环境优美，是福州市著名的人文景观和教育基地。校园内有旗山、铜盘湖等自然景观，以及图书馆、体育馆、博物馆等建筑，是参观学习和休闲游览的好去处。', '福建省福州市闽侯县上街镇福州大学城', '08:00-22:00（需要办理参观证）', 0, 4.4, '/images/spots/14.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福州大学');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福建博物院', '福建博物院是福建省规模最大、藏品最丰富的综合性博物馆，位于福州市晋安区。博物院建筑面积约5.3万平方米，馆藏文物17万余件，其中一级文物170余件，是了解福建历史文化的重要场所。博物院分为历史馆、革命馆、自然馆、艺术馆等多个展区，展示了福建丰富的历史文化和自然资源。', '福建省福州市晋安区鼓山路96号', '09:00-17:00（周一闭馆）', 0, 4.6, '/images/spots/15.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福建博物院');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福州海滨公园', '福州海滨公园位于福州市长乐区，是一处集海滨休闲、观光、娱乐为一体的综合性海滨公园。公园总面积约200公顷，拥有长达5公里的优质沙滩，是福州市民亲近大海、休闲娱乐的好去处。公园内设有沙滩浴场、海滨栈道、观景平台等设施，可以欣赏到壮阔的海景和美丽的日出日落。', '福建省福州市长乐区环海路', '全天开放', 0, 4.3, '/images/spots/16.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福州海滨公园');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福州滨海新城', '福州滨海新城位于福州市长乐区，是福州市重点打造的现代化滨海新城区。新城规划面积约189平方公里，以"滨海生态、智慧创新、宜居宜业"为特色，集商务、居住、休闲、旅游等功能于一体。新城内有滨海大道、滨海公园、商业中心等设施，是福州市新兴的城市地标和旅游观光地。', '福建省福州市长乐区滨海新城', '全天开放', 0, 4.1, '/images/spots/17.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福州滨海新城');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '长乐国际机场', '长乐国际机场（现已更名为福州长乐国际机场）位于福州市长乐区，是福建省重要的国际航空港。机场于1997年6月开始运营，是中国东南沿海重要的航空枢纽，也是福州市对外交流的重要窗口。机场航站楼设计现代，内部设施完善，是观赏飞机起降和了解现代航空运输的好去处。', '福建省福州市长乐区机场路', '全天开放（根据航班时刻表）', 0, 4.0, '/images/spots/18.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '长乐国际机场');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福州园林公园', '福州园林公园位于福州市仓山区，是一座以传统园林艺术为主题的综合性公园。公园占地面积约50公顷，园内有亭台楼阁、假山水系、花木景观等，融合了中国传统园林的精髓。公园内设有闽派盆景园、牡丹园、茶文化园等特色景区，是了解福州传统文化和休闲游览的好去处。', '福建省福州市仓山区金山大道', '08:00-18:00', 15, 4.2, '/images/spots/19.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福州园林公园');
INSERT INTO spots (name, description, address, opening_hours, ticket_price, rating, image_url, latitude, longitude)
SELECT '福州市博物馆', '福州市博物馆位于福州市鼓楼区，是一座以展示福州历史文化为主题的综合性博物馆。博物馆建筑面积约2万平方米，馆藏文物近5万件，包括陶瓷、书画、青铜器、玉器等多种类型。博物馆分为福州历史文化展、闽都民俗展、福州工艺美术展等多个展区，全面展示了福州悠久的历史和灿烂的文化。', '福建省福州市鼓楼区湖东路96号', '09:00-17:00（周一闭馆）', 0, 4.5, '/images/spots/20.jpg', NULL, NULL WHERE NOT EXISTS (SELECT 1 FROM spots WHERE name = '福州市博物馆');
-- ... 其余景点同理，依次追加 ... 